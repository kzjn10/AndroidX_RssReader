package dev.anhndt.gobear.ui.activity.onboarding;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;
import dev.anhndt.gobear.R;
import dev.anhndt.gobear.adapter.viewpager.OnBoardingAdapter;
import dev.anhndt.gobear.ui.activity.base.BaseActivity;
import dev.anhndt.gobear.ui.activity.login.LoginActivity;
import dev.anhndt.gobear.ui.fragment.onboardingstep.StepFragment;
import dev.anhndt.gobear.utils.IntentUtils;
import dev.anhndt.gobear.widget.circleindicator.CirclePageIndicator;

public class OnBoardingActivity extends BaseActivity {

    private ViewPager mVpPager;
    private OnBoardingAdapter mAdapter;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbp_activity_on_boarding);
        initViews();
        initListeners();
    }

    private void initViews() {
        mVpPager = findViewById(R.id.gbp_oba_vp_pager);
        mIndicator = findViewById(R.id.gbp_oba_ci_indicator);

        mAdapter = new OnBoardingAdapter(getSupportFragmentManager());
        mAdapter.addFragment(StepFragment.newInstance(1));
        mAdapter.addFragment(StepFragment.newInstance(2));
        mAdapter.addFragment(StepFragment.newInstance(3));

        mVpPager.setAdapter(mAdapter);
        mVpPager.setOffscreenPageLimit(3);

        mIndicator.setViewPager(mVpPager);

    }

    private void initListeners() {
        findViewById(R.id.gbp_oba_btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(OnBoardingActivity.this, LoginActivity.class);
                OnBoardingActivity.this.finish();
            }
        });
    }
}
