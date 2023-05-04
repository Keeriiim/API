import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class SQL {
    private JSON json;
    private int id;
    private String title;
    private String year;
    private String genre;
    private String rated;
    private String runtime;
    private String actors;
    private String director;

    private String dbName = "Movie Archive";
    private Connection conn;  // När vi kopplar upp oss skapas en session som hålls levande tills man stänger den man måste stänga den annars överhettas servern!


    public SQL() {
        try { // Protects against crashes
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.dbName + ".db"); // Opens a connection to a database
            System.out.println("The Database " + this.dbName + " is live!");
        } catch (SQLException e) { // Catches errors in the database connection
            System.out.println("Something went wrong with the database connection");
            System.out.println(e.getMessage());
        }
    }

    // Relevant getters and setters
    public Connection getConn() {
        return conn;
    }

    public boolean createTable() { // Creates a table in the database if it does not exist
        String sql = "CREATE TABLE IF NOT EXISTS movies ("
                + "id integer PRIMARY KEY AUTOINCREMENT,"
                + "Title varchar(50) NOT NULL,"
                + "Year varchar(50) NOT NULL,"
                + "Genre varchar(50) NOT NULL,"
                + "Actors varchar(50) NOT NULL,"
                + "Director varchar(50) NOT NULL,"
                + "Rated varchar(50) NOT NULL,"
                + "Runtime integer NOT NULL"
                + ")";
        boolean success = true;

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error, could not createTable");
            System.out.println(e.getMessage());
            success = false;
        }
        return success;
    }
    public void autoUpdateId(Connection conn){ // Updates the id column to be in order if movies are deleted
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE movies SET id = (SELECT COUNT(*) FROM movies m WHERE m.id < movies.id) + 1");
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public PreparedStatement toInsert(Connection conn, JSON json) throws SQLException { // Inserts a movie into the database
        PreparedStatement ps = conn.prepareStatement("INSERT INTO movies (Title,Year,Genre,Actors,Director,Rated,Runtime) values (?,?,?,?,?,?,?)");
        ps.setString(1, json.getTitle());
        ps.setString(2, json.getYear());
        ps.setString(3, json.getGenre());
        ps.setString(4, json.getActors());
        ps.setString(5, json.getDirector());
        ps.setString(6, json.getRated());
        ps.setString(7, json.getRuntime());
        ps.executeUpdate();
        return ps;
    }
    public boolean isTableEmpty(Connection conn) { // Checks if the table is empty
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM movies");
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            System.err.println("Error with isTableEmpty: " + e.getMessage());
        }
        return false;
    }
    public boolean doesTableContain(Connection conn, String qTitle) { // Checks if the table contains a movie
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM movies WHERE title LIKE '%" + qTitle +"%'");
            int count = 0;

            while(rs.next()){
                count++;
            }

            if(count > 0) {
                System.out.println(count + " movies found in doesTableContain");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error with doesTableContain: " + e.getMessage());
        }

        System.out.println("No movies found in doesTableContain");
        return false;
    }
    public boolean printSelectedFromDatabase(Connection conn, String filterOption, String searchQuery){ // Prints a movie from the database based on a filter option and a search query
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movies WHERE " + filterOption + " LIKE '%" + searchQuery + "%'");

            boolean found = false; // initialize found to false

            while (rs.next()) {
                String title = rs.getString("Title");
                String year = rs.getString("Year");
                String genre = rs.getString("Genre");
                String actors = rs.getString("Actors");
                String director = rs.getString("Director");
                String rated = rs.getString("Rated");
                String runtime = rs.getString("Runtime");

                System.out.println("\nTitle: " + title + "\n"
                        + "Year: " + year + "\n"
                        + "Genre: " + genre + "\n"
                        + "Actors: " + actors + "\n"
                        + "Director: " + director + "\n"
                        + "Rated: " + rated + "\n"
                        + "Runtime: " + runtime);

                System.out.println();

                found = true; // set found to true if any rows are found
            }

            if (!found) { // print "not found" if no rows are found
                System.out.println("No movies found.");
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Error with doesTableContain: " + e.getMessage());
        }

        System.out.println("No movies found in doesTableContain");
        return false;
    }
    public void printAllMoviesInDatabase(Connection conn) { // Prints all movies in the database
        PreparedStatement ps = null;

        if (isTableEmpty(conn)) {
            System.out.println("No movies in search history");
        } else {
            int count = 0; // Counts the amount of movies in the database
            try {
                System.out.println();
                System.out.println("-----------------------");
                System.out.println("Movies sorted by Title:");
                System.out.println("-----------------------");

                ps = conn.prepareStatement("SELECT * FROM movies ORDER BY title");
                ResultSet rs = ps.executeQuery();// kopierar info från db till rs

                while (rs.next()) {
                    String title = rs.getString("title");
                    String year = rs.getString("year");
                    String genre = rs.getString("genre");
                    String actors = rs.getString("actors");
                    String director = rs.getString("director");
                    String rated = rs.getString("rated");
                    String runtime = rs.getString("runtime");

                    System.out.println("\nTitle: " + title + "\n"
                            + "Year: " + year + "\n"
                            + "Genre: " + genre + "\n"
                            + "Actors: " + actors + "\n"
                            + "Director: " + director + "\n"
                            + "Rated: " + rated + "\n"
                            + "Runtime: " + runtime);

                    System.out.println();
                    count++;
                }
            } catch (SQLException e) {
                System.out.println("Något gick snett med printDatabas");
                System.out.println(e.getMessage());
            }
            System.out.println("Total amount of movies in the database: " + count);
        }
    }
    public void resetTable(Connection conn){ // Resets the table
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM movies");
            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Table has been cleared." + "\n");
            }

        } catch (SQLException e) {
            System.out.println("Error, could not resetTable");
            System.out.println(e.getMessage());
        }
    }
}