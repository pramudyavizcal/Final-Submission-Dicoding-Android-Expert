package com.pramu.mymovies.TV;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pramu.mymovies.Helper.MyMoviesHelper;
import com.pramu.mymovies.Helper.MyTVHelper;
import com.pramu.mymovies.Movies.MyMovies;
import com.pramu.mymovies.Movies.MyMoviesDetail;
import com.pramu.mymovies.R;

import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.DESCRIPTION_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.PHOTO_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.RATING_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.RELEASE_DATE_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyMoviesColumns.TITLE_MOVIES;
import static com.pramu.mymovies.Database.DatabaseContract.MyTVColumns.DESCRIPTION_TV;
import static com.pramu.mymovies.Database.DatabaseContract.MyTVColumns.PHOTO_TV;
import static com.pramu.mymovies.Database.DatabaseContract.MyTVColumns.RATING_TV;
import static com.pramu.mymovies.Database.DatabaseContract.MyTVColumns.RELEASE_DATE_TV;
import static com.pramu.mymovies.Database.DatabaseContract.MyTVColumns.TITLE_TV;

public class MyTVDetail extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TV = "test_extra_tv";

    TextView tViewjudulTV,tViewreleaseTV,
            tViewskorPengunjungTV,
            tViewOverviewTV;
    ImageButton btnFavorit;
    MyTVHelper myTVHelper;

    int pos;
    ImageView backdrop_path;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tvdetail);

        tViewjudulTV = findViewById(R.id.txt_judul);
        tViewskorPengunjungTV = findViewById(R.id.skorPengunjung);
        tViewreleaseTV = findViewById(R.id.statusTV);
        tViewOverviewTV = findViewById(R.id.overview);
        backdrop_path = findViewById(R.id.posterTV);
        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();
        btnFavorit = findViewById(R.id.btnfav);
        btnFavorit.setOnClickListener(this);


        MyTV tv = getIntent().getParcelableExtra(EXTRA_TV);
        assert tv != null;
        tViewjudulTV.setText(tv.getJudulTV());
        tViewOverviewTV.setText(tv.getOverviewTV());
        tViewreleaseTV.setText(tv.getrelease());
        tViewskorPengunjungTV.setText(tv.getSkorPengunjungTV());

        String url = "https://image.tmdb.org/t/p/w500" + tv.getposterTV();
        Glide.with(MyTVDetail.this)
                .load(url)
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
                })
                .into(backdrop_path);
        myTVHelper = MyTVHelper.getInstance(getApplicationContext());
        myTVHelper.open();
        String tvTitle = tv.getJudulTV();
        Log.d("test","onCreate: "+tvTitle+myTVHelper.getOne(tvTitle));
        if (myTVHelper.getOne(tvTitle)){
            btnFavorit.setColorFilter(Color.argb(255, 255, 0, 0));
            pos = 0;
        }else if (!myTVHelper.getOne(tvTitle)){
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
        switch (item.getItemId()) {
            case android.R.id.home:
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
        MyTV tv = getIntent().getParcelableExtra(EXTRA_TV);

        if (pos==1){

            ContentValues values = new ContentValues();
            assert tv != null;
            values.put(TITLE_TV, tv.getJudulTV());
            values.put(DESCRIPTION_TV, tv.getOverviewTV());
            values.put(PHOTO_TV, tv.getposterTV());
            values.put(RELEASE_DATE_TV, tv.getrelease());
            values.put(RATING_TV, tv.getrelease());
            long result = MyTVHelper.insert(values);

            btnFavorit.setColorFilter(Color.argb(255, 255, 0, 0));
            pos = 0;

            if (result > 0) {
                Toast.makeText(this, getString(R.string.favorit), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, getString(R.string.failedfavorit), Toast.LENGTH_SHORT).show();
            }

        }else if(pos == 0){

            assert tv != null;
            long result = myTVHelper.deleteByTitle(tv.getJudulTV());
            if (result > 0) {
                Toast.makeText(MyTVDetail.this,getString(R.string.unfavorit) , Toast.LENGTH_SHORT).show();
                pos = 1;
                btnFavorit.setColorFilter(Color.argb(255, 255, 255, 255));
            } else {
                Toast.makeText(MyTVDetail.this, getString(R.string.unfavorit), Toast.LENGTH_SHORT).show();
            }

        }

    }
}
