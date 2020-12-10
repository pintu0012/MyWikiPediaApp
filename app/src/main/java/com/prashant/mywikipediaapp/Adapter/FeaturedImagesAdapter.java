package com.prashant.mywikipediaapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class FeaturedImagesAdapter extends RecyclerView.Adapter<FeaturedImagesAdapter.ViewHolder> {

    private List<RandomArticles> itemList;
    private Context context;


    public FeaturedImagesAdapter(Context context, List<RandomArticles> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        //set data to featured images
        if (itemList.get(position).getType().equals("Featured Image")) {

            viewHolder.date.setVisibility(View.VISIBLE);
            viewHolder.user.setVisibility(View.VISIBLE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);

            if (itemList.get(position).getImageByte()!=null && itemList.get(position).getImageByte().length!=0) {
                Bitmap bmp = BitmapFactory.decodeByteArray(itemList.get(position).getImageByte(), 0, itemList.get(position).getImageByte().length);
                if (bmp!=null) {
                    viewHolder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 300, 300, false));
                }
            }else {
                viewHolder.imageView.setImageResource(R.drawable.placeholder);
            }

            viewHolder.titleText.setText(itemList.get(position).getTitle());
            viewHolder.type.setText(itemList.get(position).getType());
            viewHolder.progressBar.setVisibility(View.GONE);

        }

        //set data to random articles
        if (itemList.get(position).getType().equals("Random Article")) {

            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.date.setVisibility(View.GONE);
            viewHolder.user.setVisibility(View.GONE);
            Log.e("RandomTitle-->", itemList.get(position).getTitle());

            viewHolder.titleText.setText(itemList.get(position).getTitle());

            if (itemList.get(position).getExtract() != null && !itemList.get(position).getExtract().equals("")) {
                viewHolder.titleText.setText(itemList.get(position).getExtract());
            } else {
                viewHolder.titleText.setText(itemList.get(position).getTitle());
            }
        }
        viewHolder.type.setText(itemList.get(position).getType());

        //set data to category list
        if (itemList.get(position).getType().equals("Category List")) {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.date.setVisibility(View.GONE);
            viewHolder.user.setVisibility(View.GONE);

            if (itemList.get(position).getCategory()!=null && !itemList.get(position).getCategory().equals("")){
                viewHolder.titleText.setText(itemList.get(position).getCategory());
            }
        }

        viewHolder.save_for_later.setVisibility(View.GONE);
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
        TextView titleText, type, user, date, save_for_later;
        ImageView imageView;
        ProgressBar progressBar;

        public ViewHolder(final View itemView) {
            super(itemView);
            save_for_later = itemView.findViewById(R.id.save_for_later);
            type = itemView.findViewById(R.id.type);
            imageView = itemView.findViewById(R.id.image);
            titleText = itemView.findViewById(R.id.title);
            progressBar = itemView.findViewById(R.id.progressbar);
            user = itemView.findViewById(R.id.user);
            date = itemView.findViewById(R.id.date);

        }

    }

}
