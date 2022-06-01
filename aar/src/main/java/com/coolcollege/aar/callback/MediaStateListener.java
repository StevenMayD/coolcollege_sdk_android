package com.coolcollege.aar.callback;



import com.coolcollege.aar.bean.MediaItemBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Evan_for on 2020/7/8
 */
public interface MediaStateListener {

    void onMediaLoad();

    void onFinish(HashMap<String, ArrayList<MediaItemBean>> result);

}
