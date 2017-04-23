package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 停止直播请求
 *
 * @author: Andruby
 * @time: 2016/11/2 18:07
 */
public class StopLiveRequest extends IRequest {

    public StopLiveRequest(int requestId, String userId, String groupId) {
        mRequestId = requestId;
        mParams.put("action","stopLive");
        mParams.put("userId",userId);
        mParams.put("groupId",groupId);
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
