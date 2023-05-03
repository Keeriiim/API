import java.util.Scanner;

public class Menu {
    private User you;
    private SQL sql;
    private API api;
    private Buffer buffer = null;
    private JSON json = null;
    Scanner scan = new Scanner(System.in);
    private String apiString;


    public void Menu() throws Exception {
        this.you = new User(); // Creates an object of the User class to access the API keys inside the OMDB.txt file located on the local computer
        //  System.out.println(you.getUserHome()); // Check the user home folder
        //  System.out.println(you.getProperties()); // Check the String inside OMDB.txt
        you.loadProperty(you.getProperties()); // stores the api key inside the properties object
        this.sql = new SQL(); // Creates an object of the SQL class
        sql.createTable(); // Creates a table in the database
        this.api = new API(you.getProperties());
        searchMenu();
    }

    public String getApiString() {
        return apiString;
    }

    public void setApiString(String apiString) {
        this.apiString = apiString;
    }

    public SQL getSQL(){
        return this.sql;
    }

    public User getyou(){
        return this.you;
    }


    public void searchMenu() throws Exception{
        System.out.println("\nWelcome to the movie database!");
        System.out.println("Please select an option:");
        System.out.println("1. Search for a movie");
        System.out.println("2. Show search history");
        System.out.println("3. Delete search history");
        System.out.println("4. Info");
        System.out.println("5. Exit");

        int MainMenuChoice = scan.nextInt();

        switch (MainMenuChoice) {
            case 1:
                boolean unSuccesfullSearch = true;

                while(unSuccesfullSearch){
                    if(checkTitle()  == false){
                        checkTitle();
                    }
                    else{
                        unSuccesfullSearch = false;
                    }
                }


                if(sql.isTableEmpty(sql.getConn())) { // Kollar om databasen är tom
                    System.out.println("\n" + "-------------------------------------------");
                    System.out.println("Your SearchQuery Was Added To Your Database");
                    System.out.println("-------------------------------------------");
                    json.printMovieInfo();
                    sql.toInsert(sql.getConn(), json); // Lägger till en rad i databasen
                }
                else if ((sql.doesTableContain(sql.getConn(), json.getTitle()))){ // Kollar om databasen innehåller filmen
                    System.out.println("\n" + "-------------------------------------------");
                    System.out.println("Your SearchQuery Was Added To Your Database");
                    System.out.println("-------------------------------------------");
                    json.printMovieInfo();
                    sql.toInsert(sql.getConn(), json); // Lägger till en rad i databasen
                }
                else{
                    System.out.println("\n" + "------------------------------");
                    System.out.println("Stored Movies In Your Database");
                    System.out.println("------------------------------");
                    sql.printSelectedFromDatabase(sql.getConn(), json.getTitle()); // Prints all movies matching the search title
                }

                sql.autoUpdateId(sql.getConn()); // Uppdaterar id automatiskt
                searchMenu();
                break;

            case 2:
                try {
                    API api = new API(you.getProperties());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                sql.printMoviesInDatabase(sql.getConn());
                searchMenu();
                break;

            case 3:
                sql.resetTable(sql.getConn()); // Rensar databasen, villkor att den inte är tom för attk unna köras
                searchMenu();
                break;

            case 4:
                System.out.println("This database is a local database that stores movies from the OMDB API");
                System.out.println("The database is created with the help of Java and SQL");
                System.out.println("The database will only contain movies that you have searched for via option 1 in the main menu");
                System.out.println("Option 2 will only filter stored movies which has previous been added by option 1");
                System.out.println("Option 3 will delete all movies in the database");
                System.out.println("Option 4 will show this info");
                System.out.println("Option 5 will exit the program");
                searchMenu();
                break;

            case 5:
                System.exit(0);
                break;
        }

    }

    public boolean checkTitle(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Search by title: ");
        String apiString = scan.nextLine().trim();
        setApiString(apiString);
        System.out.println(getApiString());


        // System.out.println(api.getApiKey()); // Check the apiKey

        try {
            api.fetchURL(getApiString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            this.buffer = new Buffer(api);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.json = new JSON(buffer);
        return json.isSuccess();
    }


}
