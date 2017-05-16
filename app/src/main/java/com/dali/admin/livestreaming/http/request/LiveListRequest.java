package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.ResList;
import com.dali.admin.livestreaming.http.response.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by dali on 2017/5/8.
 */

public class LiveListRequest extends IRequest {

    public LiveListRequest(int requestId, String userId, int pageIndex, int pageSize) {
        mRequestId = requestId;
        mParams.put("action", "liveListTest");
        mParams.put("userId", userId);
        mParams.put("pageIndex", pageIndex);
        mParams.put("pageSize", pageSize);
    }

    @Override
    public String getUrl() {
        return getHost() + "Live";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response<ResList>>(){}.getType();
    }
}
