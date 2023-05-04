import org.json.JSONObject;

public class JSON {
    private Buffer buffer;
    private JSONObject movie;
    private String title;
    private String year;
    private String rated;
    private String runtime;
    private String actors;
    private String director;
    private String genre;
    private boolean success;


    public JSON(Buffer buffer) { // Stores the movie info in a JSON object
        this.buffer = buffer;
        try {
            this.movie = new JSONObject(buffer.getResponse().toString());
            this.title = movie.getString("Title");
            this.year = movie.getString("Year");
            this.genre = movie.getString("Genre");
            this.rated = movie.getString("Rated");
            this.runtime = movie.getString("Runtime");
            this.actors = movie.getString("Actors");
            this.director = movie.getString("Director");
            success = true;
        } catch (Exception e) {
            System.out.println("Could not find the title, try again");
            success = false;
        }
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

   // Relevant setters & getters
    public String getTitle() {
        return title;
    }
    public String getYear() {
        return year;
    }
    public String getGenre() {
        return genre;
    }
    public String getRated() {
        return rated;
    }
    public String getRuntime() {
        return runtime;
    }

    public String getActors() {
        return actors;
    }

    public String getDirector() {
        return director;
    }



    public void printMovieInfo(){ // prints the movie info
        System.out.println("Title: " + this.title);
        System.out.println("Year: " + this.year);
        System.out.println("Genre: " + this.genre);
        System.out.println("Rated: " + this.rated);
        System.out.println("Runtime: " + this.runtime);
        System.out.println("Actors: " + this.actors);
        System.out.println("Director: " + this.director);
    }
}
