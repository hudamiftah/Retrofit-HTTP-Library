package dev.mif.exp.retrofithttplibrary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import butterknife.BindString;
import dev.mif.exp.retrofithttplibrary.BuildConfig;
import dev.mif.exp.retrofithttplibrary.R;
import dev.mif.exp.retrofithttplibrary.adapter.MoviesAdapter;
import dev.mif.exp.retrofithttplibrary.model.Movie;
import dev.mif.exp.retrofithttplibrary.model.MovieResponse;
import dev.mif.exp.retrofithttplibrary.rest.ApiClient;
import dev.mif.exp.retrofithttplibrary.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please otain your API KEY first", Toast.LENGTH_SHORT).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                List<Movie> movieList = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movieList, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + movieList.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        /*
         * Retrofit will download and parse the API data on a background thread,
         * and then return the results back to the UI thread via the onResponse or onFailure method.
         */
    }
}


































