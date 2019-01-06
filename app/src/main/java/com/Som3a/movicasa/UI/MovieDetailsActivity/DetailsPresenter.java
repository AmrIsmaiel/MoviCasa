package com.Som3a.movicasa.UI.MovieDetailsActivity;

import android.util.Log;

import com.Som3a.movicasa.Data.Network.NetworkClient;
import com.Som3a.movicasa.Data.Network.NetworkInterface;
import com.Som3a.movicasa.Data.models.GenreResponse;
import com.Som3a.movicasa.Data.models.SearchResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter implements DetailsContract.detailsPresenter {

    MovieDetails movieDetails;
    private String TAG = "DetailsPresenter";

    DetailsPresenter(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    public void getSimilar(int id) {
        Observable<SearchResponse> call = NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getSimilarMoviesbyId(id, "f0efd7f4dc4421af2f2d21919d46db84");
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        Log.d(TAG, "onNext" + searchResponse.getTotal_results());
                        movieDetails.displaySimilarMovies(searchResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error" + e);
                        e.printStackTrace();
                        movieDetails.displayError("Error Fetching Data");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Completed");
                    }
                });
    }

    @Override
    public void getGenresList() {
        Observable<GenreResponse> call = NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getGenres("f0efd7f4dc4421af2f2d21919d46db84");
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GenreResponse>() {
                    @Override
                    public void onNext(GenreResponse genreResponse) {
                        movieDetails.saveGenresList(genreResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error" + e);
                        e.printStackTrace();
                        movieDetails.displayError("Error fetching genres");

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Completed");
                    }
                });
    }
}
