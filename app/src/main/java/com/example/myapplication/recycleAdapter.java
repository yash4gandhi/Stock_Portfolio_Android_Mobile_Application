package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.ViewHolder> {
    private static final String TAG = "rV";
//    List<String> l;


//    public recycleAdapter(List<String> l) {
//        this.l = l;
//    }

    ArrayList<favoriteobj> favArray;


    public recycleAdapter(ArrayList<favoriteobj> favArray) {
        this.favArray = favArray;
    }





    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rowitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.ticker.setText(l.get(position));
        int textColor;
        holder.ticker.setText(favArray.get(position).getTicker());
        holder.company.setText(favArray.get(position).getcompanyname());
        holder.price.setText("$"+String.format("%.2f",favArray.get(position).getPrice()));
        holder.change.setText("$"+String.format("%.2f",favArray.get(position).getChangeprice())+"("+String.format("%.2f",favArray.get(position).getPercentageChange())+"%)");
        if(favArray.get(position).getChangeprice()>0){
            textColor = Color.rgb(49, 156, 94); // green
            holder.trendup3.setVisibility(View.VISIBLE);
        }
        else if(favArray.get(position).getChangeprice() < 0){
            textColor = Color.rgb(155, 64, 73); //red

            holder.trenddown3.setVisibility(View.VISIBLE);
        }
        else{
            textColor = Color.rgb(0, 0, 0); // green
            holder.trendup3.setVisibility(View.INVISIBLE);
            holder.trenddown3.setVisibility(View.INVISIBLE);
        }
        holder.change.setTextColor(textColor);

    }

    @Override
    public int getItemCount() {
        return favArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ticker,company,price,change;
        ImageView trendup3,trenddown3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ticker = itemView.findViewById(R.id.ticker);
            company = itemView.findViewById(R.id.company);
            price = itemView.findViewById(R.id.price);
            change = itemView.findViewById(R.id.change);
            trendup3 = itemView.findViewById(R.id.trendup3);
            trenddown3 = itemView.findViewById(R.id.trenddown3);

        itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            //Toast.makeText(view.getContext(), favArray.get(getAdapterPosition()).getTicker(), Toast.LENGTH_SHORT).show();

        }
    }
}
