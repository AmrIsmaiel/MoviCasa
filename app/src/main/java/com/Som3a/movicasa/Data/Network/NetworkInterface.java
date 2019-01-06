package com.Som3a.movicasa.Data.Network;

import com.Som3a.movicasa.Data.models.GenreResponse;
import com.Som3a.movicasa.Data.models.MovieResponse;
import com.Som3a.movicasa.Data.models.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkInterface {


    @GET("movie/upcoming")
    Observable<MovieResponse> getMovies (@Query("api_key") String api_key);

    @GET("search/movie")
    Observable<SearchResponse> getMoviesSearch(@Query("api_key") String api_key, @Query("query") String q);

    @GET("movie/{movie_id}/similar")
    Observable<SearchResponse> getSimilarMoviesbyId(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
    @GET("genre/movie/list")
    Observable<GenreResponse> getGenres(@Query("api_key") String api_key);


}
