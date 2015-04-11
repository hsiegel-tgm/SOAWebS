package soa;

import javax.jws.WebService;

/**
 * Implementation Service of the Knowledge Base Search Engine
 */
@WebService(endpointInterface = "soa.Searchable")
public class KnowledgeBaseSearcher implements Searchable{

    /**
     * The method search is returning the results of a search.
     *
     * @param searchstring
     * @return String results
     */
    @Override
    public String search(String searchstring) {
        //TODO implement database stuff
        return "Return the knowledge base entry with the id: "+searchstring;
    }


}