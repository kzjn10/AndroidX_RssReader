package dev.anhndt.gobear.ui.fragment.onboardingstep;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.anhndt.gobear.R;
import dev.anhndt.gobear.global.Global;
import dev.anhndt.gobear.ui.fragment.base.BaseFragment;

/**
 * Simple onboarding step UI
 */
public class StepFragment extends BaseFragment {
    private int mStep;

    public StepFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step Parameter 1.
     * @return A new instance of fragment StepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepFragment newInstance(int step) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putInt(Global.BundleParams.STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStep = getArguments().getInt(Global.BundleParams.STEP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.gbp_fragment_step1, container, false);
    }

}
