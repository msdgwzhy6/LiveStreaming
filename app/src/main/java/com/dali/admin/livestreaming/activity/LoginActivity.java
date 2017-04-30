package com.dali.admin.livestreaming.activity;


import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.mvp.presenter.LoginPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.ILoginView;
import com.dali.admin.livestreaming.utils.AsimpleCache.ACache;
import com.dali.admin.livestreaming.utils.AsimpleCache.CacheConstants;
import com.dali.admin.livestreaming.utils.OtherUtils;

import java.lang.ref.WeakReference;

import static com.dali.admin.livestreaming.R.id.til_login;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    //共用控件
    private ProgressBar progressBar;
    private EditText etPassword;
    private EditText etLogin;
    private Button btnLogin;
    private Button btnPhoneLogin;
    private TextInputLayout tilLogin, tilPassword;
    private Button btnRegister;
    //手机验证登陆控件
    private TextView tvVerifyCode;
    private boolean isPhoneLogin = false;

    private LoginPresenter mLoginPresenter;


    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        etLogin.setText(ACache.get(this).getAsString(CacheConstants.LOGIN_USERNAME));
        etPassword.setText(ACache.get(this).getAsString(CacheConstants.LOGIN_PASSWORD));
    }

    @Override
    protected void initView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mLoginPresenter = new LoginPresenter(this);

        etLogin = obtainView(R.id.et_username);
        etPassword = obtainView(R.id.et_password);
        btnRegister = obtainView(R.id.btn_register);
        btnPhoneLogin = obtainView(R.id.btn_phone_login);
        btnLogin = obtainView(R.id.btn_login);
        progressBar = obtainView(R.id.progressbar);
        tilLogin = obtainView(til_login);
        tilPassword = obtainView(R.id.til_password);
        tvVerifyCode = obtainView(R.id.btn_verify_code);

        userNameLoginViewInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.setIMLoginListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.removeIMLoginListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 用户名密码登录界面init
     */
    public void userNameLoginViewInit() {
        //用户名登录切换
        userLoginTrans();

        tvVerifyCode.setOnClickListener(this);
        //注册
        btnRegister.setOnClickListener(this);

        //手机号登录
        btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //手机号登录
                phoneLoginViewinit();
            }
        });

        //用户名登录
        btnLogin.setOnClickListener(this);

    }

    public void phoneLoginViewinit() {
        phoneLoginTrans();

        btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转换为用户名登录界面
                userNameLoginViewInit();
            }
        });

        btnLogin.setOnClickListener(this);

        btnRegister.setOnClickListener(this);

        tvVerifyCode.setOnClickListener(this);
    }

    private void phoneLoginTrans() {
        isPhoneLogin = true;
        tvVerifyCode.setVisibility(View.VISIBLE);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(250);
        tvVerifyCode.setAnimation(alphaAnimation);

        //设定点击优先级于最前（避免被EditText遮挡的情况）
        tvVerifyCode.bringToFront();

        //设置输入框输入类型为 手机号
        etLogin.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        etLogin.setText("");
        etPassword.setText("");

        //手机号登录按钮文字改为 用户名登录
        btnPhoneLogin.setText("用户名登录");
        tilLogin.setHint("手机号");
        tilPassword.setHint("密码");
    }

    private void userLoginTrans() {
        isPhoneLogin = false;
        tvVerifyCode.setVisibility(View.GONE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(250);
        tvVerifyCode.setAnimation(alphaAnimation);
        etLogin.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        etLogin.setText("");
        etPassword.setText("");
        btnPhoneLogin.setText("手机号登录");
        tilLogin.setHint("用户名");
        tilPassword.setHint("密码");
    }

    /**
     * 手机登录和用户名登录界面显示或隐藏
     *
     * @param active
     */
    public void showOnLoading(boolean active) {
        if (active) {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            etLogin.setEnabled(false);
            etPassword.setEnabled(false);
            btnPhoneLogin.setClickable(false);
            btnRegister.setTextColor(getResources().getColor(R.color.colorTransparentGray));
            btnPhoneLogin.setTextColor(getResources().getColor(R.color.colorTransparentGray));
            btnRegister.setClickable(false);
        } else {
            progressBar.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            etLogin.setEnabled(true);
            etPassword.setEnabled(true);
            btnPhoneLogin.setClickable(true);
            btnRegister.setClickable(true);
            btnRegister.setTextColor(getResources().getColor(R.color.white));
            btnPhoneLogin.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                if (isPhoneLogin) {
                    mLoginPresenter.phoneLogin(etLogin.getText().toString(), etPassword.getText().toString());
                } else {
                    mLoginPresenter.userNameLogin(etLogin.getText().toString(), etPassword.getText().toString());
                }
                break;

            case R.id.btn_verify_code:
                mLoginPresenter.sendVerifyCode(etLogin.getText().toString());
                break;
            case R.id.btn_register:

                break;
        }

    }

    @Override
    public void loginSuccess() {
        dismissLoading();
        showMsg("登录成功");
        invoke(this, MainActivity.class);
        finish();
    }

    @Override
    public void loginFailed(int status, String msg) {
        dismissLoading();
        showMsg("登录失败：" + msg);
    }

    @Override
    public void usernameError(String errorMsg) {
        etLogin.setError(errorMsg);
    }

    @Override
    public void phoneError(String errorMsg) {
        etLogin.setError(errorMsg);
    }

    @Override
    public void passwordError(String errorMsg) {
        etPassword.setError(errorMsg);
    }

    @Override
    public void verifyCodeError(String errorMsg) {
        showMsg(errorMsg);
    }

    @Override
    public void verifyCodeFailed(String errorMsg) {
        showMsg(errorMsg);
    }

    @Override
    public void verifyCodeSuccess(int reaskDuration, int expireDuration) {
        showMsg("注册短信下发，验证码 " + expireDuration / 60 + " 分钟内有效！");
        OtherUtils.startTimer(new WeakReference<TextView>(tvVerifyCode), "验证码", reaskDuration, 1);
    }

    @Override
    public void showLoading() {
        showOnLoading(true);
    }

    @Override
    public void dismissLoading() {
        showOnLoading(false);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showMsg(int msgId) {
        showMsg(msgId);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
