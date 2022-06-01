package com.coolcollege.aar.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coolcollege.aar.application.Options;
import com.coolcollege.aar.base.RecycleBaseHolder;

import java.util.ArrayList;

/**
 * 项目名称：
 * 类描述：
 * 作者：   admin .
 * 日期：   2018/8/9 .
 * 公司： Usho Network Tech. Co., Ltd&lt;br&gt;
 */
public abstract class RecycleBaseAdapter<T, H extends RecycleBaseHolder> extends RecyclerView.Adapter<H> {

    protected ArrayList<T> list;
    protected LayoutInflater inflater;
    protected Context context;

    public RecycleBaseAdapter(ArrayList<T> list) {
        this.list = list;
        inflater = LayoutInflater.from(Options.get());
        context = Options.get();
    }

    public void addNewData(ArrayList<T> list) {
        this.list.addAll(list);
        notifyItemRangeInserted(this.list.size() - list.size(), list.size());
    }

    public void addNewData(T data) {
        list.add(data);
        notifyItemRangeInserted(list.size() - 1, list.size());
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public ArrayList<T> getList() {
        return list;
    }

    public T getItemByPosition(int position) {
        return list.get(position);
    }

    public T getItemWithOtherCount(int position) {
        return list.get(position - getOtherItemCount());
    }


    @Override
    public int getItemCount() {
        return list.size() + getOtherItemCount();
    }

    /**
     * 返回itemCount,默认为list.size
     * 子类可重写
     *
     * @return
     */
    public int getOtherItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        final int oPosition = position;

        onBindChildViewHolder(holder, position);

        if (holder.click_item != null) {
            holder.click_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, oPosition);
                    }
                }
            });

            holder.click_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        return onItemLongClickListener.onItemLongClick(v, oPosition);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    protected abstract void onBindChildViewHolder(@NonNull H holder, int position);

    protected final View inflateLayout(int layoutId, ViewGroup root) {
        return inflater.inflate(layoutId, root, false);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
