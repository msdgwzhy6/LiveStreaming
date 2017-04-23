package com.dali.admin.livestreaming.mvc.controller.Icontroller;

import com.dali.admin.livestreaming.mvp.model.UserInfo;

/**
 * Created by dali on 2017/4/10.
 */

public abstract class MVCILoginController {

    protected UserInfo mUserInfo;

    public MVCILoginController(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    /**
     * 检查手机号验证码是否合法
     * @param phone
     * @param verifyCode
     * @return
     */
    public abstract boolean checkPhoneLogin(String phone,String verifyCode);


    /**
     * 检查用户名密码是否合法
     * @param userName
     * @param password
     * @return
     */
    public abstract boolean checkUserNameLogin(String userName,String password);

    /**
     * 手机号登录
     * @param phone
     * @param verifyCode
     */
    public abstract void phoneLogin(String phone,String verifyCode);

    /**
     * 用户名登录
     * @param userName
     * @param password
     */
    public abstract void userNameLogin(String userName,String password);

    /**
     * 发送验证码
     * @param phoneNum
     */
    public abstract void sendVerifyCode(String phoneNum);
}