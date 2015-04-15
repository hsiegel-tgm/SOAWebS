package soa;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.jws.WebService;
import java.sql.*;

/**
 * Implementation Service of the Knowledge Base Search Engine
 */
@WebService(endpointInterface = "soa.Searchable")
public class KnowledgeBaseSearcher implements Searchable {

    /**
     * The method search is returning the results of a search.
     *
     * @param searchstring
     * @return String results
     */
    @Override
    public String search(String searchstring) {
        String s="";
        Connection con = null;
        try {
            // DataSource Class
            com.mysql.jdbc.jdbc2.optional.MysqlDataSource d = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
            d.setServerName("10.0.104.165");
            d.setDatabaseName("iknow");
            // d.setUser("vsdb");
            // d.setPassword("letmein");
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


    }
}