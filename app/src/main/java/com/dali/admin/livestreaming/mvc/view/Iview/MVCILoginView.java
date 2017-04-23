package com.dali.admin.livestreaming.mvc.view.Iview;

/**
 * Created by dali on 2017/4/10.
 */

public interface MVCILoginView extends MVCBaseView {

    /**
     * 登录成功
     */
    void loginSuccess();

    /**
     * 登录失败
     * @param status
     * @param msg
     */
    void loginFailed(int status, String msg);

    /**
     * 用户名错误
     * @param errorMsg
     */
    void usernameError(String errorMsg);

    /**
     *手机号错误
     * @param errorMsg
     */
    void phoneError(String errorMsg);

    /**
     * 密码错误
     * @param errorMsg
     */
    void passwordError(String errorMsg);

    /**
     * 验证码错误
     * @param errorMsg
     */
    void verifyCodeError(String errorMsg);

    /**
     * 验证失败
     * @param errorMsg
     */
    void verifyCodeFailed(String errorMsg);

    /**
     * 验证成功
     * @param reaskDuration
     * @param expireDuration
     */
    void verifyCodeSuccess(int reaskDuration, int expireDuration);

}
