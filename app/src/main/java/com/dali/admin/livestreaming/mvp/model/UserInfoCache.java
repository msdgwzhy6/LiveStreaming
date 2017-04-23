package com.dali.admin.livestreaming.mvp.model;

import android.content.Context;
import android.text.TextUtils;

import com.dali.admin.livestreaming.http.IDontObfuscate;
import com.dali.admin.livestreaming.utils.AsimpleCache.ACache;
import com.dali.admin.livestreaming.utils.Constants;

/**
 * Created by dali on 2017/4/9.
 */

public class UserInfoCache extends IDontObfuscate{

    public static void saveCache(Context context, UserInfo info){
        ACache.get(context).put("user_id",info.getUserId());
        ACache.get(context).put("nickname",info.getNickname());
        ACache.get(context).put("head_pic",info.getHeadPic());
        ACache.get(context).put("sig_id",info.getSigId());
        ACache.get(context).put("token",info.getToken());
        ACache.get(context).put("sdk_app_id",info.getSdkAppId());
        ACache.get(context).put("adk_account_type",info.getSdkAccountType());
        ACache.get(context).put("sex",info.getSex());

        if (info.getSdkAppId() != null && TextUtils.isDigitsOnly(info.getSdkAccountType())){
            Constants.IMSDK_ACCOUNT_TYPE = Integer.parseInt(info.getSdkAccountType());
        }
    }


    public static String getUserId(Context context){
        return ACache.get(context).getAsString("user_id");
    }

    public static String getNickname(Context context){
        return ACache.get(context).getAsString("nickname");
    }

    public static String getHeadPic(Context context){
        return ACache.get(context).getAsString("head_pic");
    }

    public static String getSigId(Context context){
        return ACache.get(context).getAsString("sig_id");
    }

    public static String getToken(Context context){
        return ACache.get(context).getAsString("token");
    }

    public static String getSdkAccountType(Context context){
        return ACache.get(context).getAsString("adk_account_type");
    }

    public static String getSdkAppId(Context context){
        return ACache.get(context).getAsString("sex");
    }

    public static String getSex(Context context){
        return ACache.get(context).getAsString("sdk_app_id");
    }

    public static void clearCache(Context context){
        ACache.get(context).remove("user_id");
        ACache.get(context).remove("nickname");
        ACache.get(context).remove("head_pic");
        ACache.get(context).remove("sig_id");
        ACache.get(context).remove("token");
        ACache.get(context).remove("adk_account_type");
        ACache.get(context).remove("sdk_app_id");
        ACache.get(context).remove("sex");
    }
}
