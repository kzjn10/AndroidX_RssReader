package dev.anhndt.gobear.adapter.viewpager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class OnBoardingAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mDataSet = new ArrayList<>();

    public OnBoardingAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position > mDataSet.size() || position < 0 || mDataSet.get(position) == null) {
            throw new RuntimeException("Position invalid");
        }

        return mDataSet.get(position);
    }

    @Override
    public int getCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public void addFragment(Fragment fragment) {
        do {
            if (fragment == null) {
                break;
            }

            if (mDataSet == null) {
                mDataSet = new ArrayList<>();
            }

            mDataSet.add(fragment);

        } while (false);
    }
}
