package com.Som3a.movicasa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Som3a.movicasa.Data.models.Results;
import com.Som3a.movicasa.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class SimilarMovisAdapter extends RecyclerView.Adapter<SimilarMovisAdapter.SimilarMoviesHolder> {

    private List<Results> movieList ;
    private Context context;

    public SimilarMovisAdapter (List<Results> movieList, Context context) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public SimilarMoviesHolder onCreateViewHolder( ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_similar_movie, viewGroup,false);
        return new SimilarMoviesHolder(v);
    }

    @Override
    public void onBindViewHolder( SimilarMoviesHolder holder, int position) {
        if(movieList.get(position).getPoster_path() != null){
            Glide.with(holder.ivMovie)
                    .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+movieList.get(position).getPoster_path())
                    .into(holder.ivMovie);
            holder.tvTitle.setText(movieList.get(position).getTitle());

        }
        else{

            Glide.with(holder.ivMovie)
                    .load(R.drawable.poster_placeholder)
                    .into(holder.ivMovie);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class SimilarMoviesHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivMovie;

        public SimilarMoviesHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_similarTitle);
            ivMovie = itemView.findViewById(R.id.img_similarPoster);

        }

    }
}
