package com.dali.admin.livestreaming.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.adapter.NewLiveListAdapter;
import com.dali.admin.livestreaming.base.BaseFragment;
import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.LiveListRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.response.ResList;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.model.LiveInfo;
import com.dali.admin.livestreaming.mvp.model.UserInfoCache;
import com.dali.admin.livestreaming.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播列表显示，展示当前直播以及回放视频
 * 界面展示使用：ListView + SwipeRefreshLayout
 * 列表数据 Adapter：LiveListAdapter
 * 获取数据：LiveListPresenter
 * Created by dali on 2017/4/10.
 */
public class LiveListFragment1 extends BaseFragment{

    private static final String TAG = LiveListFragment1.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private NewLiveListAdapter mListAdapter;
    private List<LiveInfo> mDatas;
    private int pageIndex = 1;

    public static LiveListFragment1 newInstance(Bundle bundle) {
        LiveListFragment1 fragment = new LiveListFragment1();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        getData();
    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = obtainView(R.id.live_list);
        mListAdapter = new NewLiveListAdapter(mContext,mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment1_live_list;
    }

    public void getData(){
            final LiveListRequest request = new LiveListRequest(RequestComm.live_list, UserInfoCache.getUserId(mContext), pageIndex, Constants.PAGESIZE);
            AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
                @Override
                public void onStart(int requestId) {
                }

                @Override
                public void onSuccess(int requestId, Response response) {
                    if (response.getStatus() == RequestComm.SUCCESS) {
                        ResList resList = (ResList) response.getData();
                        if (resList != null) {
                            ArrayList<LiveInfo> result = (ArrayList<LiveInfo>) resList.getItems();
                            if (result != null) {
                                mDatas = (ArrayList<LiveInfo>) resList.getItems();
                                showData();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(int requestId, int httpStatus, Throwable error) {

                }
            });
        }

    private void showData() {
        mListAdapter = new NewLiveListAdapter(mContext, mDatas);
        mRecyclerView.setAdapter(mListAdapter);
    }
}


