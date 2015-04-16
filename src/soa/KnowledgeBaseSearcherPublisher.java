package soa;

import javax.xml.ws.Endpoint;

/**
 * The class KnowledgeBaseSearcherPublisher publishes the Service.
 */
public class KnowledgeBaseSearcherPublisher {
    public static void main(String[] args) {
        //publish
        Endpoint.publish("http://localhost:9999/soa/searcher", new KnowledgeBaseSearcher());
        //TODO test this not LOCALLY
        System.out.println("KnowledgeBaseSearcher Service was successful published on http://localhost:9999/soa/searcher");
    }

}