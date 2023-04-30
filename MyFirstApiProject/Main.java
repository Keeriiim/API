import java.util.*; // The Package that contains the Scanner class, which is used to get user input, and the Properties class, which is used to read the API key from the file.
import java .net.*; // The package that contains the URL class, which is used to create a URL object, and the HttpURLConnection class, which is used to handle the connection to the URL object.
import java.io.*; // The package that contains the BufferedReader class, which is used to read the data from the API call, and the InputStreamReader class, which is used to read the data from the BufferedReader object.
import org.json.*; // The package that contains the JSONObject class, which is used to handle the JSON data received from the API call.
import java.io.FileInputStream; // Is used to handle the FileInputStream object that is used to read the API key from the file.
import java.io.FileNotFoundException; // Is used to handle the FileNotFoundException that is thrown when the file is not found.
import java.io.IOException; // Is used to handle the IOException that is thrown when the file is not found.
import java.util.Properties; // Is used to handle the Properties object that is used to read the API key from the file.

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        String userHome = System.getProperty("user.home"); // Saves the home folder to userHome

        Properties properties = new Properties(); // Creates a new Properties object
        try{
            FileInputStream in = new FileInputStream(userHome + "/IdeaProjects/OMDB/APIKeys/OMDB.txt"); // Creates a new FileInputStream object, reads the file and saves it to "in" variable
            properties.load(in); // Loads the file from the FileInputStream object and converts it's to a value
        }
        catch (FileNotFoundException e) {
            e.printStackTrace(); // Prints the error message if the file is not found and returns to the main method
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String apiKey = properties.getProperty("apiKey"); // Saves the API key to apiKey, the variable name typo has to match with the OMDB.txt file, important!
        System.out.println(apiKey);

        System.out.print("Search for a movie: ");
        String qTitle = input.next();

        String apiUrl = "https://www.omdbapi.com/?apikey=" +apiKey + "&t=" +qTitle; // ? means that everything after will be variables, & means that there will be more variables after this one

        URL url = new URL(apiUrl); // Creates a new URL object with the apiUrl as a parameter
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Handles the connection to the URL object, no need to write new because it is already created in the URL object
        connection.setRequestMethod("GET"); // Sets the request method to GET (GET is used to request data from a specified resource)

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Gets info in packages and stores it in the memory(buffer), when read into the program, it deletes it and stores next package
        String inputLine;
        StringBuffer response = new StringBuffer(); // StringBuffer is faster than String, it is used to store and manipulate strings
        while ((inputLine = br.readLine()) != null) { // Reads every line from the BufferedReader object, when there is no line (null) it stops
            response.append(inputLine); // Appends the inputLine variable to the response variable meaning that it adds the inputLine to the response variable
        }
        br.close(); // Closes the BufferedReader object because we don't need it anymore because we have saved the data to the response variable

        System.out.println(response.toString()); // Prints the response variable as a string

        JSONObject movie = new JSONObject(response.toString()); // Dynamical object that receives all properties residing in the response variable
       /*  String title = movie.getString("Title");
           String year = movie.getString("Year");
           String rated = movie.getString("Rated");
           String runtime = movie.getString("Runtime");
       */
        System.out.println("Title: " + movie.getString("Title"));
        System.out.println("year: " + movie.getString("Year"));
        System.out.println("rated: " + movie.getString("Rated"));
        System.out.println("runtime: " + movie.getString("Runtime"));
    }
}
