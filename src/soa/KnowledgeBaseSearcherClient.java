package soa;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class KnowledgeBaseSearcherClient {
    public static void main(String[] args) throws Exception {
        //TODO implement CLI


        // URL to the wsdl File
        URL url = new URL("http://localhost:9999/soa/searcher?wsdl");

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soa/", "KnowledgeBaseSearcherService");

        Service service = Service.create(url, qname);

        Searchable hello = service.getPort(Searchable.class);

        System.out.println(hello.search("20"));

    }
}
