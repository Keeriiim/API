import java.io.*;
import java.util.Properties;

public class User {
    private String userHome;
    private FileInputStream input;
    private Properties properties;
    public User(){
        this.userHome = System.getProperty("user.home"); // Saves the home folder to userHome
        this.properties = new Properties(); // Creates a new Properties object

        try{
            this.input = new FileInputStream(userHome + "/APIKeys/OMDB.txt"); // Creates a new FileInputStream object, reads the file and saves it to "in" variable
        }
        catch(FileNotFoundException e){
            System.out.println("Error, filelocation not found in: " + e.getMessage());
        }
    }

    public void loadProperty(Properties properties){
        try{
               properties.load(input); // Loads the file from the FileInputStream object and converts it's to a value
        }
         catch (Exception e) {
            System.out.println("Error, could not load file because: " + e.getMessage());
        }
    }

    public Properties getProperties() {
        return properties;
    }

}
