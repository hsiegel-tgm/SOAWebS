package soa;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * The class KnowledgeBaseSearcherClient is using the Knowledge Base in order to access the service
 */
public class KnowledgeBaseSearcherClient {
    /**
     * Main-method.
     * Starts the client
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //TODO implement CLI


        // URL to the wsdl File
        URL url = new URL("http://localhost:9999/soa/searcher?wsdl");

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soa/", "KnowledgeBaseSearcherService");

        // Get Service
        Service service = Service.create(url, qname);

        // Get Searchable Object
        Searchable hello = service.getPort(Searchable.class);

        // hello.ann();

        // Call method on Searchable
        System.out.println("I got: \n "+hello.search("Hannah"));

    }
}
