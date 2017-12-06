package org.brich.reactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.brich.reactive.user.User;
import org.brich.reactive.user.UserReactiveRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Milan Brich (cen76482).
 */
@Component
@Slf4j
public class MyWebSocketHandler implements WebSocketHandler {

    private final UserReactiveRepository userReactiveRepository;

    private ObjectMapper mapper;

    public MyWebSocketHandler(UserReactiveRepository userReactiveRepository, ObjectMapper mapper) {
        this.userReactiveRepository = userReactiveRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(userReactiveRepository);

        session.receive()
                .subscribe(subscriber::onNext, subscriber::onError, subscriber::onComplete);

        Flux<User> usersTailable = userReactiveRepository.findWithTailableCursorBy();

        return session.send(usersTailable
                .map(user -> {
                    try {
                        return mapper.writeValueAsString(user);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(session::textMessage));
    }
}
