package soa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * The class KnowledgeBaseSearcherClient is using the Knowledge Base in order to access the service
 */
public class KnowledgeBaseSearcherClient {
    private Searchable searcher;

    public KnowledgeBaseSearcherClient(){
        this("localhost", 9999, "/soa/searcher");
    }
    public KnowledgeBaseSearcherClient(String host){
        this(host, 9999,"/soa/searcher");
    }

    /**
     * Constructor
     * host, port and path will be added to http://localhost:9999/soa/searcher?wsdl
     * @param host - of service
     * @param port - of service
     * @param path - of service
     */
    public KnowledgeBaseSearcherClient(String host, int port,String path){
        // URL to the wsdl File
        URL url = null;
        try {
            // url = new URL("http://localhost:9999/soa/searcher?wsdl");
            url = new URL("http://"+host+":"+port+""+path+"?wsdl");
        } catch (MalformedURLException e) {
        }

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soa/", "KnowledgeBaseSearcherService");
        //QName qname = new QName("KnowledgeBaseSearcherService");

        // Get Service
        Service service = Service.create(url, qname);

        // Get Searchable Object
        searcher = service.getPort(Searchable.class);

        this.search();
    }

    /**
     * Loop the client for requests
     */
    public void search(){
        // read line
        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        String line="";

        System.out.println("To end the Client, write stop or end...\n");
        System.out.println("Please put in the search question... \n  ");

        // ru until stop or end was written
        while(!line.equalsIgnoreCase("stop")||!line.equalsIgnoreCase("end")) {
            try {
                line = buffer.readLine();
            } catch (IOException e) {
            }
            // if a search question was written.. .call searcher and wait for answer
            if(!line.equalsIgnoreCase("")){
                System.out.println("\n"+searcher.search(line));
                System.out.println("Please put in a new search question... \n  ");

            }
        }
        System.out.println("Bye....");
    }


}
