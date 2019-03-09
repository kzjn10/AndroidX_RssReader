package dev.anhndt.gobear.ui.fragment.base;

import android.view.View;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected View mRootView;


    @Override
    public void onDetach() {
        if (mRootView != null) {
            mRootView = null;
        }

        super.onDetach();
    }
}
