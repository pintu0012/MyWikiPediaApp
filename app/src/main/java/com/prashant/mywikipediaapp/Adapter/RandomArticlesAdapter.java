package com.prashant.mywikipediaapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prashant.mywikipediaapp.Model.FeaturedImagesModel;
import com.prashant.mywikipediaapp.Model.RandomArticles;
import com.prashant.mywikipediaapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RandomArticlesAdapter extends RecyclerView.Adapter<RandomArticlesAdapter.ViewHolder> {

    private List<RandomArticles> itemList;
    private Context context;


    public RandomArticlesAdapter(Context context, List<RandomArticles> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

//        Log.e("IMageUrl==", itemList.get(position).getImageInfoList().get(0).getUrl());

//        viewHolder.titleText.setText(itemList.get(position).getExtract());

        if (itemList.get(position).getType().equals("Featured Image")){

            viewHolder.date.setVisibility(View.VISIBLE);
            viewHolder.user.setVisibility(View.VISIBLE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
//            Picasso.with(context)
//                    .load(itemList.get(position).getImageInfoList().get(0).getUrl())
//                    .error(R.drawable.placeholder)
//                    .resize(400, 400)
//                    .into(viewHolder.imageView, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            viewHolder.progressBar.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            viewHolder.progressBar.setVisibility(View.GONE);
//                        }
//                    });
            viewHolder.titleText.setText(itemList.get(position).getTitle());
            viewHolder.type.setText(itemList.get(position).getType());
            viewHolder.user.setText(itemList.get(position).getImageInfoList().get(0).getUser());
            String date = itemList.get(position).getImageInfoList().get(0).getTimestamp().substring(0, 10);
            viewHolder.date.setText(date);

        }

        if (itemList.get(position).getType().equals("Random Article")) {

            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.date.setVisibility(View.GONE);
            viewHolder.user.setVisibility(View.GONE);
            Log.e("RandomTitle-->", itemList.get(position).getTitle());

            viewHolder.titleText.setText(itemList.get(position).getTitle());

            if (itemList.get(position).getExtract() != null && !itemList.get(position).getExtract().equals("")) {
                viewHolder.titleText.setText(itemList.get(position).getExtract());
            }
            else {
                viewHolder.titleText.setText(itemList.get(position).getTitle());
            }
        }
        viewHolder.type.setText(itemList.get(position).getType());

        if (itemList.get(position).getType().equals("Category List")){
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.date.setVisibility(View.GONE);
            viewHolder.user.setVisibility(View.GONE);
            Log.e("Category List---Adapter","adapter");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i< itemList.get(position).getCategoryModelArrayList().size(); i++) {
                Log.e("categoryList-->", itemList.get(position).getCategoryModelArrayList().get(i).getCategory().toString());
                stringBuilder.append(itemList.get(position).getCategoryModelArrayList().get(i).getCategory().toString()+"\n");
            }

            if (stringBuilder.length() > 0) {
                viewHolder.titleText.setText(stringBuilder);
            }
        }
//        viewHolder.user.setText(itemList.get(position).getImageInfoList().get(0).getUser());
//        String date = itemList.get(position).getImageInfoList().get(0).getTimestamp().substring(0, 10);
//        viewHolder.date.setText(date);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, type, user, date;
        ImageView imageView;
        ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            imageView = itemView.findViewById(R.id.image);
            titleText = itemView.findViewById(R.id.title);
            progressBar = itemView.findViewById(R.id.progressbar);
            user = itemView.findViewById(R.id.user);
            date = itemView.findViewById(R.id.date);

        }

    }

}
