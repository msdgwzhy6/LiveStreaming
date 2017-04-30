package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 注册接口请求
 */
public class RegisterRequest extends IRequest {

	public RegisterRequest(int requestId, String userName, String password) {
		mRequestId = requestId;
		mParams.put("action", "register");
		mParams.put("userName", userName);
		mParams.put("password", password);
		mParams.put("rePassword", password);
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
