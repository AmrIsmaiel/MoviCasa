package com.Som3a.movicasa.UI.SearchActivity;

import android.support.v7.widget.SearchView;

import com.Som3a.movicasa.Data.models.SearchResponse;

public interface SearchContract {

    interface searchPresenter {
        void getResultsBasedOnQuery(SearchView searchView);

    }

    interface searchView {
        void showToast(String str);

        void displayResult(SearchResponse searchResponse);

        void displayError(String s);

        void showProgressBar();

        void hideProgressBar();
    }
}
