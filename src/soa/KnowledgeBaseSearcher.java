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
            d.setServerName("10.0.104.157");
            d.setDatabaseName("iknow");
            d.setUser("vsdb");
            d.setPassword("letmein");
            con = d.getConnection();

            String query = "SELECT * FROM knowledgebase;";

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
            e.printStackTrace();
        }


        return s;


    }
}