package com.coolcollege.aar.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coolcollege.aar.R;
import com.coolcollege.aar.base.RecycleBaseHolder;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.bean.MimeType;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.aar.utils.GlideUtils;
import com.coolcollege.aar.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Evan_for on 2020/7/8
 */
public class MediaSelectorAdapter extends RecycleBaseAdapter<MediaItemBean, RecycleBaseHolder> {

    private static final int OTHER_TYPE_COUNT = 1;
    private static final int ITEM_TYPE_VIDEO = 1;
    private static final int ITEM_TYPE_IMG = 2;
    private static final int ITEM_TYPE_CAMERA = 3;
    private final Drawable normal;
    private final Drawable selected;
    private ArrayList<MediaItemBean> selectList;
    private boolean hideCamera;
    private String type;


    @SuppressLint("UseCompatLoadingForDrawables")
    public MediaSelectorAdapter(ArrayList<MediaItemBean> list) {
        super(list);
        normal = context.getResources().getDrawable(R.drawable.cb_mime_normal_shape);
        selected = context.getResources().getDrawable(R.drawable.cb_mime_selected_shape);
    }

    public void setSelectList(ArrayList<MediaItemBean> selectList) {
        this.selectList = selectList;
    }

    public void setHideCamera(boolean hideCamera) {
        this.hideCamera = hideCamera;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected void onBindChildViewHolder(@NonNull RecycleBaseHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_TYPE_IMG:
                updateImgItem((ImageHolder) holder, getItemWithOtherCount(position));
                break;
            case ITEM_TYPE_VIDEO:
                updateVideoItem((VideoHolder) holder, getItemWithOtherCount(position));
                break;
            case ITEM_TYPE_CAMERA:
                updateCameraItem((CameraHolder) holder);
                break;
        }
    }

    private void updateCameraItem(CameraHolder holder) {
        if (MediaSelector.TYPE_IMG.equals(type)) {
            holder.tvText.setText("拍照");
        } else if (MediaSelector.TYPE_VIDEO.equals(type)) {
            holder.tvText.setText("录像");
        }

        holder.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCameraClickListener != null) {
                    onCameraClickListener.onCameraClick(v);
                }
            }
        });
    }

    private void updateVideoItem(VideoHolder holder, MediaItemBean item) {
        String path = item.path;
        String duration = item.duration;
        boolean isChecked = item.isChecked;
        boolean isShowCheck = list.get(0).isShowCheck;

        GlideUtils.displayNormalPic(path, holder.ivVideo);
        holder.tvDuration.setText(TimeUtils.formatMills(duration));

        if (isChecked) {
            holder.tvCheck.setBackground(selected);
        } else {
            holder.tvCheck.setBackground(normal);
        }

        holder.tvCheck.setText(getSelectedIndex(item));

        if (isShowCheck) {
            holder.tvCheck.setVisibility(View.VISIBLE);
        } else {
            holder.tvCheck.setVisibility(View.GONE);
        }
    }

    private String getSelectedIndex(MediaItemBean item) {
        int selectedIndex = selectList.indexOf(item);
        if (selectedIndex != -1) {
            return String.valueOf(selectedIndex + getOtherItemCount());
        } else {
            return "";
        }
    }

    private void updateImgItem(ImageHolder holder, MediaItemBean item) {
        String path = item.path;
        boolean isChecked = item.isChecked;
        boolean isShowCheck = list.get(0).isShowCheck;

        GlideUtils.displayNormalPic(path, holder.ivImg);

        if (isChecked) {
            holder.tvCheck.setBackground(selected);
        } else {
            holder.tvCheck.setBackground(normal);
        }

        holder.tvCheck.setText(getSelectedIndex(item));

        if (isShowCheck) {
            holder.tvCheck.setVisibility(View.VISIBLE);
        } else {
            holder.tvCheck.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public RecycleBaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycleBaseHolder holder;

        switch (viewType) {
            case ITEM_TYPE_CAMERA:
                holder = new CameraHolder(inflateLayout(R.layout.item_selector_camera, null));
                break;
            case ITEM_TYPE_VIDEO:
                holder = new VideoHolder(inflateLayout(R.layout.item_selector_video, null));
                break;
            case ITEM_TYPE_IMG:
            default:
                holder = new ImageHolder(inflateLayout(R.layout.item_selector_img, null));
                break;
        }
        return holder;
    }

    @Override
    public int getOtherItemCount() {
        if (hideCamera) {
            return 0;
        } else {
            return OTHER_TYPE_COUNT;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hideCamera) {
            return getItemType(position);
        } else {
            if (position == 0) {
                return ITEM_TYPE_CAMERA;
            } else {
                return getItemType(position);
            }
        }
    }

    private int getItemType(int position) {
        MediaItemBean item = list.get(position - getOtherItemCount());
        String type = item.type;
        if (MimeType.IMG.getTypeName().equals(type)) {
            return ITEM_TYPE_IMG;
        } else {
            return ITEM_TYPE_VIDEO;
        }
    }

    public interface OnCameraClickListener {
        void onCameraClick(View view);
    }

    private OnCameraClickListener onCameraClickListener;

    public void setOnCameraClickListener(OnCameraClickListener onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }

    static class ImageHolder extends RecycleBaseHolder {

//        @BindView(R.id.iv_img)
        ImageView ivImg;
//        @BindView(R.id.tv_check)
        TextView tvCheck;

        public ImageHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvCheck = itemView.findViewById(R.id.tv_check);
        }
    }

    static class VideoHolder extends RecycleBaseHolder {

//        @BindView(R.id.iv_video)
        ImageView ivVideo;
//        @BindView(R.id.tv_duration)
        TextView tvDuration;
//        @BindView(R.id.tv_check)
        TextView tvCheck;

        public VideoHolder(View itemView) {
            super(itemView);
            ivVideo = itemView.findViewById(R.id.iv_video);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvCheck = itemView.findViewById(R.id.tv_check);
        }
    }

    static class CameraHolder extends RecycleBaseHolder {

//        @BindView(R.id.iv_camera)
        ImageView ivCamera;
//        @BindView(R.id.tv_text)
        TextView tvText;


        public CameraHolder(View itemView) {
            super(itemView);
            ivCamera = itemView.findViewById(R.id.iv_camera);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

}
