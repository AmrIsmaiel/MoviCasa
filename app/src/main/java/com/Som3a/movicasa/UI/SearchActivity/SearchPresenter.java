package com.Som3a.movicasa.UI.SearchActivity;

import android.support.v7.widget.SearchView;
import android.util.Log;

import com.Som3a.movicasa.Data.Network.NetworkClient;
import com.Som3a.movicasa.Data.Network.NetworkInterface;
import com.Som3a.movicasa.Data.models.MovieResponse;
import com.Som3a.movicasa.Data.models.SearchResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchPresenter implements SearchContract.searchPresenter {

    SearchContract.searchView searchActivity;
    private String TAG = "SearchPresenter";

    public SearchPresenter(SearchContract.searchView searchActivity) {
        this.searchActivity = searchActivity;
    }


    @Override
    public void getResultsBasedOnQuery(SearchView searchView) {

        getObservableQuery(searchView)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<SearchResponse>>() {
                    @Override
                    public Observable<SearchResponse> apply(@NonNull String s) throws Exception {
                        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                                .getMoviesSearch("f0efd7f4dc4421af2f2d21919d46db84",s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());


    }

    private Observable<String> getObservableQuery(SearchView searchView){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }

    public DisposableObserver<SearchResponse> getObserver(){
        return new DisposableObserver<SearchResponse>() {

            @Override
            public void onNext(@NonNull SearchResponse searchResponse) {
                Log.d(TAG,"OnNext"+searchResponse.getTotal_results());
                searchActivity.displayResult(searchResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                searchActivity.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

}
