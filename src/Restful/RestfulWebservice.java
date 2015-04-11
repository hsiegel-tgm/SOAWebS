package Restful;

import javax.ws.rs.*;
import javax.xml.ws.Endpoint;


@Path("/helloworld")
public class RestfulWebservice {

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World Hannah!";
    }

    @POST
    @Consumes("text/plain")
    public void postClichedMessage(String message) {
        // Store the message
    }

    public static void main(String[] argv) {
        Object implementor = new RestfulWebservice ();
        String address = "http://localhost:9000/test";
        Endpoint.publish(address, implementor);
    }
}



