package dev.anhndt.gobear.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.anhndt.gobear.R;
import dev.anhndt.gobear.entities.NewsEntity;
import dev.anhndt.gobear.global.Global;
import dev.anhndt.gobear.interfaces.OnItemInteractionListener;
import dev.anhndt.gobear.viewholders.NewsViewHolder;
import dev.anhndt.gobear.viewholders.UnknownViewHolder;

public class NewsAdapter extends BaseAdapter<NewsEntity> {

    private OnItemInteractionListener mListener;

    public NewsAdapter(Context ctx) {
        super(ctx);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case Global.HolderType.NEWS:
                view = inflater.inflate(R.layout.gbp_news_item_layout, parent, false);
                holder = new NewsViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.gbp_unknown_item_layout, parent, false);
                holder = new UnknownViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NewsEntity item = mDataSet != null && mDataSet.get(position) != null ? mDataSet.get(position) : null;
        if (item == null) {
            return;
        }

        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).setData(item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null && item != null) {
                        mListener.onItemInteraction(item);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        final NewsEntity item = mDataSet != null && mDataSet.get(position) != null ? mDataSet.get(position) : null;
        if (item != null) {
            return Global.HolderType.NEWS;
        } else {
            return Global.HolderType.UNKNOWN;
        }
    }

    public void setOnItemInteractionListener(OnItemInteractionListener listener) {
        mListener = listener;
    }
}
