package com.prashant.mywikipediaapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prashant.mywikipediaapp.Model.FeaturedImagesModel;
import com.prashant.mywikipediaapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FeaturedImagesAdapter extends RecyclerView.Adapter<FeaturedImagesAdapter.ViewHolder> {

    private List<FeaturedImagesModel> itemList;
    private Context context;


    public FeaturedImagesAdapter(Context context, List<FeaturedImagesModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        Log.e("IMageUrl==", itemList.get(position).getImageInfoList().get(0).getUrl());
        viewHolder.progressBar.setVisibility(View.VISIBLE);
//        Picasso.with(context)
//                .load(itemList.get(position).getImageInfoList().get(0).getUrl())
////                 .placeholder(R.drawable.placeholder)   // optional
//                .error(R.drawable.placeholder)      // optional
//                .resize(400, 400)                        // optional
//                .into(viewHolder.imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        viewHolder.progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError() {
//                        viewHolder.progressBar.setVisibility(View.GONE);
//                    }
//                });
        viewHolder.titleText.setText(itemList.get(position).getTitle());
        viewHolder.type.setText(itemList.get(position).getType());
        viewHolder.user.setText(itemList.get(position).getImageInfoList().get(0).getUser());
        String date = itemList.get(position).getImageInfoList().get(0).getTimestamp().substring(0, 10);
        viewHolder.date.setText(date);
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
