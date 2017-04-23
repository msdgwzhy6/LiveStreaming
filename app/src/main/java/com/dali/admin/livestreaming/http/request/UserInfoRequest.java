package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * @description: 用户信息请求
 *
 * @author: Andruby
 * @time: 2016/11/2 18:07
 */
public class UserInfoRequest   extends IRequest {

    public UserInfoRequest(int requestId,String userId ) {
        mRequestId = requestId;
        mParams.put("action","userInfo");
        mParams.put("userId",userId);
    }

    @Override
    public String getUrl() {
        return getHost() + "Live";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response<UserInfo>>() {
        }.getType();
    }
}
