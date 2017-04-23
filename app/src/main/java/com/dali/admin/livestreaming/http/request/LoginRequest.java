package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 登陆请求
 *
 * @author: dali
 * @time: 2017/13/3
 */
public class LoginRequest extends IRequest {

	public LoginRequest(int requestId, String userName, String password) {
		mRequestId = requestId;
		mParams.put("action", "login");//普通账号登录
//		mParams.put("action", "loginCniaow");//发起直播需要调用这个接口，使用菜鸟窝账号并且购买了直播课程
		mParams.put("userName", userName);
		mParams.put("password", password);
//		if (mParams.getUrlParams("action").equals("loginCniaow")) {
//			mParams.put("password", CipherUtil.getAESInfo(password));
//		} else {
//			mParams.put("password", password);
//		}
	}

	@Override
	public String getUrl() {
		return getHost() + "User";
	}

	@Override
	public Type getParserType() {
		return new TypeToken<Response<UserInfo>>() {
		}.getType();
	}
}
