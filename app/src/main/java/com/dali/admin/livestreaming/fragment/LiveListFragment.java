package com.dali.admin.livestreaming.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.activity.LivePlayerActivity;
import com.dali.admin.livestreaming.adapter.LiveListAdapter;
import com.dali.admin.livestreaming.base.BaseFragment;
import com.dali.admin.livestreaming.model.LiveInfo;
import com.dali.admin.livestreaming.mvp.presenter.LiveListPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.ILiveListView;
import com.dali.admin.livestreaming.ui.list.ListFootView;
import com.dali.admin.livestreaming.ui.listLoad.ProgressBarHelper;
import com.dali.admin.livestreaming.utils.Constants;
import com.dali.admin.livestreaming.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 直播列表显示，展示当前直播以及回放视频
 * 界面展示使用：ListView + SwipeRefreshLayout
 * 列表数据 Adapter：LiveListAdapter
 * 获取数据：LiveListPresenter
 * Created by dali on 2017/4/10.
 */
public class LiveListFragment extends BaseFragment implements ILiveListView, SwipeRefreshLayout.OnRefreshListener, ProgressBarHelper.ProgressBarClickListener, AbsListView.OnScrollListener {

    private static final String TAG = LiveListFragment.class.getSimpleName();
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //避免连击
    private long mLastClickTime = 0;

    private LiveListAdapter mListAdapter;
    private ProgressBarHelper mPbHelper;

    private LiveListPresenter mLiveListPresenter;
    private int visibleLast;
    private int viewCount;
    private int likeCount;

    private ListFootView mFootView;


    public static LiveListFragment newInstance(Bundle bundle) {
        LiveListFragment fragment = new LiveListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        refreshListView();
    }

    /**
     * 刷新直播列表
     */
    private void refreshListView() {
        if (mLiveListPresenter.reloadLiveList()) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    protected void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPbHelper.setProgressBarClickListener(this);
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (0 == mLastClickTime || System.currentTimeMillis() - mLastClickTime > 1000) {
                    if (mListAdapter.getCount() > position) {
                        LiveInfo info = mListAdapter.getItem(position);
                        if (info == null) {
                            Log.e(TAG, "live list item is null");
                            return;
                        }
                        startLivePlayer(info.getPlayUrl());
                        Log.e(TAG,"url:"+info.getPlayUrl());
                    }
                }
                mLastClickTime = System.currentTimeMillis();
            }
        });
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden){
            refreshListView();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Constants.START_LIVE_PLAY == requestCode) {
            if (0 != resultCode) {
                //观看直播返还错误信息后，刷新列表，但是不显示动画
                mLiveListPresenter.reloadLiveList();
            } else {
                if (data == null) {
                    return;
                }

                String userId = data.getStringExtra(Constants.PUSHER_ID);
                for (int i=0;i<mListAdapter.getCount();i++){
                    LiveInfo info = mListAdapter.getItem(i);
                    if (info!=null && info.getUserInfo().getUserId().equalsIgnoreCase(userId)){
                        viewCount = info.getViewCount();
                        viewCount = (int) data.getLongExtra(Constants.MEMBER_COUNT,info.getViewCount());
                        likeCount = info.getLikeCount();
                        likeCount = (int) data.getLongExtra(Constants.HEART_COUNT,info.getLikeCount());
                        mListAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        }


    }

    @Override
    protected void initView(View rootView) {
        mSwipeRefreshLayout = obtainView(R.id.swipe_refresh_layout_list);
        mListView = obtainView(R.id.live_list);
        mPbHelper = new ProgressBarHelper(obtainView(R.id.ll_data_loading), mContext);
        mLiveListPresenter = new LiveListPresenter(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setDistanceToTriggerSync(100);

        mListAdapter = new LiveListAdapter(mLiveListPresenter.getLiveListFormCache(), mContext);
        mListView.setAdapter(mListAdapter);

        //添加foot view
        mFootView = new ListFootView(mContext);
        mListView.addFooterView(mFootView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live_list;
    }

    @Override
    public void onRefresh() {
        refreshListView();
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

    @Override
    public void onLiveList(int retCode, ArrayList<LiveInfo> datas, boolean refresh) {
        if (retCode == 0) {
            if (datas != null && datas.size() > 0) {
                mListAdapter.addAll((ArrayList<LiveInfo>) datas.clone());
                mPbHelper.doLoading();
            } else {
                mPbHelper.showNoData();
            }

            if (refresh) {
                mListAdapter.notifyDataSetChanged();
            }
        } else {
            ToastUtils.showShort(mContext, "刷新失败");
            mPbHelper.showNetError();
        }

        mSwipeRefreshLayout.setRefreshing(false);
        if (!mLiveListPresenter.isHasMore()) {
            mListView.removeFooterView(mFootView);
        }
    }

    @Override
    public void clickRefresh() {
        refreshListView();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = mListAdapter.getCount();
        if (itemsLastIndex < 0)
            return;

        //加载更多
        int lastIndex = itemsLastIndex;
        Log.e(TAG, "visibleLast:" + visibleLast + ",lastIndex:" + lastIndex);
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLast >= lastIndex && !mLiveListPresenter.isLoading()) {
            mLiveListPresenter.loadDataMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        visibleLast = firstVisibleItem + visibleItemCount - 1;
    }
}
