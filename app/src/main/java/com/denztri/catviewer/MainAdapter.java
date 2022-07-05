package com.denztri.catviewer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denztri.catviewer.models.CatModels;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {
    private Context context;
    private List<CatModels> catModels;

    public MainAdapter(Context context){
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCatModels(List<CatModels> catModels) {
        this.catModels = catModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.MainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_main, parent, false);

        return new MainAdapter.MainAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainAdapterViewHolder holder, int position) {
        String url = this.catModels.get(position).getUrl();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_baseline_image_24)
                .fitCenter()
                .centerCrop()
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .into(holder.img);
        Log.d("URL", url);
    }

    @Override
    public int getItemCount() {
        if (catModels != null) return catModels.size();
        return 0;
    }

    public static class MainAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public MainAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.main_rv_img);
        }
    }
}
