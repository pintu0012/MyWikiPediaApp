package com.prashant.mywikipediaapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.prashant.mywikipediaapp.Common.SQLiteDatabaseHandler;
import com.prashant.mywikipediaapp.Model.FeaturedImagesModel;
import com.prashant.mywikipediaapp.Model.RandomArticles;
import com.prashant.mywikipediaapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RandomArticlesAdapter extends RecyclerView.Adapter<RandomArticlesAdapter.ViewHolder> {

    private List<RandomArticles> itemList;
    private Context context;
    private SQLiteDatabaseHandler sqLiteDatabaseHandler;


    public RandomArticlesAdapter(Context context, List<RandomArticles> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


//        viewHolder.titleText.setText(itemList.get(position).getExtract());

        if (itemList.get(position).getType().equals("Featured Image")) {

            viewHolder.date.setVisibility(View.VISIBLE);
            viewHolder.user.setVisibility(View.VISIBLE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(itemList.get(position).getImageInfoList().get(0).getUrl())
                    .error(R.drawable.placeholder)
                    .resize(400, 400)
                    .into(viewHolder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            viewHolder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            viewHolder.progressBar.setVisibility(View.GONE);
                        }
                    });
            viewHolder.titleText.setText(itemList.get(position).getTitle());
            viewHolder.type.setText(itemList.get(position).getType());
            if (itemList.get(position).getImageInfoList() != null) {
                if (itemList.get(position).getImageInfoList().get(0).getUser() != null) {
                    viewHolder.user.setText(itemList.get(position).getImageInfoList().get(0).getUser());
                }
                if (itemList.get(position).getImageInfoList().get(0).getTimestamp() != null) {
                    String date = itemList.get(position).getImageInfoList().get(0).getTimestamp().substring(0, 10);
                    viewHolder.date.setText(date);
                }
            }


        }

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

        if (itemList.get(position).getType().equals("Category List")) {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.date.setVisibility(View.GONE);
            viewHolder.user.setVisibility(View.GONE);
            if (itemList.get(position).getCategory()!=null && !itemList.get(position).getCategory().equals("")){
                viewHolder.titleText.setText(itemList.get(position).getCategory());
            }
        }
//        viewHolder.user.setText(itemList.get(position).getImageInfoList().get(0).getUser());
//        String date = itemList.get(position).getImageInfoList().get(0).getTimestamp().substring(0, 10);
//        viewHolder.date.setText(date);

        if (itemList.get(position).isSaved()) {
            viewHolder.save_for_later.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_black_24dp, 0, 0, 0);
        } else {
            viewHolder.save_for_later.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_border_black_24dp, 0, 0, 0);

        }
        viewHolder.save_for_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(position).isSaved()) {
                    Toast.makeText(context, "Already saved", Toast.LENGTH_SHORT).show();
                    return;
                }
                SAVE_DATA_TO_DATABASE(viewHolder,position);
            }
        });
    }

    private void SAVE_DATA_TO_DATABASE(ViewHolder viewHolder, int position) {
        sqLiteDatabaseHandler = new SQLiteDatabaseHandler(context);

        String id = "", title = "", type = "", imageUrl = "", category = "", extract = "";

        Bitmap imageBitmap= null;
        if (itemList.get(position).getPageId() != null && !itemList.get(position).getPageId().equals("")) {
            id = itemList.get(position).getPageId();
        }
        if (itemList.get(position).getTitle() != null && !itemList.get(position).getTitle().equals("")) {
            title = itemList.get(position).getTitle();
        }
        if (itemList.get(position).getType() != null && !itemList.get(position).getType().equals("")) {
            type = itemList.get(position).getType();
        }
        if (itemList.get(position).getImageUrl() != null && !itemList.get(position).getImageUrl().equals("")) {
            imageUrl = itemList.get(position).getImageUrl();
            BitmapDrawable drawable = (BitmapDrawable) viewHolder.imageView.getDrawable();
            imageBitmap = drawable.getBitmap();
        }
        if (itemList.get(position).getCategory() != null && !itemList.get(position).getCategory().equals("")) {
            category = itemList.get(position).getCategory();
        }
        if (itemList.get(position).getExtract() != null && !itemList.get(position).getExtract().equals("")) {
            extract = itemList.get(position).getExtract();
        }

        RandomArticles randomArticles = new RandomArticles(id, title, type, imageUrl, category, extract,true);
        sqLiteDatabaseHandler.addArticle(randomArticles, imageBitmap);

        Toast.makeText(context, "Successfully Added for Offline Reading", Toast.LENGTH_SHORT).show();
        itemList.get(position).setSaved(true);
        notifyDataSetChanged();
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
