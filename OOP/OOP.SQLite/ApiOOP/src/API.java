
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class API {
    private Properties properties;
    private String apiKey;
    private String apiUrl;
    private URL url;
    private HttpURLConnection connection;

    private BufferedReader br;
    private StringBuffer response;
    private String inputLine;

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

    public HttpURLConnection getConnection() {
        return connection;
    }

    /* public void buffer(API api) throws Exception{
        this.br = new BufferedReader(new InputStreamReader(getConnection().getInputStream())); // Gets info in packages and stores it in the memory(buffer), when read into the program, it deletes it and stores next package
        this.response = new StringBuffer(); // StringBuffer is faster than String, it is used to store and manipulate strings
        while ((this.inputLine = br.readLine()) != null) { // Reads every line from the BufferedReader object, when there is no line (null) it stops
            response.append(inputLine); // Appends the inputLine variable to the response variable meaning that it adds the inputLine to the response variable
        }
        br.close(); // Closes the BufferedReader object because we don't need it anymore because we have saved the data to the response variable
    }

    public StringBuffer getResponse() {
        return response;
    }

     */


}





