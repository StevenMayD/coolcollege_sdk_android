package com.coolcollege.aar.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.coolcollege.aar.R;
import com.coolcollege.aar.adapter.MediaSelectorAdapter;
import com.coolcollege.aar.adapter.RecycleBaseAdapter;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.bean.MimeType;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.callback.FolderListClickListener;
import com.coolcollege.aar.callback.MediaStateListener;
import com.coolcollege.aar.dialog.FolderListPop;
import com.coolcollege.aar.global.GlobalKey;
import com.coolcollege.aar.provider.FilePathProvider;
import com.coolcollege.aar.provider.MediaProvider;
import com.coolcollege.aar.selector.MediaProviderTask;
import com.coolcollege.aar.selector.MediaSelector;
import com.coolcollege.aar.selector.MediaSelectorModel;
import com.coolcollege.aar.selector.OptionModel;
import com.coolcollege.aar.utils.Byte2FileUtils;
import com.coolcollege.aar.utils.PermissionManager;
import com.coolcollege.aar.utils.PermissionStateListener;
import com.coolcollege.aar.utils.ToastUtil;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MediaSelectorActivity extends SimpleActivity implements MediaStateListener {

    RecyclerView rvList;
    FrameLayout flShadow;

    private ArrayList<MediaItemBean> totalList = new ArrayList<>();
    private ArrayList<MediaItemBean> selectedList = new ArrayList<>();
    private MediaProviderTask task;
    private MediaSelectorAdapter selectorAdapter;
    private static final int DEFAULT_MAX_SELECT_COUNT = 9;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_VIDEO = 2;
    private static final int VIDEO_QUALITY_LOW = 0;
    private static final int VIDEO_QUALITY_HIGH = 1;
    private static final int VIDEO_DURATION = 60;
    private OptionModel option;
    private TempFileBean imgTemp;
    private TempFileBean videoTemp;
    private FolderListPop folderListPop;
    private Animation showAnim;
    private Animation hideAnim;

    @Override
    protected int initLayout() {
        return R.layout.k_activity_media_selector;
    }

    @Override
    protected void initView() {
        rvList = findViewById(R.id.rv_list);
        flShadow = findViewById(R.id.fl_shadow);

        showAnim = AnimationUtils.loadAnimation(this, R.anim.k_show_anim);
        hideAnim = AnimationUtils.loadAnimation(this, R.anim.k_hide_anim);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvList.setLayoutManager(gridLayoutManager);

        selectorAdapter = new MediaSelectorAdapter(totalList);
        selectorAdapter.setSelectList(selectedList);
        rvList.setAdapter(selectorAdapter);

        folderListPop = new FolderListPop(this);
    }

    @Override
    protected void initData(Bundle bundle) {
        if (bundle == null) {
            Intent intent = getIntent();
            option = intent.getParcelableExtra(MediaSelector.SELECTOR_OPTIONS_KEY);
        } else {
            option = bundle.getParcelable(MediaSelector.SELECTOR_OPTIONS_KEY);
        }

        task = new MediaProviderTask();
        if (option == null)
            option = new OptionModel(DEFAULT_MAX_SELECT_COUNT, MediaSelector.TYPE_IMG);
        if (MediaSelector.TYPE_IMG.equals(option.type)) {
            task.setMediaType(MediaProvider.URI_TYPE.IMG);
            initImgTitle();
        } else {
            task.setMediaType(MediaProvider.URI_TYPE.VIDEO);
            initVideoTitle();
        }

        if (option.percent != 0 && option.percent < 50) {
            option.percent = 50;
        } else if (option.percent != 0 && option.percent >= 100) {
            option.percent = 80;
        }

        if(option.count > 9){
            option.count = DEFAULT_MAX_SELECT_COUNT;
        }else if(option.count < 0){
            option.count = 1;
        }

        if (option.primaryColor != 0) {
            titleBar.setBackgroundMainColor(option.primaryColor);
            setStatusBarMainColor(option.primaryColor);
            immersionBar.transparentStatusBar();
            immersionBar.init();
        }

        selectorAdapter.setType(option.type);
        selectorAdapter.setHideCamera(option.hideCamera);

        task.setMediaStateListener(this);
        task.execute();
    }

    @Override
    protected void onSavedData(Bundle bundle) {
        bundle.putParcelable(MediaSelector.SELECTOR_OPTIONS_KEY, option);
    }

    private void initVideoTitle() {
        if (option.count > 1) {
            changeTitleStyle("所有视频", "确定");
        } else {
            changeTitleStyle("所有视频");
        }
    }

    private void initImgTitle() {
        if (option.count > 1) {
            changeTitleStyle("所有图片", "确定");
        } else {
            changeTitleStyle("所有图片");
        }
    }

    @Override
    protected void initListener() {
        selectorAdapter.setOnItemClickListener(new RecycleBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MediaItemBean item = selectorAdapter.getItemWithOtherCount(position);
                //  文件大小
                boolean isDuration;
                try {
                    isDuration = Integer.parseInt(item.duration) <= option.duration; //处理视频时间是否超出给出的时间
                } catch (Exception e) {
                    isDuration = true;
                }

                if (isDuration) {
                    if (MediaSelectorModel.checkFileSize(item.size) && isDuration) {
                        if (option.count < 2) {
                            returnSelectedMediaData(selectorAdapter.getItemWithOtherCount(position));
                        } else {
                            selectMediaData(position);
                        }
                    } else {
                        ToastUtil.showToast("文件大小超过上传限制");
                    }
                } else {
                    ToastUtil.showToast("视频时长超过上传限制");
                }
            }
        });

        selectorAdapter.setOnCameraClickListener(new MediaSelectorAdapter.OnCameraClickListener() {
            @Override
            public void onCameraClick(View view) {
                if (MediaSelector.TYPE_IMG.equals(option.type)) {
                    requestCamera();
                } else {
                    requestRecorder();
                }
            }
        });

        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option.count > 1 && selectedList.isEmpty()) {
                    ToastUtil.showToast(MediaSelectorModel.getEmptyPickListText(option.type));
                } else {
                    returnSelectedMediaData();
                }
            }
        });

        titleBar.setOnTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                folderListPop.showAsDropDown(titleBar);
