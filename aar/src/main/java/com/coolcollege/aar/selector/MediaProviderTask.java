package com.coolcollege.aar.selector;


import android.os.AsyncTask;

import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.callback.MediaStateListener;
import com.coolcollege.aar.provider.MediaProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Evan_for on 2020/7/8
 */
public class MediaProviderTask extends AsyncTask<Void, Void, HashMap<String, ArrayList<MediaItemBean>>> {

    private MediaStateListener mediaStateListener;
    private MediaProvider.URI_TYPE type;

    public void setMediaStateListener(MediaStateListener mediaStateListener) {
        this.mediaStateListener = mediaStateListener;
    }

    public void setMediaType(MediaProvider.URI_TYPE type) {
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        if (mediaStateListener != null) mediaStateListener.onMediaLoad();
    }

    @Override
    protected void onPostExecute(HashMap<String, ArrayList<MediaItemBean>> result) {
        if (mediaStateListener != null) {
            mediaStateListener.onFinish(result);
        }
    }

    @Override
    protected HashMap<String, ArrayList<MediaItemBean>> doInBackground(Void... voids) {
        return MediaProvider.getMediaList(type);
    }

    public void removeListener() {
        mediaStateListener = null;
    }
}
