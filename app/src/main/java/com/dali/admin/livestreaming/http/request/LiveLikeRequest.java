package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 直播喜欢请求
 */
public class LiveLikeRequest extends IRequest {

    public LiveLikeRequest(int requestId, String userId, String liveId) {
        mRequestId = requestId;
        mParams.put("action","liveLike");
        mParams.put("userId",userId);
        mParams.put("liveId",liveId);
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