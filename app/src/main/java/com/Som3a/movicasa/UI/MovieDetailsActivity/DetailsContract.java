package com.Som3a.movicasa.UI.MovieDetailsActivity;

import com.Som3a.movicasa.Data.models.GenreResponse;
import com.Som3a.movicasa.Data.models.SearchResponse;

public interface DetailsContract {

    interface detailsPresenter{
        void getSimilar(int id);
        void getGenresList();
    }

    interface detailsView{
        void saveGenresList(GenreResponse genreResponse);
        void displaySimilarMovies(SearchResponse searchResponse);
        void showToast(String str);

        void displayError(String s);
    }
}
