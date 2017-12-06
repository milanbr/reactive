package org.brich.reactive.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Milan Brich (cen76482).
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserReactiveRepository userReactiveRepository;

    @Autowired
    public UserController(UserReactiveRepository userReactiveRepository) {
        this.userReactiveRepository = userReactiveRepository;
    }

    @PostMapping
    public Mono<User> saveUser(@RequestBody Mono<User> userMono) {
        return userMono.flatMap(userReactiveRepository::save);
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userReactiveRepository.findAll()
                .log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> getAllUsersStream() {
        return userReactiveRepository.findWithTailableCursorBy().log();
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<User>> getAllUsersSse() {
        return userReactiveRepository.findWithTailableCursorBy()
                .map(event -> ServerSentEvent.<User>builder()
                        .event("message")
                        .id(event.getId())
                        .data(new User(event.getId(), event.getName()))
                        .build())
                .log();
    }
}
