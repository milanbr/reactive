package org.brich.reactive.user;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Created by Milan Brich (cen76482).
 *
 * Can extend RxJava2CrudRepository for rxJava implementation
 */
public interface UserReactiveRepository extends ReactiveCrudRepository<User, String> {

    // user collection has to be of capped type!
    @Tailable
    Flux<User> findWithTailableCursorBy();
}
