package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 直播状态接口请求
 */
public class LiveStatusRequest extends IRequest {

    public LiveStatusRequest(int requestId, String userId , int status) {
        mRequestId = requestId;
        mParams.put("action","liveStatus");
        mParams.put("userId",userId);
        mParams.put("status", status);
    }

    @Override
    public String getUrl() {
        return getHost() + "Live";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response>() {}.getType();
    }
}
