package dev.anhndt.gobear.adapter.recyclerview;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by AnhNDT on 03/09/2019.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext = null;
    protected List<T> mDataSet = null;

    public BaseAdapter(Context ctx) {
        if (mDataSet == null) {
            mDataSet = new ArrayList<>();
        }
        mContext = ctx;
    }

    public BaseAdapter(Context ctx, ArrayList<T> dataSet) {
        if (mDataSet == null) {
            mDataSet = new ArrayList<>();
        }

        mContext = ctx;
        mDataSet.addAll(dataSet);
    }

    public void setDataSet(List<T> dataSet) {
        if (mDataSet == null) {
            mDataSet = new ArrayList<>();
        }

        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void removeItem(int index) {
        if (mDataSet != null && index >= 0 && index < mDataSet.size()) {
            mDataSet.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }


    public void addDataSet(List<T> dataSet) {
        if (mDataSet == null) {
            mDataSet = new ArrayList<>();
        }

        mDataSet.addAll(dataSet);
        notifyItemRangeChanged(mDataSet.size(), dataSet.size());
    }

    public void insertItem(T item) {
        if (mDataSet != null) {
            mDataSet.add(item);
            notifyItemInserted(mDataSet.size());
        }
    }

    public T getItem(int position) {
        if (mDataSet != null && position >= 0 && position < mDataSet.size()) {
            return mDataSet.get(position);
        }
        return null;
    }

    public void clear() {
        if (mDataSet != null && mDataSet.size() != 0) {
            mDataSet.clear();
            notifyDataSetChanged();
        }
    }

}
