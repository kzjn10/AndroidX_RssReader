package dev.anhndt.gobear.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.recyclerview.widget.RecyclerView;
import dev.anhndt.gobear.GoBearApp;
import dev.anhndt.gobear.R;
import dev.anhndt.gobear.entities.NewsEntity;
import dev.anhndt.gobear.widget.CoverImageView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by AnhNDT on 03/09/2019.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private CoverImageView ivCover;
    private TextView tvTitle;
    private TextView tvPublishTime;
    private TextView tvDescription;

    public NewsViewHolder(View itemView) {
        super(itemView);
        ivCover = itemView.findViewById(R.id.gbp_ni_iv_cover);
        tvTitle = itemView.findViewById(R.id.gbp_ni_tv_title);
        tvPublishTime = itemView.findViewById(R.id.gbp_ni_tv_publish_time);
        tvDescription = itemView.findViewById(R.id.gbp_ni_tv_description);
    }

    public void setData(NewsEntity data) {
        try {
            do {
                if (data == null) {
                    break;
                }

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.gbp_default_holder_background);

                Glide.with(GoBearApp.getInstance().getApplicationContext())
                        .load(data.thumb)
                        .apply(options)
                        .transition(withCrossFade())
                        .into(ivCover);


                if (data.title != null && data.title.trim().length() != 0) {
                    tvTitle.setText(data.title.trim());
                } else {
                    tvTitle.setText(R.string.gbp_common_text_na);
                }

                if (data.publishTime != null && data.publishTime.trim().length() != 0) {
                    tvPublishTime.setText(data.publishTime.trim());
                } else {
                    tvPublishTime.setText(R.string.gbp_common_text_none);
                }

                if (data.description != null && data.description.trim().length() != 0) {
                    tvDescription.setText(data.description.trim());
                } else {
                    tvDescription.setText(R.string.gbp_common_text_none);
                }
            } while (false);
        } catch (Exception e) {
            Log.e("NewsViewHolder", e.toString());
        }
    }
}
