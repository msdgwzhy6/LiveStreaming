package com.dali.admin.livestreaming.ui.listLoad;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dali.admin.livestreaming.R;

/**
 * 列表加载动画
 * Created by dali on 2017/5/11.
 */

public class ProgressBarHelper {
    public static final int MODE_LOADING_IMAGE_WITH_ANIM = 0;
    public static final int MODE_LOADING_PROGRESS = 1;

    private ImageView mImageView;
    //网络错误
    private int mNetErrorTipImageResId = R.drawable.net_error_tip;
    //没有数据
    private int mNoDataTipImageResId = R.drawable.no_data_tip;

    //数据加载
    public static final int STATE_LOADING = 0xf1;
    //数据错误
    public static final int STATE_ERROR = 0xf2;
    //数据为空
    public static final int STATE_EMPTY = 0xf3;
    //加载完成
    public static final int STATE_FINISH = 0xf4;
    private int mMode = MODE_LOADING_IMAGE_WITH_ANIM;

    public View loading;
    private TextView mTvTipText;
    private ProgressBar mProgressBar;
    private ProgressBarClickListener mProgressBarClickListener;
    private boolean isLoading = false;
    private Activity mContext;
    private OnLoadingListener mLoadingListener;

    public interface ProgressBarClickListener {
        void clickRefresh();
    }

    private interface OnLoadingListener {
        void onLoading(int state);
    }

    public void setLoadingListener(OnLoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    public void setProgressBarClickListener(ProgressBarClickListener progressBarClickListener) {
        mProgressBarClickListener = progressBarClickListener;
    }

    public ProgressBarHelper(View view, Activity context) {
        mContext = context;
        if (view != null) {
            loading = view;
        } else {
            if (context == null)
                return;
            loading = context.findViewById(R.id.ll_data_loading);
        }
        if (loading == null)
            return;

        mTvTipText = (TextView) loading.findViewById(R.id.loading_tip_txt);
        mProgressBar = (ProgressBar) loading.findViewById(R.id.loading_progress_bar);
        mImageView = (ImageView) loading.findViewById(R.id.loading_image);
        //设置加载进度
        setLoading();
    }

    public void showNetError() {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isLoading = false;
                if (mTvTipText != null) {
                    setFailue();
                }

                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                    if (loading != null) {
                        loading.setVisibility(View.VISIBLE);
                        loading.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mProgressBarClickListener != null) {
                                    if (mProgressBarClickListener != null && !mProgressBar.isShown() && mProgressBar.getVisibility() == View.GONE) {
                                        mProgressBarClickListener.clickRefresh();
                                        setLoading();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void setLoading() {
        isLoading = true;
        if (mTvTipText != null)
            mTvTipText.setText("");


        if (mMode == MODE_LOADING_PROGRESS) {
            mProgressBar.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
        } else {
            if (mImageView != null) {
                mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.common_loading_anim));
            }
            if (mImageView != null && mImageView.getDrawable() instanceof AnimationDrawable) {
                ((AnimationDrawable) mImageView.getDrawable()).start();
            }
        }

        if (loading != null) {
            loading.setEnabled(false);
            loading.setVisibility(View.VISIBLE);
        }

        if (mLoadingListener != null) {
            mLoadingListener.onLoading(STATE_LOADING);
        }
    }

    //加载失败
    private void setFailue() {
        isLoading = false;
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageDrawable(mContext.getResources().getDrawable(mNetErrorTipImageResId));
        loading.setEnabled(true);
        loading.setVisibility(View.VISIBLE);

        if (mLoadingListener != null) {
            mLoadingListener.onLoading(STATE_ERROR);
        }
    }

    //无数据加载
    private void setNoData() {
        isLoading = false;
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageDrawable(mContext.getResources().getDrawable(mNoDataTipImageResId));
        loading.setEnabled(true);
        loading.setVisibility(View.VISIBLE);
        if (mLoadingListener != null) {
            mLoadingListener.onLoading(STATE_EMPTY);
        }
    }

    //无数据
    public void showNoData(){
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isLoading=false;
                if (mTvTipText!=null){
                    setNoData();
                }

                if (mProgressBar!=null){
                    mProgressBar.setVisibility(View.GONE);
                }

                if (loading!=null){
                    loading.setVisibility(View.VISIBLE);
                    loading.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mProgressBarClickListener!=null && !mProgressBar.isShown() && mProgressBar.getVisibility() == View.GONE)
                            {
                                mProgressBarClickListener.clickRefresh();
                                setLoading();
                            }
                        }
                    });
                }
            }
        });
    }

    //正在加载
    public void doLoading() {
        if (loading != null) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isLoading = false;
                    loading.setVisibility(View.GONE);
                    if (mLoadingListener != null) {
                        mLoadingListener.onLoading(STATE_FINISH);
                    }
                }
            });
        }
    }

    public void showLoading() {
        if (isLoading)
            return;

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setLoading();
            }
        });
    }

}
