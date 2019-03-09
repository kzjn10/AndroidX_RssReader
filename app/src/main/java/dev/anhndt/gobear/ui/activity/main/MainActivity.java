package dev.anhndt.gobear.ui.activity.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dev.anhndt.gobear.R;
import dev.anhndt.gobear.adapter.recyclerview.NewsAdapter;
import dev.anhndt.gobear.entities.NewsEntity;
import dev.anhndt.gobear.global.Global;
import dev.anhndt.gobear.interfaces.OnItemInteractionListener;
import dev.anhndt.gobear.ui.activity.base.BaseActivity;
import dev.anhndt.gobear.ui.activity.login.LoginActivity;
import dev.anhndt.gobear.ui.activity.newsdetail.NewsDetailActivity;
import dev.anhndt.gobear.utils.ConnectionUtils;
import dev.anhndt.gobear.utils.IntentUtils;
import dev.anhndt.gobear.utils.SharedPrefUtils;
import dev.anhndt.gobear.utils.ToastHelper;
import dev.anhndt.gobear.viewmodel.NewsViewModel;

public class MainActivity extends BaseActivity {

    private SwipeRefreshLayout mSrlRefreshLayout;
    private RecyclerView mRvDataList;
    private NewsAdapter mAdapter;
    private View mLoadingView;
    private View mStatusView;
    private TextView mTvStatus;

    private CountDownTimer mCountDownTimer = null;

    private NewsViewModel mNewsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbp_activity_main);
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        initViews();
        initListeners();
        initData();
    }

    private void initViews() {
        mSrlRefreshLayout = findViewById(R.id.gbp_ma_srl_refresh_layout);
        mSrlRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mRvDataList = findViewById(R.id.gbp_ma_rv_data_list);
        mLoadingView = findViewById(R.id.gbp_ma_v_loading);
        mStatusView = findViewById(R.id.gbp_ma_v_status);
        mTvStatus = findViewById(R.id.gbp_ma_tv_status);

        mAdapter = new NewsAdapter(this);
        mRvDataList.setAdapter(mAdapter);
        mRvDataList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListeners() {
        mStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        mSrlRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefreshData();
            }
        });

        mAdapter.setOnItemInteractionListener(new OnItemInteractionListener() {
            @Override
            public void onItemInteraction(NewsEntity item) {
                Bundle bundle = new Bundle();
                bundle.putString(Global.ExtrasParams.TITLE, item.title);
                bundle.putString(Global.ExtrasParams.COVER, item.thumb);
                bundle.putString(Global.ExtrasParams.LINK, item.link);
                bundle.putString(Global.ExtrasParams.PUBLISH_TIME, item.publishTime);
                IntentUtils.startActivity(MainActivity.this, NewsDetailActivity.class, bundle);
            }
        });
    }

    private void initData() {
        if (ConnectionUtils.isConnected()) {
            setViewState(Global.ScreenState.LOADING);
            fetchData();
        } else {
            setViewState(Global.ScreenState.NO_CONNECTION);
        }
    }

    private void doRefreshData() {
        if (ConnectionUtils.isConnected()) {
            setViewState(Global.ScreenState.REFRESHING);
            mCountDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    fetchData();
                }
            };

            mCountDownTimer.start();
        } else {
            ToastHelper.showNetworkError();
            setViewState(Global.ScreenState.SHOW_DATA);
        }
    }

    private void fetchData() {
        mNewsViewModel.getNewsData().observe(this, new Observer<List<NewsEntity>>() {
            @Override
            public void onChanged(@Nullable List<NewsEntity> dataSet) {
                if (dataSet != null && dataSet.size() != 0) {
                    mAdapter.setDataSet(dataSet);
                    setViewState(Global.ScreenState.SHOW_DATA);
                } else {
                    setViewState(Global.ScreenState.NO_DATA);
                }
            }
        });
    }

    private void setViewState(Global.ScreenState state) {
        switch (state) {
            case REFRESHING:
                mSrlRefreshLayout.setRefreshing(true);
                mSrlRefreshLayout.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
                mStatusView.setVisibility(View.GONE);
                break;
            case SHOW_DATA:
                mSrlRefreshLayout.setRefreshing(false);
                mSrlRefreshLayout.setVisibility(View.VISIBLE);
                mLoadingView.setVisibility(View.GONE);
                mStatusView.setVisibility(View.GONE);
                break;
            case LOADING:
                mSrlRefreshLayout.setRefreshing(false);
                mSrlRefreshLayout.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.VISIBLE);
                mStatusView.setVisibility(View.GONE);
                break;
            case NO_CONNECTION:
                mSrlRefreshLayout.setRefreshing(false);
                mSrlRefreshLayout.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mStatusView.setVisibility(View.VISIBLE);
                mTvStatus.setText(R.string.gbp_msg_no_connection);
                break;
            default:
                mSrlRefreshLayout.setRefreshing(false);
                mSrlRefreshLayout.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.GONE);
                mStatusView.setVisibility(View.VISIBLE);
                mTvStatus.setText(R.string.gbp_msg_no_data);
                break;
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
            case R.id.gbp_mn_logout:
                showConfirmLogoutDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.gbp_dl_confirm_logout_title));
        builder.setMessage(getString(R.string.gbp_dl_confirm_logout_message));
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPrefUtils.setRememberLogin(false);
                IntentUtils.startActivity(MainActivity.this, LoginActivity.class);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onDestroy() {
        if (mNewsViewModel != null) {
            mNewsViewModel = null;
        }

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        super.onDestroy();
    }
}
