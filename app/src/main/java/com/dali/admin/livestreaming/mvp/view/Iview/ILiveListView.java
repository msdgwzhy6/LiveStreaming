package com.dali.admin.livestreaming.mvp.view.Iview;

import com.dali.admin.livestreaming.model.LiveInfo;

import java.util.ArrayList;

/**
 * 列表数据展示
 * Created by dali on 2017/5/8.
 */

public interface ILiveListView extends BaseView {
    /**
     * 展示数据
     * @param retCode 获取结果，0表示成功
     * @param datas 列表数据
     * @param state 状态
     */
    void onLiveList(int retCode, final ArrayList<LiveInfo> datas, int state);
}
