package com.dali.admin.livestreaming.mvp.presenter;

import android.util.Log;

import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.LiveListRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.response.ResList;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.model.LiveInfo;
import com.dali.admin.livestreaming.mvp.model.UserInfoCache;
import com.dali.admin.livestreaming.mvp.presenter.Ipresenter.ILiveListPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.ILiveListView;
import com.dali.admin.livestreaming.utils.Constants;

import java.util.ArrayList;

/**
 * 直播列表管理
 * Created by dali on 2017/5/8.
 */

public class LiveListPresenter extends ILiveListPresenter {

    private static final String TAG = LiveListPresenter.class.getSimpleName();
    private ArrayList<LiveInfo> mLiveInfos = new ArrayList<>();

    private ILiveListView mLiveListView;
    private int pageIndex = 1;

    private int state = Constants.STATE_NORMAL;

    public LiveListPresenter(ILiveListView liveListView) {
        super(liveListView);

        mLiveListView = liveListView;
        getData();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    /**
     * 获取直播列表
     * 1、拉取在线直播列表 2、拉取7天内录播列表 3、拉取在线直播和7天内录播列表，录播列表在后
     */
    private void getData() {
        final LiveListRequest request = new LiveListRequest(RequestComm.live_list, UserInfoCache.getUserId(mLiveListView.getContext()), pageIndex,Constants.PAGESIZE);
        AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
            @Override
            public void onStart(int requestId) {
            }

            @Override
            public void onSuccess(int requestId, Response response) {
                if (response.getStatus() == RequestComm.SUCCESS) {
                    ResList resList =  (ResList) response.getData();
                    if (resList != null) {
                        ArrayList<LiveInfo> result = (ArrayList<LiveInfo>) resList.getItems();
                        if (result != null) {
                            mLiveInfos = (ArrayList<LiveInfo>) resList.getItems();
                            if (mLiveListView != null) {
                                mLiveListView.onLiveList(0, mLiveInfos, state);
                            }
                            Log.e(TAG,"PageIndex:"+resList.getPageIndex());
                        } else {
                            if (mLiveListView != null) {
                                mLiveListView.onLiveList(0, mLiveInfos,state);
                            }
                        }
                    }else {
                        if (mLiveListView!=null){
                            mLiveListView.onLiveList(1,null,state);
                        }
                    }
                }else {
                    if (mLiveListView!=null){
                        mLiveListView.onLiveList(1,null,state);
                    }
                }
            }

            @Override
            public void onFailure(int requestId, int httpStatus, Throwable error) {
                if (mLiveListView!=null){
                    mLiveListView.onLiveList(1,null,state);
                }
            }
        });
    }

    @Override
    public ArrayList<LiveInfo> getLiveListData() {
         return mLiveInfos;
    }

    @Override
    public void refreshData() {
        pageIndex = 1;
        state = Constants.STATE_REFRESH;
        getData();
    }

    @Override
    public void loadDataMore() {
        pageIndex = pageIndex + 1;
        Log.e(TAG,"pageIndex:"+pageIndex);
        state = Constants.STATE_MORE;
        getData();
    }

    public int getPageIndex() {
        return pageIndex;
    }
}
