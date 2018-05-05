package findmovie.android.com.findmovie.Model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Musabir on 5/3/2018.
 */

public class MovieModel  extends RealmObject{
    @PrimaryKey
    String imdbID;
    String Title;
    String Plot;
    String Released;
    String Genre;
    String Writer;
    String Actors;
    String Poster;
    String imdbRating;
    String Awards;
    int isFav;

    public MovieModel(){

    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public int getIsFav() {
        return isFav;
    }

    public void setIsFav(int isFav) {
        this.isFav = isFav;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "imdbID='" + imdbID + '\'' +
                ", Title='" + Title + '\'' +
                ", Plot='" + Plot + '\'' +
                ", Released='" + Released + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Writer='" + Writer + '\'' +
                ", Actors='" + Actors + '\'' +
                ", Poster='" + Poster + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", Awards='" + Awards + '\'' +
                ", isFav=" + isFav +
                '}';
    }
}
