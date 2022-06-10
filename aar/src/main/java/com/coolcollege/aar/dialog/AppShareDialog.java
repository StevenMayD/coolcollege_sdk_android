package com.coolcollege.aar.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.coolcollege.aar.R;
import com.coolcollege.aar.adapter.AppShareMenuAdapter;
import com.coolcollege.aar.adapter.RecycleBaseAdapter;
import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.callback.ShareMenuListener;
import com.coolcollege.aar.model.ShareMenuModel;
import com.coolcollege.aar.utils.GlideUtils;
import com.coolcollege.aar.utils.ToastUtil;

import java.util.ArrayList;

public class AppShareDialog extends BaseDialog {

    private RecyclerView rvList;
    private TextView tvCancel;
    private Activity act;
    private String platformType;
    private String shareState;

    public AppShareDialog(@NonNull Activity activity) {
        super(activity);
        act = activity;
    }

    @Override
    protected int initLayout() {
        return R.layout.layout_app_share_dialog;
    }

    @Override
    protected void initView() {
        rvList = getBaseView().findViewById(R.id.rv_list);
        tvCancel = getBaseView().findViewById(R.id.tv_cancel);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rvList.setLayoutManager(manager);
    }

    public void initList(ArrayList<ShareParams> list, ShareMenuListener shareMenuListener){
        AppShareMenuAdapter adapter = new AppShareMenuAdapter(list);
        rvList.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecycleBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ShareParams item = adapter.getItemByPosition(position);
                loadBitmap(item);
                if (1 == item.type) {
                    platformType = "微信";
                } else if (2 == item.type) {
                    platformType = "钉钉";
                } else if (3 == item.type) {
                    platformType = "微信朋友圈";
                }
                if (shareMenuListener != null) shareMenuListener.onComplete(new ShareMenuModel(platformType, shareState));
            }
        });
    }

    @Override
    protected void initListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    protected int defaultGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int defaultWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int windowAnim() {
        return R.style.dialogBtm2TopAnim;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected Drawable windowBgDrawable() {
        return getContext().getResources().getDrawable(R.drawable.shape_white_top_round_5);
    }

    public void shareCode(ShareParams item, Bitmap shareBitmap) {
        Uri imageUri;
        ComponentName cop = null;

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

        if (item.title == null && item.content == null && item.url == null) {
            shareIntent.setType("image/*");
            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(act.getContentResolver(), shareBitmap, null, null));
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        } else {
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, item.url);
        }

        if (1 == item.type) {
            cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"); // 微信
        } else if (2 == item.type) {
            cop = new ComponentName("com.alibaba.android.rimet", "com.alibaba.android.rimet.biz.BokuiActivity"); // 钉钉
        } else if (3 == item.type) {
            cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"); // 微信朋友圈
            if ("text/plain".equals(shareIntent.getType())) {
                ToastUtil.showToast("微信朋友圈不支持纯文本分享");
                return;
            }
        }
        shareIntent.setComponent(cop);
        if (shareIntent.resolveActivity(act.getPackageManager()) != null) {
            act.startActivity(Intent.createChooser(shareIntent, item.title));
        }
    }

    private void loadBitmap(ShareParams item) {
        GlideUtils.justGetBitmapFromPic(item.logo, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                shareCode(item, resource);
            }
        });
    }
}
