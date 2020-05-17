package com.pramu.mymovies.Movies;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pramu.mymovies.Favorit.MyFavoritFragment;
import com.pramu.mymovies.Helper.MyMoviesHelper;
import com.pramu.mymovies.MainActivity;
import com.pramu.mymovies.R;

import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.DESCRIPTION_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.PHOTO_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.RATING_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.RELEASE_DATE_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.TITLE_MOVIES;

public class MyMoviesDetail extends AppCompatActivity implements View.OnClickListener{


    public static final String EXTRA_MOVIE = "test_extra_movie";
    TextView tViewjudul,tViewreleaseFilm,
     tViewskorPengunjung,tViewOverview;
    ImageView imagePoster;
    ImageButton btnFavorit;
    MyMoviesHelper myMoviesHelper;

    int pos;
    private ProgressBar progressBar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movies_detail);

        tViewjudul = findViewById(R.id.txt_judul);
        tViewreleaseFilm = findViewById(R.id.statusFilm);
        tViewskorPengunjung = findViewById(R.id.skorPengunjung);
        tViewOverview = findViewById(R.id.overview);
        imagePoster = findViewById(R.id.posterFilm);
        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();
        btnFavorit = findViewById(R.id.btnfav);
        btnFavorit.setOnClickListener(this);


        MyMovies movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        assert movie != null;
        tViewjudul.setText(movie.getTitle());
        tViewreleaseFilm.setText(movie.getRelease());
        tViewOverview.setText(movie.getOverview());
        tViewskorPengunjung.setText(movie.getVote());
        String url = "https://image.tmdb.org/t/p/w500" + movie.getPoster();
        Glide.with(MyMoviesDetail.this).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imagePoster);
        myMoviesHelper = MyMoviesHelper.getInstance(getApplicationContext());
        myMoviesHelper.open();
        String moviesTitle = movie.getTitle();
        Log.d("test","onCreate: "+moviesTitle+myMoviesHelper.getOne(moviesTitle));
        if (myMoviesHelper.getOne(moviesTitle)){
            btnFavorit.setColorFilter(Color.argb(255, 255, 0, 0));
            pos = 0;
        }else if (!myMoviesHelper.getOne(moviesTitle)){
            btnFavorit.setColorFilter(Color.argb(255, 255, 255, 255));
            pos = 1;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onClick(View view) {
        MyMovies movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (pos==1){

            ContentValues values = new ContentValues();
            assert movie != null;
            values.put(TITLE_MOVIES, movie.getTitle());
            values.put(DESCRIPTION_MOVIES, movie.getOverview());
            values.put(PHOTO_MOVIES, movie.getPoster());
            values.put(RELEASE_DATE_MOVIES, movie.getRelease());
            values.put(RATING_MOVIES, movie.getVote());
            long result = MyMoviesHelper.insert(values);

            btnFavorit.setColorFilter(Color.argb(255, 255, 0, 0));
            pos = 0;

            if (result > 0) {
                Toast.makeText(this, getString(R.string.favorit), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, getString(R.string.failedfavorit), Toast.LENGTH_SHORT).show();
            }

        }else if(pos == 0){

            assert movie != null;
            long result = myMoviesHelper.deleteByTitle(movie.getTitle());
            if (result > 0) {
                Toast.makeText(MyMoviesDetail.this, getString(R.string.unfavorit), Toast.LENGTH_SHORT).show();
                pos = 1;
                btnFavorit.setColorFilter(Color.argb(255, 255, 255, 255));
            } else {
                Toast.makeText(MyMoviesDetail.this, getString(R.string.unfavorit), Toast.LENGTH_SHORT).show();
            }

        }

    }
}
