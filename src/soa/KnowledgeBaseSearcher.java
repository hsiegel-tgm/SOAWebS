package soa;

import model.KnowledgeBase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.jws.WebService;
import java.util.List;

/**
 * Implementation Service of the Knowledge Base Search Engine
 */
@WebService(endpointInterface = "soa.Searchable")
public class KnowledgeBaseSearcher implements Searchable {
    private SessionFactory sf;

    public KnowledgeBaseSearcher(SessionFactory sf){
        this.sf = sf;
    }

    public KnowledgeBaseSearcher(){
    }

    /**
     * The method search is returning the results of a search.
     *
     * @param searchstring
     * @return String results
     */
    @Override
    public String search(String searchstring) {
        String s="The result from the Knowledgebase is: \n";

        /* Connection con = null;
        try {
            // DataSource Class
            com.mysql.jdbc.jdbc2.optional.MysqlDataSource d = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
            d.setServerName("10.0.104.165");
            d.setDatabaseName("iknow");
            d.setUser("vsdb");
            d.setPassword("vsdbpassword");
            con = d.getConnection();
            //TODO mach das bitte mit Hibernate???
            String query = "SELECT * FROM knowledgebase WHERE text LIKE '%"+searchstring+"%' ;";
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String text = rs.getString("topic");
                String topic = rs.getString("text");
                s+=id+" "+text+" "+topic+" \n";
                System.out.println(id+" "+text+" "+topic+" \n");
            }
        } catch (SQLException e) {
            System.out.println("There was a problem");//TODO
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("There was a general problem... "); //TODO
        }
        return s;
        */

       // SessionFactory m_sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();



        // open Session
        Session session = sf.openSession();

        // create query
        Query q = session.getNamedQuery("searchKnowledgeBase");

        // setting parameters
        q.setParameter("searchsting", "%"+searchstring+"%");

        // run query and fetch reslut
        List<?> res = q.list();

        // print out results
        if (res.size() >= 1) {
            s+= "\n\n Results: \n";
            for (int i = 0; i < res.size(); ++i) {
                KnowledgeBase kb = (KnowledgeBase) res.get(i);
                s+="ID:" + kb.getID() + "\n     Topic: "   + kb.getTopic() +"\n     Text: "+kb.getText()+"\n";
            }
        }
        session.flush();
        session.close();

        return s;
    }
}