package com.dali.admin.livestreaming.mvp.view.Iview;

import android.app.Activity;

/**
 * Created by dali on 2017/4/27.
 */

public interface IPublishView extends BaseView{

    Activity getActivity();

    /**
     * 定位成功
     * @param location 位置
     */
    void doLocationSuccess(String location);

    /**
     * 定位失败
     */
    void doLocationFailed();

    /**
     * 图片上传成功
     * @param url 路径
     */
    void doUploadSuccess(String url);

    /**
     * 图片上传失败
     * @param url 路径
     */
    void doUploadFailed(String url);

    /**
     * 结束页面
     */
    void finishActivity();
}
