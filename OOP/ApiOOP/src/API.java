import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class API {
    private Properties properties;
    private String apiKey;
    private String apiUrl;
    private URL url;
    private HttpURLConnection connection;

    public API(Properties properties){
        this.properties = properties; // Saves the created property from User class
        this.apiKey = properties.getProperty("apiKey"); // Saves the apiKey from the omdb.txt to apiKey, the variable name typo has to match with the OMDB.txt file, important!
         }
    public void fetchURL(String searchQuery) throws Exception {
        this.apiUrl = "https://www.omdbapi.com/?apikey=" + this.apiKey + "&t=" + searchQuery; // ? means that everything after will be variables, & means that there will be more variables after this one
        this.url = new URL(apiUrl); // Creates a new URL object with the apiUrl as a parameter
        this.connection = (HttpURLConnection) url.openConnection(); // Handles the connection to the URL object, no need to write new because it is already created in the URL object
        connection.setRequestMethod("GET"); // Sets the request method to GET (GET is used to request data from a specified resource)
    }

    // Relevant setters & getters
    public HttpURLConnection getConnection() {
        return connection;
    }
}