//                switchArrow();

                if (option.type.equals(MediaSelector.TYPE_IMG)) {
                    ArrayList<String> sourceTypeList = option.sourceType;
                    if (sourceTypeList.size() == 1) { // 拍照或相册
                        String sourceType = sourceTypeList.get(0);
                        if (sourceType.equals(MediaSelector.IMG_CAMERA)) {
                            // 拍照 禁止"所有图片"的下拉操作
                        } else if (sourceType.equals(MediaSelector.IMG_ALBUM)) {
                            folderListPop.showAsDropDown(titleBar);
                            switchArrow();
                        }
                    } else {
                        folderListPop.showAsDropDown(titleBar);
                        switchArrow();
                    }
                } else {
                    folderListPop.showAsDropDown(titleBar);
                    switchArrow();
                }
            }
        });

        folderListPop.setOnItemClickListener(new FolderListClickListener() {
            @Override
            public void onItemClick(String text, ArrayList<MediaItemBean> mediaList) {
                resetSelectTag(mediaList);
                resetData(mediaList);

                titleBar.setTitle(text);
                folderListPop.dismiss();
                switchArrow();
            }
        });

        folderListPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                switchArrow();
            }
        });

        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flShadow.setVisibility(View.VISIBLE);
                flShadow.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flShadow.setVisibility(View.GONE);
                flShadow.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void resetData(ArrayList<MediaItemBean> mediaList) {
        totalList.clear();
        totalList.addAll(mediaList);
        selectorAdapter.notifyDataSetChanged();
    }

    private void resetSelectTag(ArrayList<MediaItemBean> mediaList) {
        for (MediaItemBean bean : mediaList) {
            bean.isChecked = false;
            bean.isShowCheck = option.count > 1;
        }
        selectedList.clear();
    }


    private void requestRecorder() {
        PermissionManager.checkPermissions(this, new PermissionStateListener() {
            @Override
            public void onGranted() {
                startRecorder();
            }

            @Override
            public void onDenied() {
                ToastUtil.showToast("请前往手机设置打开相机的权限");
            }
        }, Permission.CAMERA);

    }

    private void startRecorder() {
        Intent intent = new Intent(this, VideoRecordActivity.class);
        intent.putExtra(GlobalKey.DURATION_KEY, option.duration);
        startActivityForResult(intent, REQUEST_VIDEO);

        //系统录像
        /*videoTemp = FilePathProvider.getOutputMediaFileUri(FilePathProvider.TYPE_VIDEO);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, VIDEO_QUALITY_LOW);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoTemp.uri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VIDEO_DURATION);
        startActivityForResult(intent, REQUEST_VIDEO);*/
    }

    @Override
    protected void onActivityForResult(int requestCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_PHOTO:
                setTakePhotoResult();
                break;
            case REQUEST_VIDEO:
                //系统录像返回值
                //Uri uri = intent.getData();
                videoTemp = intent.getParcelableExtra(MediaSelector.RESULT_DATA);
//                videoTemp = intent.getParcelableExtra(GlobalKey.VIDEO_PATH_KEY);
                setRecordVideoResult();
                break;
        }
    }

    private void setRecordVideoResult() {
        MediaItemBean bean = new MediaItemBean(MimeType.VIDEO.getTypeName()
                , videoTemp.duration, videoTemp.path, videoTemp.size);
        returnSelectedMediaData(bean);
    }

    private void setTakePhotoResult() {
        MediaItemBean bean = new MediaItemBean(MimeType.IMG.getTypeName(), "", imgTemp.path, FilePathProvider.getFileSize(imgTemp.path));
        returnSelectedMediaData(bean);
    }

    private void requestCamera() {
        PermissionManager.checkPermissions(this, new PermissionStateListener() {
            @Override
            public void onGranted() {
                startCamera();
            }

            @Override
            public void onDenied() {
                ToastUtil.showToast("请前往手机设置打开相机的权限");
            }
        }, Permission.CAMERA);
    }

    private void startCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgTemp = FilePathProvider.getOutputMediaFileUri(FilePathProvider.TYPE_IMG);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgTemp.uri);

        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(openCameraIntent, REQUEST_PHOTO);
    }

    private void returnSelectedMediaData() {
        compressedImg(selectedList);
        Intent intent = new Intent();
        intent.putExtra(MediaSelector.RESULT_DATA, selectedList);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void returnSelectedMediaData(MediaItemBean mediaItem) {
        Intent intent = new Intent();
        ArrayList<MediaItemBean> list = new ArrayList<>();
        list.add(mediaItem);
        compressedImg(list);
        intent.putExtra(MediaSelector.RESULT_DATA, list);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void selectMediaData(int position) {
        MediaItemBean item = selectorAdapter.getItemWithOtherCount(position);
        item.isChecked = !item.isChecked;
        if (item.isChecked) {
            if (!selectedList.contains(item)) {
                if (selectedList.size() < option.count) {
                    selectedList.add(item);
                } else {
                    item.isChecked = false;
                    ToastUtil.showToast(MediaSelectorModel.getRemindText(option.count, option.type));
                }
            }
        } else {
            selectedList.remove(item);
        }
        selectorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void updateTitle() {
        titleBar.setTitleRightPic(R.mipmap.k_ic_arrow_down_white);
    }

    @Override
    protected boolean isShowLeft() {
        return true;
    }

    @Override
    protected boolean isShowRightText() {
        return true;
    }

    @Override
    protected boolean useMainColor() {
        return false;
    }

    @Override
    public void onMediaLoad() {

    }

    @Override
    protected void releaseData() {
        flShadow.clearAnimation();
        showAnim = null;
        hideAnim = null;

        if (folderListPop != null) {
            folderListPop.dismiss();
            folderListPop.removeListener();
            folderListPop = null;
        }

        if (task != null) {
            task.removeListener();
            task.cancel(true);
            task = null;
        }
    }

    @Override
    public void onFinish(HashMap<String, ArrayList<MediaItemBean>> result) {
        if (!totalList.isEmpty()) totalList.clear();
        ArrayList<MediaItemBean> list;
        if (option.type.equals(MediaSelector.TYPE_IMG)) {
//            list = result.get(GlobalKey.ALL_PIC);
            ArrayList<String> sourceTypeList = option.sourceType;
            if (sourceTypeList.size() == 1) { // 拍照或相册
                String sourceType = sourceTypeList.get(0);
                if (sourceType.equals(MediaSelector.IMG_CAMERA)) {
                    selectorAdapter.setHideCamera(false); // 获取相机资源 显示拍照按钮 并且不加载相册资源
                    list = null;
                } else if (sourceType.equals(MediaSelector.IMG_ALBUM)) {
                    selectorAdapter.setHideCamera(true); // 获取相册资源 不允许拍照按钮
                    list = result.get(GlobalKey.ALL_PIC);
                } else {
                    selectorAdapter.setHideCamera(false);
                    list = result.get(GlobalKey.ALL_PIC);
                }
            } else if (sourceTypeList.size() == 2) { // 拍照和相册
                selectorAdapter.setHideCamera(false); // 允许拍照
                list = result.get(GlobalKey.ALL_PIC); // 获取相册资源
            } else {
                selectorAdapter.setHideCamera(false);
                list = result.get(GlobalKey.ALL_PIC);
            }

        } else {
            list = result.get(GlobalKey.ALL_VIDEO);
        }
        if (list == null) list = new ArrayList<>();
        folderListPop.setListData(result);

        totalList.addAll(list);
        initSelectState();
        selectorAdapter.notifyDataSetChanged();
    }

    private void initSelectState() {
        if (!totalList.isEmpty()) {
            MediaItemBean firstItem = totalList.get(0);
            if (option.count > 1) {
                firstItem.isShowCheck = true;
            } else {
                firstItem.isShowCheck = false;
            }
        }
    }

    private void switchArrow() {
        if (folderListPop != null) {
            if (folderListPop.isShowing()) {
                flShadow.setVisibility(View.VISIBLE);
                flShadow.startAnimation(showAnim);
                titleBar.setTitleRightPic(R.mipmap.k_ic_arrow_up_white);
            } else {
                flShadow.setVisibility(View.VISIBLE);
                flShadow.startAnimation(hideAnim);
                titleBar.setTitleRightPic(R.mipmap.k_ic_arrow_down_white);
            }
        }
    }

    private void compressedImg(ArrayList<MediaItemBean> list) {
        for (MediaItemBean bean : list) {
            String type = bean.type;
            if (MimeType.IMG.getTypeName().equals(type)) {
                String path = bean.path;
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                byte[] bytes = ImageUtils.compressByQuality(bitmap, option.percent != 0 ? option.percent : 80);
                String filePath;
                if (path.contains(".")) {
                    String[] split = path.split("\\.");
                    filePath = path.replace("." + split[split.length - 1], UUID.randomUUID().toString() + "." + split[split.length - 1]);
                } else {
                    filePath = path + UUID.randomUUID().toString() + ".jpg";
                }
                if (Byte2FileUtils.to(bytes, filePath)) {
                    bean.path = filePath;
                    bean.size = String.valueOf(FileUtils.getFileLength(filePath));
                }
            }
        }
    }
}