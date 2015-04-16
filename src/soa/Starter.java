package soa;

import model.KnowledgeBaseManagement;
import model.PerformActionOnDatabase;

import javax.xml.ws.Endpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CLI to start webservice and Client
 *
 * @author Hannah Siege
 * @version 0.2
 */
public class Starter {
    public static void main(String arg []){
        new Starter();
    }

    /**
     * Starts the input
     */
    public Starter(){
        try {
            getParameters();
        } catch (IOException e1) {
        }
    }


    /**
     * The method getParameters is demanding the CLI interaction with the user and simply starting the components
     *
     * @throws IOException
     */
    public void getParameters() throws IOException{
        String start = "client"; //default: client
        String host = "localhost"; //default: localhost
        int port = 9999; //default: 9999
        String path="/soa/searcher"; //default: /soa/searcher
        int inserts = 1000;
        int tags = 50;
        String type = "everything";
        String keyname = "Tag";

        start = read(start,"System: What would you like to start? (client|searcher|default|filldb)","filldb","client","default","searcher");

        switch (start) {
            case "client":
                host = readNormal("System: Please specify the host (localhost or ip)");
                path = readNormal("System: Please specify the path to the service (/xx/xx/xx.../xx)");
                port = readInt("System: Please specify the port",0,20000);
                break;
            case "searcher":
                System.out.println("The following information will be added to an string like \"http://\"+host+\":\"+port+\"\"+path (e.g http://localhost:9999/searcher/)"  );
                host = readNormal("System: Please specify the host (localhost or ip)");
                path = readNormal("System: Please specify the path to the service (/xx/xx/xx.../xx)");
                port = readInt("System: Please specify the port",0,20000);
                break;
            case "filldb":
                type = read(type,"System: Please specify the type you want to have ...  (Everything|delete|only-tags|only-entries|only-tagEntries|default)","only-tags","only-entries","only-tagEntries","Everything","delete");
                switch (type) {
                    case "everything":
                        System.out.println("Okay. We will need some additional informations... \n");
                        inserts = readInt("System: Please specify the number of entries to be inserted ",0,1234567);
                        System.out.println("Good Choice! We will insert " + inserts + " Entries\n");
                        tags = readInt("System: Please specify the number of tags to be inserted ",1,250);
                        System.out.println("Good Choice! We will insert " + tags + " Tags\n");
                        keyname = readNormal("System: Specify the Name of a tag-name (tag will be name0 ... name+"+tags+")");
                        break;
                    case "delete":
                        System.out.println("Okay. We will delete the entries and the tags... \n");
                        break;
                    case "only-tags":
                        System.out.println("Okay. We will only generate some tags... \nWe will need some additional informations... \n");
                        tags = readInt("System: Please specify the number of tags to be inserted ",1,250);
                        System.out.println("Good Choice! We will insert " + tags + " Tags\n");
                        keyname = readNormal("System: Specify the Name of a tag-name (tag will be name0 ... name+"+tags+")");
                        break;
                    case "only-entries":
                        System.out.println("Okay. We will only generate some entries... \nWe will need some additional informations... \n");
                        inserts = readInt("System: Please specify the number of entries to be inserted (max 100000)",0,1100000);
                        break;
                    case "only-tagentries":
                        System.out.println("Okay. We will only generate random tags to the entries... \n");
                }
        }

        //nice output
        System.out.print("\n You are done. Starting the program.");
        for(int i = 0; i<5; ++i){
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
        System.out.print("\n");

        // start the services
        switch (start) {
            case "client":
                new KnowledgeBaseSearcherClient(host,port,path);
                break;

            case "searcher":
                Endpoint.publish("http://"+host+":"+port+""+path, new KnowledgeBaseSearcher());
                System.out.println("KnowledgeBaseSearcher Service was successful published on http://"+host+":"+port+""+path+"\n\n");
                break;

            case "filldb":
                long startTime = System.currentTimeMillis();
                KnowledgeBaseManagement kbm = new KnowledgeBaseManagement();

                switch (type) {
                    case "everything":
                        kbm.all(inserts, tags, keyname);
                        break;
                    case "delete":
                        kbm.delete();
                        break;
                    case "only-tags":
                        kbm.onlyTags(tags,keyname);
                        break;
                    case "only-entries":
                        kbm.onlyEntries(inserts);
                        break;
                    case "only-tagentries":
                        kbm.onlyKKb();
                        break;
                }

                long estimatedTime = System.currentTimeMillis() - startTime;
                System.out.println("\nPerforming the action took: "+estimatedTime/1000 +" seconds");
                break;
        }
    }

    /**
     * The method read is reading in a line, and checks it.
     *
     * @param default_string
     * @param message
     * @param strings
     * @return answer
     * @throws IOException
     */
    public String read(String default_string, String message,String... strings) throws IOException{
        boolean ok = false;
        String answer="";

        //asking as long as the answer was permitted
        while(!ok){
            System.out.println(message);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();

            //checking if the String is 'permitted'
            for ( String s : strings ) {
                if(line.equalsIgnoreCase(s)){
                    ok = true;
                    answer = line;
                    if(line.equalsIgnoreCase("default"))
                        answer = default_string;
                }
            }
        }
        return answer.toLowerCase();
    }


    /**
     * The method reads an integer value from the user and checks if it is between a specific range
     *
     * @param message
     * @param from
     * @param to
     * @return
     * @throws IOException
     */
    public int readInt(String message,int from, int to) throws IOException{
        boolean ok = false;
        int answer=0;
        while(!ok){
            System.out.println(message + ". The range must be between "+(from+1)+" to "+to);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            int num = 0;
            try{
                num = Integer.parseInt(line);
            }catch(java.lang.NumberFormatException nfe){
                continue;
            }

            if(num > from && num <= to){
                answer =  num;
                ok = true;
            }
        }
        return answer;
    }

    /**
     * The method reads in a String without checking it
     *
     * @param message
     * @return
     * @throws IOException
     */
    public String readNormal(String message) throws IOException{
        boolean ok = false;
        String answer="";
        while(!ok){
            System.out.println(message);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            if(line.length()>=1){
                ok = true;
                answer = line;
            }
        }
        return answer;
    }
}
