package com.coolcollege.aar.selector;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.coolcollege.aar.act.MediaSelectorActivity;


/**
 * Created by Evan_for on 2020/7/8
 */
public final class SelectorOptions {

    private MediaSelector selector;
    private OptionModel optionModel;

    public SelectorOptions() {
    }

    public SelectorOptions(MediaSelector selector, String type) {
        this.selector = selector;
        optionModel = new OptionModel();
        optionModel.type = type;
    }

    public SelectorOptions maxSelectCount(int count) {
        optionModel.count = count;
        return this;
    }

    public SelectorOptions maxDuration(int duration) {
        optionModel.duration = duration;
        return this;
    }

    public SelectorOptions compressed(boolean compressed) {
        optionModel.compressed = compressed;
        return this;
    }

    public SelectorOptions percent(int percent) {
        optionModel.percent = percent;
        return this;
    }

    public SelectorOptions themeColor(int color) {
        optionModel.primaryColor = color;
        return this;
    }

    public SelectorOptions hideCamera(boolean hideCamera) {
        optionModel.hideCamera = hideCamera;
        return this;
    }

    public void forResult(int requestCode) {
        Activity activity = selector.getActivity();
        if (activity == null) return;

        Fragment fragment = selector.getFragment();

        Intent intent = new Intent(activity, MediaSelectorActivity.class);
        intent.putExtra(MediaSelector.SELECTOR_OPTIONS_KEY, optionModel);

        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

}
