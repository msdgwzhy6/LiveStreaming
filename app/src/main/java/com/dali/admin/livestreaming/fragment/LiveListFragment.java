package com.dali.admin.livestreaming.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveListFragment extends BaseFragment {


    public static LiveListFragment newInstance(Bundle bundle) {
        LiveListFragment fragment = new LiveListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.tv_id);
        textView.setText(getArguments().getString("title"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_list;
    }

}
