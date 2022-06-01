package com.coolcollege.aar.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcollege.aar.R;
import com.coolcollege.aar.adapter.AppShareMenuAdapter;
import com.coolcollege.aar.adapter.RecycleBaseAdapter;
import com.coolcollege.aar.bean.ShareParams;
import com.coolcollege.aar.callback.ShareMenuListener;
import com.coolcollege.aar.utils.ShareUtils;

import java.util.ArrayList;

public class AppShareDialog extends BaseDialog {

    RecyclerView rvList;
    TextView tvCancel;
//    private Activity activity;

    public AppShareDialog(@NonNull Context context) {
        super(context);
//        activity = (MainActivity) context;
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
                ShareUtils.appShare(item, shareMenuListener);
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

//    public void shareCode() {
//        Uri U;
//        File sd = Environment.getExternalStorageDirectory();
//        String path = sd.getPath() + "/1.png";
//        File file = new File(path);
//        if (Build.VERSION.SDK_INT < 24) {
//            U = Uri.fromFile(file);
//        } else {
//            //android 7.0及以上权限适配
//            U = FileProvider.getUriForFile(activity,
//                    BuildConfig.APPLICATION_ID + ".provider",
//                    file);
//        }
//
//        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
//                .setType("image/*")
//                .getIntent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, U);
//        ComponentName cop = new ComponentName("com.tencent.wework", "com.tencent.wework.launch.AppSchemeLaunchActivity"); // 企业微信
////        ComponentName cop = new ComponentName("com.ss.android.lark", "com.ss.android.lark.share.impl.systemshare.ShareActivity"); // 飞书
//        shareIntent.setComponent(cop);
//        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
//            activity.startActivity(Intent.createChooser(shareIntent, activity.getTitle()));
//        }
//        if (mShareMenuListener != null) mShareMenuListener.onComplete(new ShareMenuModel("测试企业微信", "success"));
//        dismiss();
//    }
//
//    private boolean checkAppInstalled(Context context, String pkgName) {
//        if (pkgName== null || pkgName.isEmpty()) {
//            return false;
//        }
//        final PackageManager packageManager = context.getPackageManager();
//        List<PackageInfo> info = packageManager.getInstalledPackages(0);
//        if(info == null || info.isEmpty())
//            return false;
//        for ( int i = 0; i < info.size(); i++ ) {
//            if(pkgName.equals(info.get(i).packageName)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
