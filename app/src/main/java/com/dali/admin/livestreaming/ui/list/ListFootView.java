package com.dali.admin.livestreaming.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dali.admin.livestreaming.R;

/**
 * Created by dali on 2017/5/13.
 */

public class ListFootView extends RelativeLayout {

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private TextView mMoreText;
    private Context mContext;

    public ListFootView(Context context) {
        this(context,null);
    }

    public ListFootView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ListFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ListFootView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initView();
    }

    public void initView(){
        View view = View.inflate(mContext, R.layout.list_refresh_footer,this);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pull_to_refresh_progress);
        mLoadingText = (TextView) view.findViewById(R.id.pull_to_refresh_text);
        mMoreText = (TextView) view.findViewById(R.id.get_more);
    }

    public void setNetError() {
        mLoadingText.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mMoreText.setVisibility(View.VISIBLE);
        mMoreText.setText(R.string.networkerror_retry);
    }

    public void showErrorMsg(String msg) {
        mLoadingText.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mMoreText.setVisibility(VISIBLE);
        mMoreText.setGravity(Gravity.CENTER);
        mMoreText.setText(msg);
        mMoreText.setTextColor(Color.RED);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void setLoading() {
        mLoadingText.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingText.setText(R.string.loading_ing);
        mMoreText.setVisibility(View.INVISIBLE);
    }

    public void setLoadDone() {
        mLoadingText.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mMoreText.setVisibility(View.INVISIBLE);
    }

    public void setLoadDoneClick() {
        mLoadingText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mMoreText.setVisibility(View.VISIBLE);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getContext().getString(R.string.no_more_data_and_click));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#4A94D2")), 8, 12, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mMoreText.setText(spannableStringBuilder);
    }
}
