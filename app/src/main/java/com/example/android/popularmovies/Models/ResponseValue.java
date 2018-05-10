package com.example.android.popularmovies.Models;

/**
 * Created by haya on 20/04/2018.
 */

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;

        import io.realm.RealmList;
        import io.realm.RealmObject;

public class ResponseValue  extends RealmObject {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    @SerializedName("results")
    @Expose
    private RealmList<Movie> movies = null;

    public Integer getPage() {
        return page;
    }
    public RealmList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(RealmList<Movie> movies) {
        this.movies = movies;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }



}

