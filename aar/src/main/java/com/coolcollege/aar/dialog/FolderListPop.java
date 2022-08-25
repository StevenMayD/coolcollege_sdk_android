package com.coolcollege.aar.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.coolcollege.aar.R;
import com.coolcollege.aar.adapter.FolderListAdapter;
import com.coolcollege.aar.adapter.RecycleBaseAdapter;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.callback.FolderListClickListener;
import com.coolcollege.aar.provider.MediaProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class FolderListPop extends PopupWindow {

    private Context context;
    RecyclerView rvList;
    private FolderListAdapter adapter;
    private FolderListClickListener listener;

    public FolderListPop(Context context) {
        super(context);
        this.context = context;
        initPop();
    }

    private void initPop() {
        View view = View.inflate(context, R.layout.k_dialog_folder_list, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        setAnimationStyle(R.style.k_dialogTop2BtmAnim);

        rvList = view.findViewById(R.id.rv_list);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(manager);
    }

    public void setListData(HashMap<String, ArrayList<MediaItemBean>> map) {
        ArrayList<String> folderPathList = MediaProvider.getFolderPathList(map);

        if (adapter == null) {
            adapter = new FolderListAdapter(folderPathList);
            adapter.setMediaMap(map);
            rvList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        adapter.setOnItemClickListener(new RecycleBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (listener != null) {
                    String item = adapter.getItemByPosition(position);
                    listener.onItemClick(MediaProvider.getFolderName(item), map.get(item));
                }
            }
        });
    }

    public void setOnItemClickListener(FolderListClickListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }
}
