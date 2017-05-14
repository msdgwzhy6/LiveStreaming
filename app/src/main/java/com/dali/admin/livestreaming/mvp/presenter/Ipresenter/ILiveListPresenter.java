package com.dali.admin.livestreaming.mvp.presenter.Ipresenter;

import com.dali.admin.livestreaming.model.LiveInfo;
import com.dali.admin.livestreaming.mvp.view.Iview.BaseView;

import java.util.ArrayList;

/**
 * 列表数据管理
 * Created by dali on 2017/5/8.
 */

public abstract class ILiveListPresenter implements BasePresenter {
    protected BaseView mBaseView;

    public ILiveListPresenter(BaseView baseView) {
        mBaseView = baseView;
    }

    /**
     * 获取缓存列表
     * @return
     */
    public abstract ArrayList<LiveInfo> getLiveListFormCache();

    /**
     * 重新加载列表
     * @return
     */
    public abstract boolean reloadLiveList();

    /**
     * 加载更多
     * @return
     */
    public abstract boolean loadDataMore() ;

}
