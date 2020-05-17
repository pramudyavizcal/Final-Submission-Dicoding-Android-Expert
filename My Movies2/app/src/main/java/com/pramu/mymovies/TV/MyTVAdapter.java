package com.pramu.mymovies.TV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pramu.mymovies.R;

import java.io.BufferedReader;
import java.util.ArrayList;

public class MyTVAdapter extends RecyclerView.Adapter<MyTVAdapter.TVViewHolder> {
    private ArrayList<MyTV> tvs = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    public void setData(ArrayList<MyTV> items) {
        tvs.clear();
        tvs.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyTVAdapter.TVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_mytv, viewGroup,false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVViewHolder tvViewHolder, int i) {
        tvViewHolder.txtjudul.setText(tvs.get(i).getJudulTV());
        tvViewHolder.txtwaktuTayang.setText(tvs.get(i).getrelease());
        tvViewHolder.txtoverview.setText(tvs.get(i).getOverviewTV());
        tvViewHolder.txtskorPengunjung.setText(tvs.get(i).getSkorPengunjungTV());
        String url = "https://image.tmdb.org/t/p/w500" + tvs.get(i).getposterTV();
        Glide.with(tvViewHolder.itemView.getContext())
                .load(url)
                .into(tvViewHolder.imgPosterTV);

        tvViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(tvs.get(tvViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }

    public class TVViewHolder extends RecyclerView.ViewHolder {
        private TextView txtjudul;
        private TextView txtwaktuTayang;
        private TextView txtskorPengunjung,txtoverview;
        private ImageView imgPosterTV;

        public TVViewHolder(@NonNull View itemView) {
            super(itemView);
            txtjudul = itemView.findViewById(R.id.txt_judul);
            imgPosterTV = itemView.findViewById(R.id.posterTV);
            txtwaktuTayang = itemView.findViewById(R.id.tanggalRilis);
            txtoverview = itemView.findViewById(R.id.overview);
            txtskorPengunjung = itemView.findViewById(R.id.skorPengunjung);
        }
    }
    public interface OnItemClickCallback {
        void onItemClicked(MyTV data);
    }
}
