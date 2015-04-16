package soa;

import model.FillDatabase;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.xml.ws.Endpoint;

/**
 * The class KnowledgeBaseSearcherPublisher publishes the Service.
 */
public class KnowledgeBaseSearcherPublisher {
    public static void main(String[] args) {

        //TODO das gehoert hier alles nicht hin ...
        SessionFactory m_sessionFactory;
        m_sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        long startTime = System.currentTimeMillis();
        new FillDatabase().fillDatabase(m_sessionFactory,"KnowledgeBase",100,2,30);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("it took: "+estimatedTime +" milliseconds");
        System.out.println("it took: "+estimatedTime/1000 +" seconds");
        System.out.println("it took: "+(estimatedTime/1000)/60 +" minutes");

        //TODO nur das geheort hier hin!
        //publish
        Endpoint.publish("http://localhost:9999/soa/searcher", new KnowledgeBaseSearcher(m_sessionFactory));
        System.out.println("KnowledgeBaseSearcher Service was successful published on http://localhost:9999/soa/searcher");
    }

}