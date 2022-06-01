package com.coolcollege.aar.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coolcollege.aar.R;
import com.coolcollege.aar.base.RecycleBaseHolder;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.provider.MediaProvider;
import com.coolcollege.aar.utils.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class FolderListAdapter extends RecycleBaseAdapter<String, FolderListAdapter.MyViewHolder> {

    private HashMap<String, ArrayList<MediaItemBean>> map;

    public FolderListAdapter(ArrayList<String> list) {
        super(list);
    }

    public void setMediaMap(HashMap<String, ArrayList<MediaItemBean>> map) {
        this.map = map;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindChildViewHolder(@NonNull MyViewHolder holder, int position) {
        String item = getItemByPosition(position);

        ArrayList<MediaItemBean> list = map.get(item);

        if (list != null && list.size() > 0) {
            GlideUtils.displayNormalPic(list.get(0).path, holder.ivPic);
            holder.tvCount.setText("(" + list.size() + ")");
        } else {
            holder.ivPic.setImageResource(R.mipmap.ic_placeholder);
            holder.tvCount.setText("");
        }

        holder.tvText.setText(MediaProvider.getFolderName(item));
    }

    @NonNull
//    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(inflateLayout(R.layout.item_folder, parent));
    }

    static class MyViewHolder extends RecycleBaseHolder {

        ImageView ivPic;
        TextView tvText;
        TextView tvCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvText = itemView.findViewById(R.id.tv_text);
            tvCount = itemView.findViewById(R.id.tv_count);
        }
    }

}
