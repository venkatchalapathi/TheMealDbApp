package com.example.venky.capstoneproject.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.venky.capstoneproject.Activities.BrowseByActivity;
import com.example.venky.capstoneproject.Activities.MainActivity;
import com.example.venky.capstoneproject.Models.Category;
import com.example.venky.capstoneproject.R;

import java.util.ArrayList;

/**
 * Created by VENKY on 12/20/2018.
 */

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.ViewHolder> {
    BrowseByActivity browseByActivity;
    int[] country_images;
    String[] country_names;
    ArrayList<Category> names;
    int check;

    public BrowseAdapter(BrowseByActivity browseByActivity,
                         int[] country_images, String[] country_names, int check, ArrayList<Category> names) {
        this.browseByActivity = browseByActivity;
        this.country_images = country_images;
        this.country_names = country_names;
        this.check = check;
        this.names = names;
    }

    @NonNull
    @Override
    public BrowseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(browseByActivity)
                .inflate(R.layout.country_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseAdapter.ViewHolder holder, int position) {
        if (check == 3) {
            holder.image.setImageResource(country_images[position]);
            holder.country.setText(country_names[position]);
        } else {
            Glide.with(browseByActivity).load(names.get(position).getStrCategoryThumb()).into(holder.image);
            holder.country.setText(names.get(position).getStrCategory());
        }
    }

    @Override
    public int getItemCount() {
        if (check == 3) {
            return country_names.length;
        } else {
            return names.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView country;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.country_img_id);
            country = itemView.findViewById(R.id.country_name_id);


            if (check == 3) {
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.RESULT, country_names[getAdapterPosition()]);
                        browseByActivity.setResult(Activity.RESULT_OK, intent);
                        browseByActivity.finish();
                    }
                });
            } else {
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.RESULT, names.get(getAdapterPosition()).getStrCategory());
                        browseByActivity.setResult(Activity.RESULT_OK, intent);
                        browseByActivity.finish();
                    }
                });

            }

        }


    }
}
