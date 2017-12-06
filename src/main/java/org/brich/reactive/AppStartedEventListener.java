package org.brich.reactive;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

/**
 * Created by Milan Brich (cen76482).
 */
@Component
@Slf4j
public class AppStartedEventListener {

    private final MongoOperations mongoOperations;

    @Autowired
    public AppStartedEventListener(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // create capped collection
        CollectionOptions collectionOptions = CollectionOptions.empty();
        CollectionOptions size = collectionOptions.size(10000);
        CollectionOptions capped = size.capped();

        MongoCollection<Document> users = mongoOperations.createCollection("user", capped);

        Document tDocument = new Document();
        tDocument.put("name", "Milan");
        users.insertOne(tDocument);
    }
}
