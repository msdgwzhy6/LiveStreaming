package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.CreateLiveResp;
import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * @description: 创建直播接口请求
 */
public class CreateLiveRequest extends IRequest {

    public CreateLiveRequest(int requestId, String userId , String groupId, String title, String liveCover, String location,int isRecord) {
        mRequestId = requestId;
        mParams.put("action","createLive");
        mParams.put("userId",userId);
        mParams.put("groupId",groupId);
        mParams.put("title", title);
        mParams.put("liveCover", liveCover);
        mParams.put("location", location);
        mParams.put("isRecord", isRecord);
    }

    @Override
    public String getUrl() {
        return getHost() + "Live";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response<CreateLiveResp>>() {
        }.getType();
    }
}
