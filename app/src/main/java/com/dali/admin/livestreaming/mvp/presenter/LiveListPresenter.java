package com.dali.admin.livestreaming.mvp.presenter;

import android.util.Log;

import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.LiveListRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.response.ResList;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.logic.ImUserInfoMgr;
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
    private boolean mHasMore;
    private boolean isLoading;
    private ArrayList<LiveInfo> mLiveInfos = new ArrayList<>();

    private ILiveListView mLiveListView;

    public LiveListPresenter(ILiveListView liveListView) {
        super(liveListView);

        mLiveListView = liveListView;
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isHasMore() {
        return mHasMore;
    }

    /**
     * 获取内存中缓存的直播列表
     *
     * @return
     */
    @Override
    public ArrayList<LiveInfo> getLiveListFormCache() {
        return mLiveInfos;
    }

    /**
     * 分页获取完整直播列表
     * @return
     */
    @Override
    public boolean reloadLiveList() {
        Log.e(TAG, "fetchLiveList start");
        mLiveInfos.clear();
        fetchLiveList(RequestComm.live_list, UserInfoCache.getUserId(mLiveListView.getContext()), 1, Constants.PAGESIZE);
        return true;
    }

    /**
     * 获取直播列表
     *
     * @param type      1、拉取在线直播列表 2、拉取7天内录播列表 3、拉取在线直播和7天内录播列表，录播列表在后
     * @param userId    用户id
     * @param pageIndex 页数
     * @param pageSize  每页个数
     */
    private void fetchLiveList(int type, String userId, final int pageIndex, int pageSize) {
        final LiveListRequest request = new LiveListRequest(type, userId, pageIndex, pageSize);
        AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
            @Override
            public void onStart(int requestId) {
                isLoading = true;
            }

            @Override
            public void onSuccess(int requestId, Response response) {
                Log.e(TAG, "onSuccess");
                Log.e(TAG,"url:"+request.getUrl() + ",status:"+response.getStatus());
                if (response.getStatus() == RequestComm.SUCCESS) {
                    ResList<LiveInfo> resList = (ResList<LiveInfo>) response.getData();
                    if (resList != null) {
                        ArrayList<LiveInfo> result = (ArrayList<LiveInfo>) resList.getItems();
                        if (result != null) {
                            Log.e(TAG, "fetchLiveList curCount:" + result.size());
                            if (!result.isEmpty()) {
                                mLiveInfos.addAll(result);
                                mHasMore = mLiveInfos.size() >= (pageIndex * Constants.PAGESIZE);

                                Log.e(TAG,"mLiveInfos size:"+mLiveInfos.size());
                            } else {
                                mHasMore = false;
                            }
                            if (mLiveListView != null) {
                                mLiveListView.onLiveList(0, mLiveInfos, pageIndex == 1);
                            }
                        } else {
                            if (mLiveListView != null) {
                                mLiveListView.onLiveList(0, mLiveInfos, pageIndex == 1);
                            }
                        }
                    }else {
                        if (mLiveListView!=null){
                            mLiveListView.onLiveList(1,null,true);
                        }
                    }
                }else {
                    if (mLiveListView!=null){
                        mLiveListView.onLiveList(1,null,true);
                    }
                }
                isLoading = false;
            }

            @Override
            public void onFailure(int requestId, int httpStatus, Throwable error) {
                Log.e(TAG,"onFailure");
                if (mLiveListView!=null){
                    mLiveListView.onLiveList(1,null,true);
                }
                isLoading = false;
            }
        });
    }

    @Override
    public boolean loadDataMore() {
        if (mHasMore){
            int pageIndex = mLiveInfos.size() / Constants.PAGESIZE + 1;
            fetchLiveList(RequestComm.live_list_more,ImUserInfoMgr.getInstance().getUserId(),pageIndex,Constants.PAGESIZE);
        }
        return true;
    }


}
