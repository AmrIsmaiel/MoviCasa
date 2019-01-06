package com.Som3a.movicasa.UI.MovieDetailsActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Som3a.movicasa.Adapters.SimilarMovisAdapter;
import com.Som3a.movicasa.Data.DBHelper;
import com.Som3a.movicasa.Data.models.GenreResponse;
import com.Som3a.movicasa.Data.models.Results;
import com.Som3a.movicasa.Data.models.SearchResponse;
import com.Som3a.movicasa.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MovieDetails extends AppCompatActivity implements DetailsContract.detailsView {

    TextView tvTitle, tvOverview, tvReaseDate, tvGenres, tvNoSimilar;
    ImageView imgPoster, imgClose, imgBookmark;
    RecyclerView rvSimilarMovies;
    SimilarMovisAdapter adapter;
    int[] idGenres;
    int flag=0;
    List<String> idGenresResponseList;
    private static final String TAG = "DetailsActivity";
    private static String id;
    DetailsPresenter detailsPresenter;
    DBHelper dbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tvTitle = findViewById(R.id.tv_movieDetailsTitle);
        tvOverview = findViewById(R.id.tv_MovieOverView);
        imgBookmark = findViewById(R.id.imgBookmarkWhite);
        tvReaseDate = findViewById(R.id.tv_movieDetailsRelease);
        tvGenres = findViewById(R.id.tv_movieDetailsGenre);
        imgPoster = findViewById(R.id.img_movieDetailsPoster);
        rvSimilarMovies = findViewById(R.id.rv_similarList);
        imgClose = findViewById(R.id.img_close);
        tvNoSimilar = findViewById(R.id.tv_noSimilarMovies);
        dbHelper = new DBHelper(this);

        idGenresResponseList = new ArrayList<>();
        rvSimilarMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        rvSimilarMovies.setHasFixedSize(true);
        adapter = new SimilarMovisAdapter(new ArrayList<Results>(), MovieDetails.this);
        rvSimilarMovies.setAdapter(adapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idGenres = extras.getIntArray("genre");
            tvTitle.setText(extras.getString("title"));
            tvReaseDate.setText(extras.getString("release"));
            tvOverview.setText(extras.getString("overview"));
            Glide.with(this).
                    load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + extras.getString("poster"))
                    .into(imgPoster);
        } else {
            Glide.with(this).load(R.drawable.poster_placeholder).into(imgPoster);
        }
        id = Objects.requireNonNull(extras).getString("id");
        Log.d(TAG, "" + id);

        cursor = dbHelper.getBookmark(Integer.parseInt(id));
        if (cursor.getCount() > 0) {
            imgBookmark.setImageResource(R.drawable.ic_round_bookmark_orange_28px);
            flag = 1 ;
            Log.d(TAG,""+flag);


        } else {
            imgBookmark.setImageResource(R.drawable.ic_round_bookmark_border_36px);
            flag = 0 ;
        }

        imgBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    flag = 0;
                    imgBookmark.setImageResource(R.drawable.ic_round_bookmark_border_36px);
                    dbHelper.deleteBookmark(Integer.parseInt(id));
                }
                else if( flag == 0){
                    flag = 1;
                    imgBookmark.setImageResource(R.drawable.ic_round_bookmark_orange_28px);
                    dbHelper.insertBookmark(Integer.parseInt(id), String.valueOf(tvTitle.getText()));
                }
            }
        });


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupPresenter();
        getmoviesList();
        detailsPresenter.getGenresList();
    }

    private void setupPresenter() {
        detailsPresenter = new DetailsPresenter(this);
    }

    private void getmoviesList() {
        detailsPresenter.getSimilar(Integer.parseInt(id));
    }

    @Override
    public void saveGenresList(GenreResponse genreResponse) {
        for (int i = 0; i < genreResponse.getGenres().size(); i++) {
            idGenresResponseList.add(genreResponse.getGenres().get(i).getId());
        }
        for (int k = 0; k < idGenresResponseList.size(); k++) {
            for (int j = 0; j < idGenres.length; j++) {
                if (idGenres[j] == Integer.parseInt((idGenresResponseList.get(k)))) {
                    tvGenres.append((genreResponse.getGenres().get(k).getName()) + "\n\n");
                }
            }
        }

    }

    @Override
    public void displaySimilarMovies(SearchResponse searchResponse) {
        Log.d(TAG, searchResponse.getTotal_results());
        if (searchResponse.getTotal_results().equals("0")) {
            tvNoSimilar.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        adapter = new SimilarMovisAdapter(searchResponse.getResults(), MovieDetails.this);
        rvSimilarMovies.setAdapter(adapter);
    }


    @Override
    public void showToast(String str) {
        Toast.makeText(MovieDetails.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }
}
