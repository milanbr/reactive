package org.brich.reactive;

import org.brich.reactive.user.User;
import org.brich.reactive.user.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Created by Milan Brich (cen76482).
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ReactiveApplication.class})
public class UserControllerTest {

    @Autowired
    private UserController userController;

    private WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        this.webTestClient = WebTestClient.bindToController(userController)
                .build();
    }
//
//    @Test
//    public void saveUser() {
//        User user = new User();
//        user.setName("Karel");
//
//        webTestClient.post().uri("/api/users")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//                .body(Mono.just(user), User.class)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody()
//                .jsonPath("$.id").isNotEmpty()
//                .jsonPath("$.name").isEqualTo("Karel");
//    }

    @Test
    public void getAllUsersStepVerifier() {
        userController.saveUser(Mono.just(new User("2", "Pepa"))).block();

        StepVerifier.create(userController.getAllUsers())
                .expectNextMatches(user -> user.getName().equals("Milan"))
                .expectNextMatches(user -> user.getName().equals("Pepa"))
                .expectComplete().verify();
    }

    @Test
    public void getAllUsers() {
        webTestClient.get().uri("/api/users")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(User.class);
    }
}
