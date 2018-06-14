package com.example.xkw.imagepos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyItemHolder> {

    private List<LocalImage> localImages = new ArrayList<>();
    private LocalImage selectedImg;

    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    void setClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public GalleryAdapter(List<LocalImage> images) {
        this.localImages = images;
    }

    @NonNull
    @Override
    public MyItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyItemHolder viewHolder;
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_image, parent, false);
            viewHolder = new MyItemHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemHolder holder, int position) {
        holder.mImg.setOnClickListener(view -> {
            selectedImg = localImages.get(position);
            //notify the task interface controller
            if (mItemClickListener != null){
                mItemClickListener.onClick(position);
            }
        });
    }

    public LocalImage getSelectedImg(){
        return selectedImg;
    }

    @Override
    public int getItemCount() {
        return localImages.size();
    }

    //default package-private
    void addImages(List<LocalImage> images){
        localImages.addAll(images);
        notifyDataSetChanged();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        MyItemHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.img);
        }

    }
}