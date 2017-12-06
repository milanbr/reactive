package org.brich.reactive;

import lombok.extern.slf4j.Slf4j;
import org.brich.reactive.user.User;
import org.brich.reactive.user.UserReactiveRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;

/**
 * Created by Milan Brich (cen76482).
 */
@Component
@Slf4j
public class WebSocketMessageSubscriber {

    private UserReactiveRepository userReactiveRepository;

    public WebSocketMessageSubscriber(UserReactiveRepository userReactiveRepository) {
        this.userReactiveRepository = userReactiveRepository;
    }

    public void onError(Throwable error) {
        log.info("onError");
        error.printStackTrace();
    }

    public void onComplete() {
        log.info("completed");
    }

    public void onNext(WebSocketMessage webSocketMessage) {
        String payloadAsText = webSocketMessage.getPayloadAsText();
        log.info("onNext: " + payloadAsText);

        userReactiveRepository.save(new User(payloadAsText)).subscribe();
    }
}
