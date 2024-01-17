import java.io.IOException;
import java.net.URI;
import java.util.*;

/*
implement a web server (like NumberServer.java) that tracks a list of strings.
It should support a path for adding a new string to the list, 
and a path for querying the list of strings 
and returning a list of all strings that have a given substring.

Examples of paths/queries:

/add?s=anewstringtoadd
/add?s=pineapple
/add?s=apple
/search?s=app
(would return pineapple and apple)
*/

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return strings.toString();
        } else {
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                strings.add(parameters[1]);
                return String.format("%s added!", parameters[1]);
            } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                ArrayList<String> search = new ArrayList<>();
                for(int i = 0; i < strings.size(); i++) {
                    if(strings.get(i).contains(parameters[1])) {
                        search.add(strings.get(i));
                    }
                }
                return search.toString();       
            }
        }   
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
