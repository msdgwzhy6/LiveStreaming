package com.dali.admin.livestreaming.mvp.presenter;

import android.util.Log;

import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.LoginRequest;
import com.dali.admin.livestreaming.http.request.PhoneLoginRequest;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.request.VerifyCodeRequest;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.logic.IMLogin;
import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.dali.admin.livestreaming.mvp.model.UserInfoCache;
import com.dali.admin.livestreaming.mvp.presenter.Ipresenter.ILoginPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.ILoginView;
import com.dali.admin.livestreaming.utils.AsimpleCache.ACache;
import com.dali.admin.livestreaming.utils.AsimpleCache.CacheConstants;
import com.dali.admin.livestreaming.utils.OtherUtils;

/**
 * Created by dali on 2017/4/7.
 */

public class LoginPresenter extends ILoginPresenter implements IMLogin.IMLoginListener {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private ILoginView mLoginView;

    private IMLogin mIMLogin = IMLogin.getInstace();

    public LoginPresenter(ILoginView loginView) {
        super(loginView);
        mLoginView = loginView;
    }

    public void setIMLoginListener() {
        mIMLogin.setIMLoginListener(this);
    }

    public void removeIMLoginListener() {
        mIMLogin.removeIMLoginListener();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {
        if (mLoginView != null) {
            mLoginView = null;
        }
    }

    @Override
    public boolean checkPhoneLogin(String phone, String verifyCode) {
        if (OtherUtils.isPhoneNumValid(phone)) {
            if (OtherUtils.isVerifyCodeValid(verifyCode)) {
                if (OtherUtils.checkNetWorkState(mLoginView.getContext())) {
                    return true;
                } else {
                    mLoginView.showMsg("当前无网络连接！");
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
    public void phoneLogin(final String phone, final String verifyCode) {

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

                    UserInfo info = (UserInfo) response.getData();
                    Log.e("token",info.getToken());
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

//                        if (TextUtils.isEmpty(info.getSdkAppId()) && TextUtils.isEmpty(info.getSdkAccountType())) {
//                            try {
//                                Constants.IMSDK_APPID = Integer.parseInt(info.getSdkAppId());
//                                Constants.IMSDK_ACCOUNT_TYPE = Integer.parseInt(info.getSdkAccountType());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                        }
                        UserInfoCache.saveCache(mLoginView.getContext(), info);
                        ACache.get(mLoginView.getContext()).put(CacheConstants.LOGIN_USERNAME, userName);
                        ACache.get(mLoginView.getContext()).put(CacheConstants.LOGIN_PASSWORD, password);

                        mIMLogin.setIMLoginListener(LoginPresenter.this);
                        //IM登录
                        mIMLogin.imLogin(info.getUserId(), info.getSigId());

                        mLoginView.loginSuccess();
                        Log.e("onSuccess","login success");

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
                            if (null != mLoginView) {
                                mLoginView.verifyCodeSuccess(60, 60);
                            }
                        } else {
                            mLoginView.verifyCodeFailed("获取后台验证码失败！");
                        }
                        mLoginView.dismissLoading();
                    }

                    @Override
                    public void onFailure(int requestId, int httpStatus, Throwable error) {

                        if (null != mLoginView) {
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

    @Override
    public void onSuccess() {

        Log.e(TAG, "onSuccess:login success");

        //登录成功，设置登录用户信息
//        ImUserInfoMgr.getInstance().setUserId(mIMLogin.getLastUserInfo().identifier, new IUserInfoMgrListener() {
//            @Override
//            public void onQueryUserInfo(int error, String errorMsg) {
//
//            }
//
//            @Override
//            public void onSetUserInfo(int error, String errorMsg) {
//                if (0 != error) {
//                    Log.e(TAG, "onSetUserInfo:set userSig failed");
//                }
//            }
//        });
        //移除监听
        mIMLogin.removeIMLoginListener();
        //登录成功提示
        mLoginView.dismissLoading();
        mLoginView.loginSuccess();

    }

    @Override
    public void onFailure(int code, String msg) {
        Log.e(TAG, "onFailure:login failed");
        mLoginView.dismissLoading();
        mLoginView.loginFailed(code,msg);
    }
}
