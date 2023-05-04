import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Buffer {
    private API api;
    private BufferedReader br;
    private String inputLine;
    private StringBuffer response;



    public Buffer(API api) throws Exception{
        this.api = api;
        this.br = new BufferedReader(new InputStreamReader(api.getConnection().getInputStream())); // Gets info in packages and stores it in the memory(buffer), when read into the program, it deletes it and stores next package
        this.response = new StringBuffer(); // StringBuffer is faster than String, it is used to store and manipulate strings
        while ((this.inputLine = br.readLine()) != null) { // Reads every line from the BufferedReader object, when there is no line (null) it stops
            response.append(inputLine); // Appends the inputLine variable to the response variable meaning that it adds the inputLine to the response variable
        }
        br.close(); // Closes the BufferedReader object because we don't need it anymore because we have saved the data to the response variable
    }

    // Relevant setters & getters
    public StringBuffer getResponse() {
        return response;
    }


}
