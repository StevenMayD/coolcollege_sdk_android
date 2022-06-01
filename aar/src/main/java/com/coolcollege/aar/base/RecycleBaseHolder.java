package com.coolcollege.aar.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 项目名称：cn.usho.sosho.base
 * 类描述：
 * 作者：   admin .
 * 日期：   2018/8/9 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public class RecycleBaseHolder extends RecyclerView.ViewHolder{
    public final View click_item;

    public RecycleBaseHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this,itemView);
        click_item = itemView.findViewWithTag("click_item");
    }
}
