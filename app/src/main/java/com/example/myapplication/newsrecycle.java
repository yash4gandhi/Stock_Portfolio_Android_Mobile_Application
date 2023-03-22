package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.temporal.Temporal;
import java.util.ArrayList;

public class newsrecycle extends  RecyclerView.Adapter<newsrecycle.ViewHolder> {

    ArrayList<newsobj> nlist;

    public newsrecycle(ArrayList<newsobj> nlist) {

        this.nlist = nlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.channel.setText(nlist.get(position).getChannel());
        holder.time.setText(nlist.get(position).getTime());
        holder.story.setText(nlist.get(position).getNews());
        System.out.println("in newstv"+nlist.get(position).getImg());
        Picasso.get().load(nlist.get(position).getImg()).into(holder.newsimg);

    }

    @Override
    public int getItemCount() {
        return nlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newsimg;
        TextView channel, time, story;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsimg = itemView.findViewById(R.id.newsimg);
            channel = itemView.findViewById(R.id.channel);
            time = itemView.findViewById(R.id.time);
            story = itemView.findViewById(R.id.story);


        }
    }
}
