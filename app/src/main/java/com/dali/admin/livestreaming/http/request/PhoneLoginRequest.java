package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 手机登陆请求
 * @author: Andruby
 * @time: 2016/11/2 18:07
 */
public class PhoneLoginRequest extends IRequest {

	public PhoneLoginRequest(int requestId, String mobile, String verifyCode) {
		mRequestId = requestId;
		mParams.put("action", "phoneLogin");
		mParams.put("mobile", mobile);
		mParams.put("verifyCode", verifyCode);
	}

	@Override
	public String getUrl() {
		return getHost() + "User";
	}

	@Override
	public Type getParserType() {
		return new TypeToken<Response>() {
		}.getType();
	}
}
