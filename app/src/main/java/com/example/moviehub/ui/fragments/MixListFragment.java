package com.example.moviehub.ui.fragments;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviehub.network.DiscoverRequest;
import com.example.moviehub.network.MoviesRequest;
import com.example.moviehub.network.NetworkConstraint;
import com.example.moviehub.network.PersonRequest;
import com.example.moviehub.network.RetrofitClient;
import com.example.moviehub.network.TvShowRequest;
import com.example.moviehub.R;
import com.example.moviehub.adapter.SimilarMovieAdapter;
import com.example.moviehub.model.DiscoverGenre;
import com.example.moviehub.model.PersonMovies;
import com.example.moviehub.model.Result;
import com.example.moviehub.model.SimilarMovie;
import com.example.moviehub.utils.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MixListFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    String s = "";

    Type.MixListType mixListType;
    Type.MovieOrTvshow movieOrTvshow;

    public MixListFragment(String s, Type.MixListType mixListType, Type.MovieOrTvshow movieOrTvshow) {
        this.s = s;
        this.mixListType = mixListType;
        this.movieOrTvshow = movieOrTvshow;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_similar, container, false);
        recyclerView = view.findViewById(R.id.similarmovierecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // Inflate the layout for this fragment


        switch (mixListType) {
            case GENRE:
                if (movieOrTvshow == Type.MovieOrTvshow.MOVIE) {
                    getGenerMovie();
                    Log.i("fffsccd", "onCreateView: "+3423);
                } else
                    getGenreTvshow();
                break;
            case SIMILAR:
                if (movieOrTvshow == Type.MovieOrTvshow.MOVIE)
                    getSimilarMovie();
                else
                    getSimilarTvsgow();

                break;
            case CREDIT:
                if (movieOrTvshow == Type.MovieOrTvshow.MOVIE)
                    getCreditMovie();
                else
                    getCreditTvshow();
                Log.i("acscsfcs", "getCreditTvshow: " + 233);
                break;

        }


        return view;
    }

    private void getSimilarTvsgow() {
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(TvShowRequest.class)
                .getsimilartv(s, NetworkConstraint.key)
                .enqueue(new Callback<SimilarMovie>() {
                    @Override
                    public void onResponse(Call<SimilarMovie> call, Response<SimilarMovie> response) {
                        if (response.body() != null) {
                            SimilarMovieAdapter adapter = new SimilarMovieAdapter(getContext(), response.body().getResults(), Type.MovieOrTvshow.TVSHOW);
                            recyclerView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onFailure(Call<SimilarMovie> call, Throwable t) {

                    }
                });
    }


    private void getGenreTvshow() {
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(DiscoverRequest.class)
                .getDiscovertv(NetworkConstraint.key, s)
                .enqueue(new Callback<DiscoverGenre>() {
                    @Override
                    public void onResponse(Call<DiscoverGenre> call, Response<DiscoverGenre> response) {
                        if (response.body() != null) {
                            SimilarMovieAdapter adapter = new SimilarMovieAdapter(getContext(), response.body().getResults(), Type.MovieOrTvshow.TVSHOW);
                            recyclerView.setAdapter(adapter);
                        }


                    }

                    @Override
                    public void onFailure(Call<DiscoverGenre> call, Throwable t) {

                    }
                });
    }

    private void getCreditTvshow() {


        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(PersonRequest.class)
                .getPersonTvshow(s, NetworkConstraint.key)
                .enqueue(new Callback<PersonMovies>() {
                    @Override
                    public void onResponse(Call<PersonMovies> call, Response<PersonMovies> response) {

                        if (response.body() != null) {
                            Log.i("sdsff", "onResponse: " + response.toString());
                            Log.i("sdsff", "onResponse: " + response.body());

                            PersonMovies personMovies = response.body();

                            List<Result> casts = personMovies.getCast();
                            List<Result> crews = personMovies.getCrew();

                            List<Result> credit = new ArrayList<>();
                            credit.addAll(casts);
                            credit.addAll(crews);

                            recyclerView.setAdapter(new SimilarMovieAdapter(getContext(), credit, movieOrTvshow));

                        }

                    }

                    @Override
                    public void onFailure(Call<PersonMovies> call, Throwable t) {
                        Log.i("sdsff", "onFailure: " + t.getMessage());

                    }
                });


    }

    private void getCreditMovie() {
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(PersonRequest.class)
                .getPersonMovieTvshowRequest(s, NetworkConstraint.key)
                .enqueue(new Callback<PersonMovies>() {
                    @Override
                    public void onResponse(Call<PersonMovies> call, Response<PersonMovies> response) {

                        if (response.body() != null) {
                            Log.i("adsfcz", "onResponse: " + response.body());
                            Log.i("adsfcz", "onResponse: " + response.toString());


                            PersonMovies personMovies = response.body();

                            List<Result> casts = personMovies.getCast();
                            List<Result> crews = personMovies.getCrew();

                            List<Result> credit = new ArrayList<>();
                            credit.addAll(casts);
                            credit.addAll(crews);


                            recyclerView.setAdapter(new SimilarMovieAdapter(getContext(), credit, movieOrTvshow));

                        }

                    }

                    @Override
                    public void onFailure(Call<PersonMovies> call, Throwable t) {
                        Log.i("adsfcz", "onFailure: " + t.getMessage());

                    }
                });

    }

    private void getGenerMovie() {
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(DiscoverRequest.class)
                .getDiscover(NetworkConstraint.key, s)
                .enqueue(new Callback<DiscoverGenre>() {
                    @Override
                    public void onResponse(Call<DiscoverGenre> call, Response<DiscoverGenre> response) {
                        if (response.body() != null) {
                            SimilarMovieAdapter adapter = new SimilarMovieAdapter(getContext(), response.body().getResults(), Type.MovieOrTvshow.MOVIE);
                            recyclerView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onFailure(Call<DiscoverGenre> call, Throwable t) {

                    }
                });
    }

    private void getSimilarMovie() {
        Log.i("cxvdv", "onResponse: " + 234);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(MoviesRequest.class)
                .getsimilarmovie(s, NetworkConstraint.key)
                .enqueue(new Callback<SimilarMovie>() {
                    @Override
                    public void onResponse(Call<SimilarMovie> call, Response<SimilarMovie> response) {
                        if (response.body() != null) {
                            SimilarMovieAdapter adapter = new SimilarMovieAdapter(getContext(), response.body().getResults(), Type.MovieOrTvshow.MOVIE);
                            recyclerView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onFailure(Call<SimilarMovie> call, Throwable t) {

                    }
                });
    }

}