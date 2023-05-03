import org.json.JSONObject;

public class JSON {
    private Buffer buffer;
    private JSONObject movie;
    private String title;
    private String year;
    private String rated;
    private String runtime;
    private String actor;
    private String director;
    private String genre;
    private boolean success;


    public JSON(Buffer buffer) {
        this.buffer = buffer;
        try {
            this.movie = new JSONObject(buffer.getResponse().toString());
            this.title = movie.getString("Title");
            this.year = movie.getString("Year");
            this.genre = movie.getString("Genre");
            this.rated = movie.getString("Rated");
            this.runtime = movie.getString("Runtime");
            this.actor = movie.getString("Actors");
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

    public String getActor() {
        return actor;
    }

    public String getDirector() {
        return director;
    }



    public void printMovieInfo(){
        System.out.println("Title: " + this.title);
        System.out.println("Year: " + this.year);
        System.out.println("Genre: " + this.genre);
        System.out.println("Rated: " + this.rated);
        System.out.println("Runtime: " + this.runtime);
        System.out.println("Actors: " + this.actor);
        System.out.println("Director: " + this.director);
    }
}
