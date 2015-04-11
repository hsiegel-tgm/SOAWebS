package soa;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class KnowledgeBaseSearcherPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/soa/searcher", new KnowledgeBaseSearcher());
        System.out.println("Service Published.");
    }

}