package com.dali.admin.livestreaming.http.request;


import com.dali.admin.livestreaming.http.response.GroupMemberList;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @description: 观众列表请求
 */
public class FetchGroupMemberListReuest extends IRequest {

    public FetchGroupMemberListReuest(int requestId, String userId, String groupId, String liveId,
                                      String hostId, int pageIndex, int pageSize) {
        mRequestId = requestId;
        mParams.put("action", "groupMember");
        mParams.put("userId", userId);
        mParams.put("groupId", groupId);
        mParams.put("liveId", liveId);
        mParams.put("hostId", hostId);
        mParams.put("pageIndex", pageIndex);
        mParams.put("pageSize", pageSize);
    }

    @Override
    public String getUrl() {
        return getHost() + "Live";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response<GroupMemberList<UserInfo>>>() {
        }.getType();
    }
}
