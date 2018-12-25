package com.example.venky.capstoneproject.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venky.capstoneproject.Activities.MealDetailsActivity;
import com.example.venky.capstoneproject.Models.Ingreds;
import com.example.venky.capstoneproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/18/2018.
 */

public class IngredsAdapter extends
        RecyclerView.Adapter<IngredsAdapter.ViewHolder> {
    MealDetailsActivity mealDetailsActivity;
    ArrayList<Ingreds> list;
    LinearLayout linearLayout;

    public IngredsAdapter(MealDetailsActivity mealDetailsActivity, ArrayList<Ingreds> list) {
        this.mealDetailsActivity = mealDetailsActivity;
        this.list = list;
        this.linearLayout=linearLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(mealDetailsActivity)
                .inflate(R.layout.ingeds_row,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.with(mealDetailsActivity)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.iv, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        holder.tv.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        else return 0;
    }

    public class ViewHolder extends
            RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv;
        TextView tv,tv2;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressBar2);
            iv=itemView.findViewById(R.id.imageIngred);
            tv=itemView.findViewById(R.id.textIngred);
            tv2=itemView.findViewById(R.id.mesureIngred);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mealDetailsActivity, list.get(getAdapterPosition()).getName()+"\n    "+list.get(getAdapterPosition()).getMeasure(), Toast.LENGTH_SHORT).show();
        }
    }
}

