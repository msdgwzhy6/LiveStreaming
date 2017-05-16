package com.dali.admin.livestreaming.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.activity.LivePlayerActivity;
import com.dali.admin.livestreaming.adapter.NewLiveListAdapter;
import com.dali.admin.livestreaming.base.BaseFragment;
import com.dali.admin.livestreaming.base.RecyclerViewAdapter;
import com.dali.admin.livestreaming.model.LiveInfo;
import com.dali.admin.livestreaming.mvp.presenter.LiveListPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.ILiveListView;
import com.dali.admin.livestreaming.ui.listLoad.ProgressBarHelper;
import com.dali.admin.livestreaming.utils.Constants;
import com.dali.admin.livestreaming.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 直播列表显示，展示当前直播以及回放视频
 * 界面展示使用：RecyclerView + SwipeRefreshLayout
 * 列表数据 Adapter：LiveListAdapter
 * 获取数据：LiveListPresenter
 * Created by dali on 2017/4/10.
 */
public class LiveListFragment extends BaseFragment implements ILiveListView, ProgressBarHelper.ProgressBarClickListener, RecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = LiveListFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mMaterialRefreshLayout;
    //避免连击
    private long mLastClickTime = 0;

    private NewLiveListAdapter mListAdapter;
    private ProgressBarHelper mPbHelper;

    private LiveListPresenter mLiveListPresenter;

    private int viewCount;
    private int likeCount;

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
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mLiveListPresenter.refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (mLiveListPresenter.getPageIndex() <= 6) {
                    mLiveListPresenter.loadDataMore();
                } else {
                    ToastUtils.showShort(mContext, "没有数据啦...");
                    materialRefreshLayout.finishRefreshLoadMore();
                }

            }
        });
        mPbHelper.setProgressBarClickListener(this);

        mListAdapter.setOnItemClickListener(this);

    }

    /**
     * 开始播放视频
     *
     * @param playerUrl 视频数据
     */
    private void startLivePlayer(String playerUrl) {
        LivePlayerActivity.invoke(mContext, playerUrl);
    }


    @Override
    protected void initView(View rootView) {
        mMaterialRefreshLayout = obtainView(R.id.refresh);
        mRecyclerView = obtainView(R.id.live_list);
        mPbHelper = new ProgressBarHelper(obtainView(R.id.ll_data_loading), mContext);
        mLiveListPresenter = new LiveListPresenter(this);
        mListAdapter = new NewLiveListAdapter(mContext,mLiveListPresenter.getLiveListData());
        mMaterialRefreshLayout.setLoadMore(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_list;
    }

    @Override
    public void clickRefresh() {

        if (mLiveListPresenter.getLiveListData() != null && mLiveListPresenter.getLiveListData().size() > 0) {
            showData(mLiveListPresenter.getLiveListData(), Constants.STATE_REFRESH);
        } else {
            onLoading(ProgressBarHelper.STATE_ERROR);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(mContext, msg);
    }

    @Override
    public void showMsg(int msgId) {
        ToastUtils.showShort(mContext, msgId);
    }

    public void showData(ArrayList<LiveInfo> datas, int state) {
        switch (state) {
            case Constants.STATE_NORMAL:
                mListAdapter = new NewLiveListAdapter(mContext, datas);
                mRecyclerView.setAdapter(mListAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                break;
            case Constants.STATE_REFRESH:
                mListAdapter.clearData();
                mListAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);
                mMaterialRefreshLayout.finishRefresh();
                break;
            case Constants.STATE_MORE:
                mListAdapter.addData(mListAdapter.getDatas().size(), datas);
                mRecyclerView.scrollToPosition(mListAdapter.getDatas().size());
                mMaterialRefreshLayout.finishRefreshLoadMore();
                break;

        }
    }

    @Override
    public void onLiveList(int retCode, ArrayList<LiveInfo> datas, int state) {
        if (retCode == 0) {
            if (datas != null && datas.size() > 0) {
                onLoading(ProgressBarHelper.STATE_FINISH);
                showData(datas, state);
            } else {
                onLoading(ProgressBarHelper.STATE_EMPTY);
            }
        } else {
            onLoading(ProgressBarHelper.STATE_ERROR);
        }
    }

    public void onLoading(int state) {
        switch (state) {
            case ProgressBarHelper.STATE_EMPTY:
                mPbHelper.showNoData();
                break;
            case ProgressBarHelper.STATE_ERROR:
                mPbHelper.showNetError();
                break;
            case ProgressBarHelper.STATE_FINISH:
                mPbHelper.goneLoading();
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        ToastUtils.showShort(mContext,"position:"+position);
        if (0 == mLastClickTime || System.currentTimeMillis() - mLastClickTime > 1000) {
            if (mListAdapter.getItemCount() > position) {
                LiveInfo info = mLiveListPresenter.getLiveListData().get(position);
                if (info == null) {
                    Log.e(TAG, "live list item is null");
                    return;
                }
                startLivePlayer(info.getPlayUrl());
                Log.e(TAG, "url:" + info.getPlayUrl());
            }
        }
        mLastClickTime = System.currentTimeMillis();
    }
}
