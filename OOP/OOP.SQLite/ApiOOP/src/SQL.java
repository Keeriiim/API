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
    private String actor;
    private String director;

    private String dbName = "Movie Archive";
    private Connection conn;  // När vi kopplar upp oss skapas en session som hålls levande tills man stänger den man måste stänga den annars överhettas servern!


    public SQL() {
        try { // Skyddar mot krasch
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + this.dbName + ".db"); // Öppnar en anslutning till en databas
            System.out.println("Databasen " + this.dbName + " är live!");
        } catch (SQLException e) { // fångar error i databaskopplingen
            // throw new RuntimeException(e);
            System.out.println("Något gick snett med databaskopplingen");
            System.out.println(e.getMessage());
        }

    }

    public void setJson(JSON json){
        this.json = json;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDbName() {
        return dbName;
    }

    public Connection getConn() {
        return conn;
    }



    public boolean createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS movies ("
                + "id integer PRIMARY KEY AUTOINCREMENT,"
                + "Title varchar(50) NOT NULL,"
                + "Year varchar(50) NOT NULL,"
                + "Rated varchar(50) NOT NULL,"
                + "Runtime integer NOT NULL,"
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


    public void autoUpdateId(Connection conn){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE movies SET id = (SELECT COUNT(*) FROM movies m WHERE m.id < movies.id) + 1");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public PreparedStatement toInsert(Connection conn, JSON json) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO movies (Title,Year,Rated,Runtime) values (?,?,?,?)");
        ps.setString(1, json.getTitle());
        ps.setString(2, json.getYear());
        ps.setString(3, json.getRated());
        ps.setString(4, json.getRuntime());
        ps.executeUpdate();
        return ps;
    }











    public boolean isTableEmpty(Connection conn) {
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

    public boolean doesTableContain(Connection conn, String qTitle) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM movies WHERE title LIKE '%" + qTitle +"%'");
            int count = 0;
            while(rs.next()){
                count++;
               //  String title = rs.getString("title");
               //  System.out.println(title);
            }
            System.out.println(count + " movies found in doesTableContain");
                return count == 0;

        } catch (SQLException e) {
            System.err.println("Error with doesTableContain: " + e.getMessage());
        }
        System.out.println("No movies found in doesTableContain");
        return false;
    }

    public boolean printSelectedFromDatabase(Connection conn, String qTitle) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movies WHERE title LIKE '%" + qTitle +"%'");
            while(rs.next()){
            //    String id = rs.getString("id");
                String title = rs.getString("Title");
                String year = rs.getString("Year");
                String rated = rs.getString("Rated");
                String runtime = rs.getString("Runtime");
                System.out.println("Title: " + title + "\n"
                        + "Year: " + year + "\n"
                        + "Rated: " + rated + "\n"
                        + "Runtime: " + runtime);

                System.out.println();
            }
            return true;

        } catch (SQLException e) {
            System.err.println("Error with doesTableContain: " + e.getMessage());
        }
        System.out.println("No movies found in doesTableContain");
        return false;
    }




    public void printMoviesInDatabase(Connection conn) {
        PreparedStatement ps = null;

        if (isTableEmpty(conn)) {
            System.out.println("No movies in search history");
        } else {
            try {
                System.out.println();
                ps = conn.prepareStatement("SELECT id, Title, Year, Rated, Runtime FROM movies ORDER BY title");
                ResultSet rs = ps.executeQuery();// kopierar info från db till rs
                System.out.println("Movies sorted by Title: ");

                while (rs.next()) {
                    String id = rs.getString("id");
                    String title = rs.getString("Title");
                    String year = rs.getString("Year");
                    String rated = rs.getString("Rated");
                    String runtime = rs.getString("Runtime");
                    System.out.println("id: " + id + "\n"
                            + "Title: " + title + "\n"
                            + "Year: " + year + "\n"
                            + "Rated: " + rated + "\n"
                            + "Runtime: " + runtime);

                    System.out.println();

                    // alternativt
                    // System.out.println(rs.getString("name") + " " + rs.getString("lastName") + " " + rs.getString("age"));

                }
            } catch (SQLException e) {
                System.out.println("Något gick snett med printDatabas");
                System.out.println(e.getMessage());
            }
        }
    }



    public void resetTable(Connection conn) {

        PreparedStatement ps = null;   // Radera en rad från tabellen baserat på dess id
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



    public void findMovieInDatabaseByActor(String search) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movies WHERE actors LIKE ?");
            pstmt.setString(1, "%" + search + "%");
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                System.out.println("Title: " + result.getString("title"));
                System.out.println("Year: " + result.getString("year"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Director: " + result.getString("director"));
                System.out.println("Actors: " + result.getString("actors"));
            }
        } catch (SQLException e) {
            System.out.println("No movies found");
            System.out.println(e.getMessage());
        }
    }
    public void findMovieInDatabaseByYear(String search) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movies WHERE year LIKE ?");
            pstmt.setString(1, "%" + search + "%");
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                System.out.println("Title: " + result.getString("title"));
                System.out.println("Year: " + result.getString("year"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Director: " + result.getString("director"));
                System.out.println("Actors: " + result.getString("actors"));
            }
        } catch (SQLException e) {
            System.out.println("No movies found");
            System.out.println(e.getMessage());
        }
    }
    public void findMovieInDatabaseByDirector(String search) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movies WHERE director LIKE ?");
            pstmt.setString(1, "%" + search + "%");
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                System.out.println("Title: " + result.getString("title"));
                System.out.println("Year: " + result.getString("year"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Director: " + result.getString("director"));
                System.out.println("Actors: " + result.getString("actors"));
            }
        } catch (SQLException e) {
            System.out.println("No movies found");
            System.out.println(e.getMessage());
        }
    }



}