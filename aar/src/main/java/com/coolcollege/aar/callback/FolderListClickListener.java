package com.coolcollege.aar.callback;



import com.coolcollege.aar.bean.MediaItemBean;

import java.util.ArrayList;

public interface FolderListClickListener {

    void onItemClick(String text, ArrayList<MediaItemBean> mediaList);

}
