package com.example.moviehub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviehub.Network.NetworkConstraint;
import com.example.moviehub.R;
import com.example.moviehub.model.YoutubeConnect;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailorAdapter extends RecyclerView.Adapter<TrailorAdapter.TrailorViewHolder>{
    Context context;
    List<YoutubeConnect.Result>list;

    public  TrailorAdapter(Context context, List<YoutubeConnect.Result>list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public TrailorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.trailor,parent,false);
        return new TrailorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailorViewHolder holder, int position) {

        Picasso.get().load("https://i.ytimg.com/vi/"+list.get(position).getKey()+"/hqdefault.jpg").into(holder.image);
         holder.name.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TrailorViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public TrailorViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
        }
    }
}