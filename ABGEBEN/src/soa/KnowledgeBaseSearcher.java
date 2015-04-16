package soa;

import model.KnowledgeBase;
import model.Tag;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.jws.WebService;
import java.util.List;

/**
 * Implementation Service of the Knowledge Base Search Engine
 * @author Hannah Siegel
 * @version 0.3
 */
@WebService(endpointInterface = "soa.Searchable")
public class KnowledgeBaseSearcher implements Searchable {
    private SessionFactory sf;

    public KnowledgeBaseSearcher(SessionFactory sf){
        this.sf = sf;
    }

    public KnowledgeBaseSearcher(){
        sf = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    /**
     * The method search is returning the results of a search.
     *
     * @param searchstring
     * @return String results
     */
    @Override
    public String search(String searchstring) {
        String s="";
        StringBuilder sb = new StringBuilder();

        // open Session
        Session session = sf.openSession();

        // create query
        Query q = session.getNamedQuery("searchTag");

        // setting parameters
        q.setParameter("searchstring", searchstring);

        // save starting time
        long startTime = System.currentTimeMillis();

        // run query and fetch reslut
        List<Tag> res = q.list();

        // add to result string
        if (res.size() >= 1) {
            List<KnowledgeBase>kbs = res.get(0).getKnowledgebases();
            kbs.forEach(x -> {
                sb.append("Knowledge: " + x.toString());
            });
        }

        // make string
        s = sb.toString();
        String newString="";
        long estimatedTime = System.currentTimeMillis() - startTime;
        newString += "Searching took "+estimatedTime/1000 +" seconds\n";
        newString += s;

        // flush
        session.flush();
        session.close();

        // return result
        return newString;
    }
}