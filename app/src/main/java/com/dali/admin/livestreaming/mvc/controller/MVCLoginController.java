package com.dali.admin.livestreaming.mvc.controller;

import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.LoginRequest;
import com.dali.admin.livestreaming.http.request.PhoneLoginRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.request.VerifyCodeRequest;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.mvc.controller.Icontroller.MVCILoginController;
import com.dali.admin.livestreaming.mvc.model.MVCUSerInfoCache;
import com.dali.admin.livestreaming.mvc.view.MVCLoginView;
import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.dali.admin.livestreaming.utils.AsimpleCache.ACache;
import com.dali.admin.livestreaming.utils.AsimpleCache.CacheConstants;
import com.dali.admin.livestreaming.utils.OtherUtils;

/**
 * Created by dali on 2017/4/10.
 */

public class MVCLoginController extends MVCILoginController {

    private MVCLoginView mLoginView;

    public MVCLoginController(UserInfo userInfo,MVCLoginView loginView) {
        super(userInfo);
        mLoginView = loginView;
    }

    @Override
    public boolean checkPhoneLogin(String phone, String verifyCode) {
        if (OtherUtils.isPhoneNumValid(phone)) {
            if (OtherUtils.isVerifyCodeValid(verifyCode)) {
                if (OtherUtils.checkNetWorkState(mLoginView.getContext())) {
                    return true;
                } else {
                    mLoginView.showMsg("网络未连接");
                }
            } else {
                mLoginView.verifyCodeError("验证码错误！");
            }
        } else {
            mLoginView.phoneError("手机格式错误！");
        }
        mLoginView.dismissLoading();
        return false;
    }

    @Override
    public boolean checkUserNameLogin(String userName, String password) {
        if (OtherUtils.isUsernameVaild(userName)) {
            if (OtherUtils.isPasswordValid(password)) {
                if (OtherUtils.checkNetWorkState(mLoginView.getContext())) {
                    return true;
                } else {
                    mLoginView.showMsg("当前无网络连接！");
                }
            } else {
                mLoginView.passwordError("密码过短！");
            }
        } else {
            mLoginView.usernameError("用户名不符合规范！");
        }
        mLoginView.dismissLoading();
        return false;
    }

    @Override
    public void phoneLogin(final String phone, String verifyCode) {
        if (checkPhoneLogin(phone, verifyCode)) {
            final PhoneLoginRequest request = new PhoneLoginRequest(RequestComm.loginPhone, phone, verifyCode);
            AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
                @Override
                public void onStart(int requestId) {
                    mLoginView.showLoading();
                }

                @Override
                public void onSuccess(int requestId, Response response) {

                    if (response.getStatus() == RequestComm.SUCCESS) {
                        ACache.get(mLoginView.getContext()).put(CacheConstants.LOGIN_PHONE, phone);
                        mLoginView.loginSuccess();
                    } else {
                        mLoginView.loginFailed(response.getStatus(), response.getMsg());
                    }
                    mLoginView.dismissLoading();
                }

                @Override
                public void onFailure(int requestId, int httpStatus, Throwable error) {
                    mLoginView.verifyCodeFailed("网络异常");
                    mLoginView.dismissLoading();
                }
            });
        }
    }

    @Override
    public void userNameLogin(final String userName, final String password) {
        if (checkUserNameLogin(userName, password)) {
            LoginRequest request = new LoginRequest(RequestComm.loginUsername, userName, password);
            AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
                @Override
                public void onStart(int requestId) {
                    mLoginView.showLoading();
                }

                @Override
                public void onSuccess(int requestId, Response response) {
                    if (response.getStatus() == RequestComm.SUCCESS) {
                        UserInfo info = (UserInfo) response.getData();
                        mLoginView.setModel(info);
                        MVCUSerInfoCache.saveCache(mLoginView.getContext(), info);
                        ACache.get(mLoginView.getContext()).getAsString(CacheConstants.LOGIN_USERNAME);
                        ACache.get(mLoginView.getContext()).put(CacheConstants.LOGIN_USERNAME, userName);
                        ACache.get(mLoginView.getContext()).put(CacheConstants.LOGIN_PASSWORD, password);
                        mLoginView.loginSuccess();
                    } else {
                        mLoginView.loginFailed(response.getStatus(), response.getMsg());
                        mLoginView.dismissLoading();
                    }
                }

                @Override
                public void onFailure(int requestId, int httpStatus, Throwable error) {
                    mLoginView.loginFailed(httpStatus, error.getMessage());
                    mLoginView.dismissLoading();
                }
            });
        }
    }

    @Override
    public void sendVerifyCode(String phoneNum) {
        if (OtherUtils.isPhoneNumValid(phoneNum)) {
            if (OtherUtils.checkNetWorkState(mLoginView.getContext())) {
                VerifyCodeRequest request = new VerifyCodeRequest(RequestComm.verifyCodeRequestId, phoneNum);
                AsyncHttp.instance().postJson(request, new AsyncHttp.IHttpListener() {
                    @Override
                    public void onStart(int requestId) {
                        mLoginView.showLoading();
                    }

                    @Override
                    public void onSuccess(int requestId, Response response) {
                        if (response.getStatus() == RequestComm.SUCCESS) {
                            UserInfo info = (UserInfo) response.getData();
//                            mLoginView.setModel(info);
                            if (null != mLoginView){
                                mLoginView.verifyCodeSuccess(60,60);
                            }
                        }else {
                            mLoginView.verifyCodeFailed("获取后台验证码失败！");
                        }
                        mLoginView.dismissLoading();
                    }

                    @Override
                    public void onFailure(int requestId, int httpStatus, Throwable error) {

                        if (null != mLoginView){
                            mLoginView.verifyCodeFailed("获取后台验证码失败！");
                        }
                        mLoginView.dismissLoading();
                    }
                });
            } else {
                mLoginView.showMsg("当前无网络连接！");
            }
        } else {
            mLoginView.phoneError("手机号码不符合规范！");
        }
    }
}
