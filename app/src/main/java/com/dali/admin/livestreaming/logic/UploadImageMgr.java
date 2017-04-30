package com.dali.admin.livestreaming.logic;

import android.content.Context;
import android.util.Log;

import com.dali.admin.livestreaming.http.AsyncHttp;
import com.dali.admin.livestreaming.http.request.RequestComm;
import com.dali.admin.livestreaming.http.request.UploadPicRequest;
import com.dali.admin.livestreaming.http.response.Response;
import com.dali.admin.livestreaming.http.response.UploadResp;

import java.io.File;

/**
 * 图片上传管理类
 * Created by dali on 2017/4/28.
 */

public class UploadImageMgr {

    private Context mContext;
    private OnUploadListener mUploadListener;

    public UploadImageMgr(Context context, OnUploadListener uploadListener) {
        mContext = context;
        mUploadListener = uploadListener;
    }

    public void uploadCover(String userId,final String path,int type){
        try {
            UploadPicRequest request = new UploadPicRequest(RequestComm.UploadPic,userId,type,new File(path));
            AsyncHttp.instance().post(request, new AsyncHttp.IHttpListener() {
                @Override
                public void onStart(int requestId) {

                }

                @Override
                public void onSuccess(int requestId, Response response) {
                    if (response.getStatus() == RequestComm.SUCCESS){
                        try {

                            UploadResp resp = (UploadResp) response.getData();
                            Log.d("PublishActivity","request url:"+resp.getUrl());
                            if (mUploadListener != null){
                                mUploadListener.onUploadResult(0,resp.getId(),resp.getUrl());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        if (mUploadListener != null){
                            mUploadListener.onUploadResult(-1,"",null);
                        }
                    }
                }

                @Override
                public void onFailure(int requestId, int httpStatus, Throwable error) {
                    if (mUploadListener != null){
                        mUploadListener.onUploadResult(-1,"",null);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface OnUploadListener{
        /**
         * 上传图片监听
         * @param code 0:成功  -1：失败
         * @param imageId 图片Id
         * @param url 图片url
         */
        void onUploadResult(int code,String imageId,String url);
    }
}
