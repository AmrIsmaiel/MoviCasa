package com.Som3a.movicasa.UI.SearchActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Som3a.movicasa.Adapters.MoviesAdapter;
import com.Som3a.movicasa.Data.models.Results;
import com.Som3a.movicasa.Data.models.SearchResponse;
import com.Som3a.movicasa.R;
import com.Som3a.movicasa.UI.MovieDetailsActivity.MovieDetails;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchContract.searchView {

    Toolbar toolbar;
    ProgressBar progressBar;
    RecyclerView rvSearchList;
    private static final String TAG = "Search Activity";
    private SearchView searchView;
    SearchPresenter searchPresenter;
    MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvSearchList = findViewById(R.id.rvSearchList);
        toolbar = findViewById(R.id.toolBar);
        progressBar = findViewById(R.id.progressBarSearch);
        setSupportActionBar(toolbar);
        rvSearchList.setLayoutManager(new LinearLayoutManager(this));
        rvSearchList.setHasFixedSize(true);
        adapter = new MoviesAdapter(new ArrayList<Results>(), SearchActivity.this);
        rvSearchList.setAdapter(adapter);
        setupMVP();
    }


    private void setupMVP() {
        searchPresenter = new SearchPresenter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Enter Movie name..");
        searchPresenter.getResultsBasedOnQuery(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(SearchActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayResult(SearchResponse searchResponse) {
        Log.d(TAG, searchResponse.getResults().get(0).getTitle());
        adapter.notifyDataSetChanged();
        adapter = new MoviesAdapter(searchResponse.getResults(), SearchActivity.this);
        rvSearchList.setAdapter(adapter);
        adapter.setOnItemClickListener(new MoviesAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, Results results) {
                Intent intent = new Intent(SearchActivity.this, MovieDetails.class);
                intent.putExtra("id", results.getId());
                intent.putExtra("title", results.getTitle());
                intent.putExtra("overview", results.getOverview());
                intent.putExtra("genre", results.getGenre_ids());
                intent.putExtra("poster", results.getPoster_path());
                intent.putExtra("release", results.getRelease_date());
                startActivity(intent);
            }
        });
    }

    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
