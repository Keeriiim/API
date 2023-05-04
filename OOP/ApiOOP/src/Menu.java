import java.util.Scanner;

public class Menu {
    private User you;
    private SQL sql;
    private API api;
    private Buffer buffer = null;
    private JSON json = null;
    Scanner scan = new Scanner(System.in);
    private String apiString;
    private String searchQuery ="";


    public void Menu() throws Exception {
        this.you = new User(); // Creates an object of the User class to access the API keys inside the OMDB.txt file located on the local computer
        //  System.out.println(you.getUserHome()); // Check the user home folder
        //  System.out.println(you.getProperties()); // Check the String inside OMDB.txt
        you.loadProperty(you.getProperties()); // stores the api key inside the properties object
        this.sql = new SQL(); // Creates an object of the SQL class
        sql.createTable(); // Creates a table in the database
        this.api = new API(you.getProperties());
        searchMenu(); // Calls the searchMenu method to start the program
    }

    // Relevant setters and getter
    public String getApiString() {
        return apiString;
    }

    public void setApiString(String apiString) {
        this.apiString = apiString;
    }



    public void searchMenu() throws Exception{ // Main methom to run the program
        System.out.println("\nWelcome to the movie database!");
        System.out.println("Please select an option:");
        System.out.println("1. Search for a movie");
        System.out.println("2. Show search history");
        System.out.println("3. Delete search history");
        System.out.println("4. Info");
        System.out.println("5. Exit");

        int MainMenuChoice = scan.nextInt();

        switch (MainMenuChoice) { // Switch case for the main menu
            case 1: // Search for a movie, if it's located in the database then it prints it, else it adds it to the database via API
                boolean unSuccesfullSearch = true;

                while(unSuccesfullSearch){ // Checks if the movies can be found via the API
                    if(checkTitle()  == false){
                        checkTitle();
                    }
                    else{
                        unSuccesfullSearch = false;
                    }
                }


                if(sql.isTableEmpty(sql.getConn())) { // Checks if the table is empty, if yes then it adds the movie to the database via API
                    System.out.println("\n" + "-------------------------------------------");
                    System.out.println("Your SearchQuery Was Added To Your Database");
                    System.out.println("-------------------------------------------");
                    json.printMovieInfo();
                    sql.toInsert(sql.getConn(), json); // Lägger till en rad i databasen
                }
                else if (!(sql.doesTableContain(sql.getConn(), json.getTitle()))){ // If the database is not empty then it checks if the movie is already in the database, if not then prints the info and adds it
                    System.out.println("\n" + "-------------------------------------------");
                    System.out.println("Your SearchQuery Was Added To Your Database");
                    System.out.println("-------------------------------------------");
                    json.printMovieInfo();
                    sql.toInsert(sql.getConn(), json);
                }
                else{ // Checks if the movie is already in the database, if yes, then it prints it
                    System.out.println("\n" + "------------------------------");
                    System.out.println("Stored Movies In Your Database");
                    System.out.println("------------------------------");
                    sql.printSelectedFromDatabase(sql.getConn(),"title" ,json.getTitle()); // Prints all movies matching the search title
                }

                sql.autoUpdateId(sql.getConn()); // Updates the id column in the database
                searchMenu();
                break;

            case 2: // Option to filter your database by title, year, genre, actors or director
                try {
                    API api = new API(you.getProperties());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                System.out.println("----------------------------------------");
                System.out.println("Filter your search history by:");
                System.out.println("1. Title\n" + "2. Year\n" + "3. Genre\n"  + "4. Actors\n" + "5. Director\n" + "6. Print whole database ordered by title");
                System.out.println("----------------------------------------");
                int filterChoice = scan.nextInt();

                switch (filterChoice) {
                    case 1:
                        System.out.println("Enter title:");
                        searchQuery = scan.next();
                        sql.printSelectedFromDatabase(sql.getConn(), "title", searchQuery);
                        break;

                    case 2:
                        System.out.println("Enter Year:");
                        searchQuery = scan.next();
                        sql.printSelectedFromDatabase(sql.getConn(), "year", searchQuery);
                        break;

                    case 3:
                        System.out.println("Enter Genre:");
                        searchQuery = scan.next();
                        sql.printSelectedFromDatabase(sql.getConn(), "genre", searchQuery);
                        break;

                    case 4:
                        System.out.println("Enter Actors:");
                        searchQuery = scan.next();
                        sql.printSelectedFromDatabase(sql.getConn(), "actors", searchQuery);
                        break;

                    case 5:
                        System.out.println("Enter Director:");
                        searchQuery = scan.next();
                        sql.printSelectedFromDatabase(sql.getConn(), "director", searchQuery);
                        break;

                    case 6:
                        sql.printAllMoviesInDatabase(sql.getConn());
                        break;
                }
                searchMenu();
                break;

            case 3:
                sql.resetTable(sql.getConn()); // Clears the database of any information
                searchMenu();
                break;

            case 4: // Prints info about the program
                System.out.println("This database is a local database that stores movies from the OMDB API");
                System.out.println("The database is created with the help of Java and SQL");
                System.out.println("The database will only contain movies that you have searched for via option 1 in the main menu");
                System.out.println("Option 2 will only filter stored movies which has previous been added by option 1");
                System.out.println("Option 3 will delete all movies in the database");
                System.out.println("Option 4 will show this info");
                System.out.println("Option 5 will exit the program");
                searchMenu();
                break;

            case 5: // Exits the program
                System.exit(0);
                break;
        }

    }

    public boolean checkTitle(){ // Checks if the movie can be found via the API
        Scanner scan = new Scanner(System.in);
        System.out.println("Search by title: ");
        String apiString = scan.nextLine().trim();
        setApiString(apiString);
        System.out.println(getApiString());

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
        return json.isSuccess(); // Returns true if the movie can be found via the API and stores it's information in json, else false
    }


}
