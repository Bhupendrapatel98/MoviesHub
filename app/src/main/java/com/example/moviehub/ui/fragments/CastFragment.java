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

import com.example.moviehub.network.MoviesRequest;
import com.example.moviehub.network.NetworkConstraint;
import com.example.moviehub.network.RetrofitClient;
import com.example.moviehub.network.TvShowRequest;
import com.example.moviehub.R;
import com.example.moviehub.adapter.CastAdapter;
import com.example.moviehub.model.Credit;
import com.example.moviehub.utils.Type;

/**
 * A simple {@link Fragment} subclass.
 */
public class CastFragment extends Fragment {
    RecyclerView castrecyclerView;
    View view;
    String s="";
    Type.MovieOrTvshow type;
    Type.Credit creditType;






    public static CastFragment newInstance(String s, Type.MovieOrTvshow type,Type.Credit creditType) {
        CastFragment f = new CastFragment();
        Bundle args = new Bundle();
        args.putString("id", s);
        args.putSerializable("type", type);
        args.putSerializable("creditType", creditType);
        f.setArguments(args);
        return f;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_cast, container, false);


        Bundle args = getArguments();
        if(args!=null){
            type = (Type.MovieOrTvshow)args.getSerializable("type");
            creditType = (Type.Credit)args.getSerializable("creditType");
            s = args.getString("id");
        }

        castrecyclerView=view.findViewById(R.id.castrecycler);

        castrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        Log.i("dsffdf", "onCreateView: "+type);
        if (type == Type.MovieOrTvshow.MOVIE){
            Log.i("dsffdf", "onCreateView: 1");
            getMovieRequest();

        }else {
            Log.i("dsffdf", "onCreateView: 2");
            getTvshowRequest();
        }

        return view;
    }

    private void getTvshowRequest() {

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(TvShowRequest.class)
                .getCrewRequest(s,NetworkConstraint.key)
                .enqueue(new Callback<Credit>() {

                    @Override
                        public void onResponse(Call<Credit> call, Response<Credit> response) {
                        if (response.body()!=null){

                            String a= response.body().getId().toString();
                            CastAdapter adapter=new CastAdapter(getContext(),response.body(),type,creditType);
                            castrecyclerView.setAdapter(adapter);
                            Log.i("dsscc", "onResponse: "+response.toString());
                            Log.i("dsscc", "onResponse: "+response.body().getCast());
                        }
                    }

                    @Override
                    public void onFailure(Call<Credit> call, Throwable t) {

                    }
                });


    }


    private void getMovieRequest() {

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(MoviesRequest.class)
                .getCrewRequest(s,NetworkConstraint.key)
                .enqueue(new Callback<Credit>() {
                    @Override
                    public void onResponse(Call<Credit> call, Response<Credit> response) {
                        if (response.body()!=null){
                            String a= response.body().getId().toString();
                            CastAdapter adapter=new CastAdapter(getContext(),response.body(),type,creditType);
                            castrecyclerView.setAdapter(adapter);
                            Log.i("dsscc", "onResponse: "+response.body().toString());
                            Log.i("dsscc", "onResponse: "+response.body().getCast());
                            Log.i("aada", "onResponse: "+a);
                        }


                    }

                    @Override
                    public void onFailure(Call<Credit> call, Throwable t) {

                    }
                });

    }

}
