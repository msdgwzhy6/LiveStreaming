package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by dali on 2017/4/9.
 */

public class VerifyCodeRequest extends IRequest {

    public VerifyCodeRequest(int requestId,String mobile) {
        mRequestId = requestId;
        mParams.put("action","verifyCode");
        mParams.put("mobile",mobile);
    }

    @Override
    public String getUrl() {
        return getHost() + "User";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response>(){
        }.getType();
    }
}
