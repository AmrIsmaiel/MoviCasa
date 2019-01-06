package com.Som3a.movicasa.UI.MainActivity;

import com.Som3a.movicasa.Data.models.MovieResponse;


public interface MainContract {

    interface mainPresenter {
        void getMovies();
    }

    interface mainView {
        void showToast(String s);

        void showProgressBar();

        void hideProgressBar();

        void displayMovies(MovieResponse movieResponse);

        void displayError(String s);
    }

}
