package com.example.venky.capstoneproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.venky.capstoneproject.Activities.MealDetailsActivity;
import com.example.venky.capstoneproject.Models.MealInfo;
import com.example.venky.capstoneproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by VENKY on 12/18/2018.
 */

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHoder>{
    Context mainActivity;
    ArrayList<MealInfo> list;

    public LatestAdapter(Context mainActivity, ArrayList<MealInfo> list) {
        this.mainActivity = mainActivity;
        this.list = list;
    }


    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(mainActivity).inflate(R.layout.latest_row,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHoder holder, int position) {
        if (list.get(position).getStrMealThumb()!=null){
            Picasso.with(mainActivity).load(list.get(position).getStrMealThumb()).placeholder(R.drawable.placeholder).into(holder.imageView, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
        }
        holder.textView.setText(list.get(position).getStrMeal());
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }

    }

    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;
        public ViewHoder(View itemView) {
            super(itemView);

            progressBar=itemView.findViewById(R.id.progressBar);
            imageView=itemView.findViewById(R.id.imageId);
            textView=itemView.findViewById(R.id.titleId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id= Integer.parseInt(list.get(getAdapterPosition()).getIdMeal());
            // Toast.makeText(mainActivity, ""+id, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(mainActivity,MealDetailsActivity.class);
            intent.putExtra(MealDetailsActivity.MEAL_ID,id);
            mainActivity.startActivity(intent);
        }
    }
}
