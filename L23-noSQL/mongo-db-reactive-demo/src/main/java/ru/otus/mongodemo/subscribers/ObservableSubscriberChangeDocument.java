package ru.otus.mongodemo.subscribers;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.OperationType;
import org.bson.Document;

public class ObservableSubscriberChangeDocument
        extends ObservableSubscriber<ChangeStreamDocument<Document>> {

    public ObservableSubscriberChangeDocument() {
        super(true);
    }

    public ObservableSubscriberChangeDocument(boolean printOnNextRecord) {
        super(printOnNextRecord);
    }

    @Override
    public void onNext(ChangeStreamDocument<Document> changedDocument) {
        Document document = changedDocument.getFullDocument();
        OperationType operation = changedDocument.getOperationType();
        System.out.println(String.format("operation: %s, changed document: %s", operation, document));
    }

}
