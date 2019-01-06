package com.Som3a.movicasa.UI.MainActivity;

import android.util.Log;

import com.Som3a.movicasa.Data.Network.NetworkClient;
import com.Som3a.movicasa.Data.Network.NetworkInterface;
import com.Som3a.movicasa.Data.models.MovieResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenter implements MainContract.mainPresenter {

    MainActivity mainActivity;
    private String TAG = "MainPresenter";

    MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void getMovies() {
        getObservable().subscribeWith(getObserver());
    }

    private Observable<MovieResponse> getObservable() {
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getMovies("f0efd7f4dc4421af2f2d21919d46db84")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieResponse> getObserver() {
        return new DisposableObserver<MovieResponse>() {

            @Override
            public void onNext(@NonNull MovieResponse movieResponse) {
                Log.d(TAG, "OnNext" + movieResponse.getTotal_results());
                mainActivity.displayMovies(movieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                mainActivity.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                mainActivity.hideProgressBar();
            }
        };
    }
}
