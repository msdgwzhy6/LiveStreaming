package com.dali.admin.livestreaming.logic;

/**
 * 用户资料管理类与服务器通信的结果回调，包括查询资料的结果，修改资料的结果
 * Created by dali on 2017/4/22.
 */

public interface IUserInfoMgrListener {

    /**
     * 用户信息查询结果
     * @param error 查询结果 0表示成功，非0表示失败
     * @param errorMsg 查询失败的错误信息
     */
    void onQueryUserInfo(int error,String errorMsg);

    /**
     * 用户信息设置结果
     * @param error 查询结果 0表示成功，非0表示失败
     * @param errorMsg 查询失败的错误信息
     */
    void onSetUserInfo(int error,String errorMsg);
}
