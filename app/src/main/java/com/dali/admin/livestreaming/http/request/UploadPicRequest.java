package com.dali.admin.livestreaming.http.request;

import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.http.response.UploadResp;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;


/**
 * @description: 图片上传请求
 */
public class UploadPicRequest  extends IRequest {

    public UploadPicRequest(int requestId, String userId, int type, File file) throws FileNotFoundException {
        mRequestId = requestId;
        mParams.put("userId",userId);
        mParams.put("type",type);
        mParams.put("file",file);
    }

    @Override
    public String getUrl() {
        return getHost() + "Image/upload";
    }

    @Override
    public Type getParserType() {
        return new TypeToken<Response<UploadResp>>() {}.getType();
    }
}