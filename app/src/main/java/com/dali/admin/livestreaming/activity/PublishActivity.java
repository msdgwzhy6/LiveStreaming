package com.dali.admin.livestreaming.activity;

import android.view.View;
import android.widget.TextView;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.ui.customviews.CustomSwitch;

public class PublishActivity extends BaseActivity implements View.OnClickListener {

    private CustomSwitch mBtnSwitch;
    private TextView mTvRecord;

    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {
        mBtnSwitch.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBtnSwitch = obtainView(R.id.btn_record);
        mTvRecord = obtainView(R.id.tv_record);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_record:
                if (mBtnSwitch.isChecked()){
                    mBtnSwitch.setChecked(false,true);
                    mTvRecord.setText("不进行录制");
                }else {
                    mBtnSwitch.setChecked(true,true);
                    mTvRecord.setText("进行录制");
                }
                break;
        }
    }
}
