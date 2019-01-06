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


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    List<Results>  movieList ;
    Context context;
    private onItemClickListener mItemClickListener;

    public void setOnItemClickListener(onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public MoviesAdapter(List<Results> movieList, Context context) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent,false);
        MoviesHolder movieHolder  = new MoviesHolder(v);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder( MoviesHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvOverview.setText(movieList.get(position).getOverview());
        holder.tvReleaseDate.setText(movieList.get(position).getRelease_date());
        if(movieList.get(position).getPoster_path() == null){
            Glide.with(holder.ivMovie).load(R.drawable.poster_placeholder).into(holder.ivMovie);
        }
        else {
            Glide.with(holder.ivMovie).load("https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movieList.get(position).getPoster_path()).into(holder.ivMovie);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvOverview,tvReleaseDate;
        ImageView ivMovie;

        public MoviesHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movieTitle);
            tvOverview = itemView.findViewById(R.id.tv_OverView);
            tvReleaseDate = itemView.findViewById(R.id.tv_movieRelease);
            ivMovie = itemView.findViewById(R.id.img_moviePoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClickListener(v, getAdapterPosition(), movieList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
    public interface onItemClickListener {
        void onItemClickListener(View view, int position, Results results);
    }
}

