package dev.anhndt.gobear.ui.activity.newsdetail;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.widget.Toolbar;
import dev.anhndt.gobear.GoBearApp;
import dev.anhndt.gobear.R;
import dev.anhndt.gobear.global.Global;
import dev.anhndt.gobear.ui.activity.base.BaseActivity;
import dev.anhndt.gobear.utils.IntentUtils;
import dev.anhndt.gobear.utils.ToastHelper;
import dev.anhndt.gobear.widget.CoverImageView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class NewsDetailActivity extends BaseActivity {

    private Toolbar mToolBar;
    private WebView mWvContent = null;
    private ProgressBar mPbProgress = null;
    private CoverImageView mIvCover = null;
    private TextView mTvTitle = null;
    private TextView mTvPublishTime = null;

    private String mUrl = "";
    private String mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbp_activity_news_detail);
        initViews();
        initListeners();
        initData();
    }

    private void initViews() {
        mToolBar = findViewById(R.id.gbp_da_tool_bar);
        mPbProgress = findViewById(R.id.gbp_da_pb_progress);
        mIvCover = findViewById(R.id.gbp_da_iv_cover);
        mTvTitle = findViewById(R.id.gbp_da_tv_title);
        mTvPublishTime = findViewById(R.id.gbp_da_tv_publish_time);
        mWvContent = findViewById(R.id.gbp_da_wv_content);

        WebSettings webSettings = mWvContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);

        mPbProgress.setMax(100);
        mPbProgress.setProgress(0);
        mWvContent.setWebChromeClient(new MyWebChromeClient());
        mWvContent.setWebViewClient(new MyWebViewClient());

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setTitle("");
    }

    private void initListeners() {

    }


    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUrl = bundle.getString(Global.ExtrasParams.LINK);
            if (mUrl != null && mUrl.trim().length() != 0) {
                mWvContent.loadUrl(mUrl);
            }

            mTitle = bundle.getString(Global.ExtrasParams.TITLE);
            if (mTitle != null && mTitle.trim().length() != 0) {
                mTvTitle.setText(mTitle);
            }

            String publishTime = bundle.getString(Global.ExtrasParams.PUBLISH_TIME);
            if (publishTime != null && publishTime.trim().length() != 0) {
                mTvPublishTime.setText(publishTime);
            }

            String cover = bundle.getString(Global.ExtrasParams.COVER);
            if (cover != null && cover.trim().length() != 0) {

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.gbp_default_holder_background);

                Glide.with(GoBearApp.getInstance().getApplicationContext())
                        .load(cover)
                        .apply(options)
                        .transition(withCrossFade())
                        .into(mIvCover);
            }

        } else {
            ToastHelper.show(R.string.gbp_msg_no_data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gbp_mn_share:
                IntentUtils.shareLink(NewsDetailActivity.this, mUrl, mTitle != null ? mTitle : "");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mUrl = url;
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUrl = request.getUrl().toString();
                view.loadUrl(mUrl);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mPbProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mPbProgress.setVisibility(View.GONE);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mPbProgress.setProgress(newProgress);
        }
    }

}
