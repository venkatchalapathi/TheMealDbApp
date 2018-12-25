package com.example.venky.capstoneproject.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.venky.capstoneproject.Activities.MainActivity;
import com.example.venky.capstoneproject.Activities.MealDetailsActivity;
import com.example.venky.capstoneproject.Models.Meal;
import com.example.venky.capstoneproject.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/21/2018.
 */

public class CountryAndCategoryAdapter extends
        RecyclerView.Adapter<CountryAndCategoryAdapter.ViewHolder> {

    MainActivity mainActivity;
    ArrayList<Meal> arrayList;

    public CountryAndCategoryAdapter(MainActivity mainActivity, ArrayList<Meal> arrayList) {
        this.mainActivity = mainActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mainActivity)
                .inflate(R.layout.latest_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (arrayList.get(position).getStrMealThumb()!=null){
            Picasso.with(mainActivity).load(arrayList.get(position).getStrMealThumb()).placeholder(R.drawable.placeholder).into(holder.imageView, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
        }
        holder.textView.setText(arrayList.get(position).getStrMeal());
    }

    @Override
    public int getItemCount() {
        if (arrayList!=null)
        {
            return arrayList.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressBar);
            imageView=itemView.findViewById(R.id.imageId);
            textView=itemView.findViewById(R.id.titleId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           int id= Integer.parseInt(arrayList.get(getAdapterPosition()).getIdMeal());
            // Toast.makeText(mainActivity, ""+id, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(mainActivity,MealDetailsActivity.class);
            intent.putExtra(MealDetailsActivity.MEAL_ID,id);
            mainActivity.startActivity(intent);
        }
    }
}
