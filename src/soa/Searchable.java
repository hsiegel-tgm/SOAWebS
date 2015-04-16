package soa;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * Interface for the Search service
 * @author Hannah Siegel
 * @version 0.2
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface Searchable {
    @WebMethod String search(String searchstring);
}


