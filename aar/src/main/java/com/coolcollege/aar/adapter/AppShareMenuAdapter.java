package com.coolcollege.aar.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coolcollege.aar.R;
import com.coolcollege.aar.base.RecycleBaseHolder;
import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.global.GlobalKey;

import java.util.ArrayList;

public class AppShareMenuAdapter extends RecycleBaseAdapter<ShareParams, AppShareMenuAdapter.MyViewHolder> {

    public AppShareMenuAdapter(ArrayList<ShareParams> list) {
        super(list);
    }

    @Override
    protected void onBindChildViewHolder(@NonNull MyViewHolder holder, int position) {
        ShareParams item = getItemByPosition(position);

        holder.ivPic.setImageResource(getPicFromType(item.type));
        holder.tvText.setText(getTextFromType(item.type));

    }

    private int getPicFromType(int type) {
        switch (type) {
            case GlobalKey.WE_CHAT:
                return R.mipmap.ic_share_wechat;
            case GlobalKey.DING_DING:
                return R.mipmap.ic_share_ding;
            case GlobalKey.WE_CHAT_MOMENT:
            default:
                return R.mipmap.ic_share_moment;
        }
    }

    private String getTextFromType(int type) {
        switch (type) {
            case GlobalKey.WE_CHAT:
                return "微信";
            case GlobalKey.DING_DING:
                return "钉钉";
            case GlobalKey.WE_CHAT_MOMENT:
            default:
                return "朋友圈";
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(inflateLayout(R.layout.item_app_share_menu, parent));
    }

    static class MyViewHolder extends RecycleBaseHolder {

        ImageView ivPic;
        TextView tvText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

}
