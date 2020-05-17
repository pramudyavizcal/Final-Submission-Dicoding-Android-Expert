package com.pramu.mymovies.Movies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pramu.mymovies.R;

import java.util.ArrayList;

public class MyMoviesAdapter extends RecyclerView.Adapter<MyMoviesAdapter.MovieViewHolder> {
    private ArrayList<MyMovies> movies = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

   public void setData(ArrayList<MyMovies> items) {
        movies.clear();
        movies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_mymovies, viewGroup,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder,final int i) {
        movieViewHolder.txtjudul.setText(movies.get(i).getTitle());
        movieViewHolder.txtoverview.setText(movies.get(i).getOverview());
        movieViewHolder.txtwaktuTayang.setText(movies.get(i).getRelease());
        movieViewHolder.txtskorPengunjung.setText(movies.get(i).getVote());
        String url = "https://image.tmdb.org/t/p/w500" + movies.get(i).getPoster();
        Glide.with(movieViewHolder.itemView.getContext())
                .load(url)
                .into(movieViewHolder.imgPosterFilm);

        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(movies.get(movieViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView txtjudul,txtoverview, txtwaktuTayang, txtskorPengunjung;
        private ImageView imgPosterFilm;

        public MovieViewHolder(View view) {
            super(view);
            txtjudul = view.findViewById(R.id.txt_judul);
            txtoverview = view.findViewById(R.id.overview);
            imgPosterFilm = view.findViewById(R.id.posterFilm);
            txtwaktuTayang = view.findViewById(R.id.tanggalRilis);
            txtskorPengunjung = view.findViewById(R.id.skorPengunjung);

        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(MyMovies data);
    }
}