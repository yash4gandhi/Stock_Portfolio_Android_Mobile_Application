package com.example.myapplication;

import android.content.Intent;
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

public class PortrecycleAdapter extends  RecyclerView.Adapter<PortrecycleAdapter.PortViewHolder>{

    private static final String TAG = "rV";
    ArrayList<PortfolioObject> portArray = new ArrayList<>();
    public static final  String Extra_Text = "com.example.myapplication.Extra_Text";


    public PortrecycleAdapter(ArrayList<PortfolioObject> portArray) {
        this.portArray = portArray;
    }


    @NonNull
    @Override
    public PortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rowitem2, parent, false);
        PortViewHolder viewHolder = new PortViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PortViewHolder holder, int position) {
        int textColor;
        holder.ticker2.setText(portArray.get(position).getTicker());
        holder.share.setText(String.format("%d",(portArray.get(position).getShares()))+" shares");
        holder.price2.setText("$"+String.format("%.2f",portArray.get(position).getPrice()));
        holder.change2.setText("$"+String.format("%.2f",portArray.get(position).getChangeprice())+"("+String.format("%.2f",portArray.get(position).getPercentageChange())+"%)");
        if(portArray.get(position).getChangeprice()>0){
            textColor = Color.rgb(49, 156, 94); // green
            holder.trendup2.setVisibility(View.VISIBLE);
        }
        else if(portArray.get(position).getChangeprice() < 0){
            textColor = Color.rgb(155, 64, 73); //red

            holder.trenddown2.setVisibility(View.VISIBLE);
        }
        else{
            textColor = Color.rgb(0, 0, 0); // green
            holder.trendup2.setVisibility(View.INVISIBLE);
            holder.trenddown2.setVisibility(View.INVISIBLE);
        }
        holder.change2.setTextColor(textColor);


    }

    @Override
    public int getItemCount() {
        return portArray.size();
    }

    class PortViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ticker2,share,price2,change2;
        ImageView trendup2, trenddown2;
        public PortViewHolder(@NonNull View itemView) {
            super(itemView);
            ticker2 = itemView.findViewById(R.id.ticker2);
            share = itemView.findViewById(R.id.share);
            price2 = itemView.findViewById(R.id.price2);
            change2 = itemView.findViewById(R.id.change2);
            trendup2 = itemView.findViewById(R.id.trendup2);
            trenddown2 = itemView.findViewById(R.id.trenddown2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           //Toast.makeText(view.getContext(), portArray.get(getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(view.getContext(), Activity2.class );
            System.out.println("Company");
            intent.putExtra(Extra_Text,portArray.get(getAdapterPosition()).getTicker());
            view.getContext().startActivity(intent);

        }
    }

}




