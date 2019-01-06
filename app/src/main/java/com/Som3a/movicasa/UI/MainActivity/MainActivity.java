package com.Som3a.movicasa.UI.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Som3a.movicasa.Adapters.MoviesAdapter;
import com.Som3a.movicasa.Data.models.MovieResponse;
import com.Som3a.movicasa.Data.models.Results;
import com.Som3a.movicasa.R;
import com.Som3a.movicasa.UI.BookMarks.BookMarks;
import com.Som3a.movicasa.UI.MovieDetailsActivity.MovieDetails;
import com.Som3a.movicasa.UI.SearchActivity.SearchActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainContract.mainView {

    private static final String TAG = "MainActivity";
    RecyclerView rvMainList;
    ProgressBar progressBar;
    int[] list = new int[0];
    Toolbar toolbar;
    MoviesAdapter adapter;
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMainList = findViewById(R.id.rvMainList);
        progressBar = findViewById(R.id.progressBarMain);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        rvMainList.setLayoutManager(new LinearLayoutManager(this));
        rvMainList.setHasFixedSize(true);
        adapter = new MoviesAdapter(new ArrayList<Results>(), MainActivity.this);
        rvMainList.setAdapter(adapter);

        setupMVP();
        getMovieList();
    }


    private void setupMVP() {
        mainActivityPresenter = new MainActivityPresenter(this);
    }

    private void getMovieList() {

        mainActivityPresenter.getMovies();

    }


    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
        if (movieResponse != null) {
            Log.d(TAG, movieResponse.getResults().get(0).getTitle());
            adapter.notifyDataSetChanged();
            adapter = new MoviesAdapter(movieResponse.getResults(), MainActivity.this);
            rvMainList.setAdapter(adapter);

            adapter.setOnItemClickListener(new MoviesAdapter.onItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position, Results results) {
                    list = results.getGenre_ids();
                    Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                    intent.putExtra("id", results.getId());
                    intent.putExtra("title", results.getTitle());
                    intent.putExtra("overview", results.getOverview());
                    intent.putExtra("genre", list);
                    intent.putExtra("poster", results.getPoster_path());
                    intent.putExtra("release", results.getRelease_date());
                    startActivity(intent);
                }
            });
        } else {
            Log.d(TAG, "Movies response null");
        }
    }

    @Override
    public void displayError(String e) {

        showToast(e);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.ic_search) {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        } else if (id == R.id.ic_bookmarkDir) {
            Intent intent = new Intent(MainActivity.this, BookMarks.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
