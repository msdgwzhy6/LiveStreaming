package com.dali.admin.livestreaming.logic;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.dali.admin.livestreaming.mvp.model.UserInfo;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

/**
 * IM用户资料管理类
 * Created by dali on 2017/4/22.
 */

public class ImUserInfoMgr {

    private String TAG = getClass().getName();

    private TCUserinfo mTCUserinfo;

    public class TCUserinfo {
        public String userId;
        public String loginSig;
        public String cosSig;

        public String nickName;//昵称
        public String headPic;//头像
        public String coverPic;//直播封面
        public TIMFriendGenderType sex;//性别
        public String selfSignature;//个性签名

        public String location;
        public double latitude;
        public double longitude;

    }

    private ImUserInfoMgr() {
        mTCUserinfo = new TCUserinfo();
    }

    private static ImUserInfoMgr instance;

    public static ImUserInfoMgr getInstance() {
        if (instance == null) {
            synchronized (ImUserInfoMgr.class) {
                instance = new ImUserInfoMgr();
            }
        }
        return instance;
    }

    /**
     * 查询用户资料
     *
     * @param listener 查询结果回调
     */
    public void queryUserInfo(final IUserInfoMgrListener listener) {
        try {
            TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
                @Override
                public void onError(int i, String s) {
                    if (listener != null) {
                        listener.onQueryUserInfo(i, s);
                    }
                }

                @Override
                public void onSuccess(TIMUserProfile timUserProfile) {
                    if (!TextUtils.isEmpty(timUserProfile.getIdentifier()))
                        mTCUserinfo.userId = timUserProfile.getIdentifier();
                    if (!TextUtils.isEmpty(timUserProfile.getNickName()))
                        mTCUserinfo.nickName = timUserProfile.getNickName();
                    if (!TextUtils.isEmpty(timUserProfile.getFaceUrl()))
                        mTCUserinfo.headPic = timUserProfile.getFaceUrl();
                    if (!TextUtils.isEmpty(timUserProfile.getFaceUrl()))
                        mTCUserinfo.coverPic = timUserProfile.getFaceUrl();

                    mTCUserinfo.sex = timUserProfile.getGender();
                    if (!TextUtils.isEmpty(timUserProfile.getLocation())) {
                        mTCUserinfo.location = timUserProfile.getLocation();
                    }

                    if (listener != null) {
                        listener.onQueryUserInfo(0, null);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置用户Id，并使用Id向服务器查询用户信息
     * setUserId 一般在登录成功之后调用，用户信息需要提供给其它类使用，或者展示给用户，
     * 因此登录成功之后需要立即向服务器查询用户信息
     *
     * @param userId
     * @param listener
     */
    public void setUserId(final String userId, final IUserInfoMgrListener listener) {
        mTCUserinfo.userId = userId;
        if (listener != null) {
            queryUserInfo(new IUserInfoMgrListener() {
                @Override
                public void onQueryUserInfo(int error, String errorMsg) {
                    if (0 == error) {
                        mTCUserinfo.userId = userId;
                    } else {
                        Log.e(TAG, "setUserId failed:" + error + "," + errorMsg);
                    }
                    if (null != listener) {
                        listener.onQueryUserInfo(error, errorMsg);
                    }
                }

                @Override
                public void onSetUserInfo(int error, String errorMsg) {
                    if (null != listener) {
                        listener.onSetUserInfo(error, errorMsg);
                    }
                }
            });
        }
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     * @param listener 设置结果回调
     */
    public void setUserNickName(final String nickname, final IUserInfoMgrListener listener) {
        if (null != mTCUserinfo.nickName && mTCUserinfo.nickName.equals(nickname)) {
            if (null != listener) {
                listener.onSetUserInfo(0, null);
            }
            return;
        }

        mTCUserinfo.nickName = nickname;
        TIMFriendshipManager.getInstance().setNickName(nickname,
                new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "setUserNickName failed:" + i + "," + s);
                        if (null != listener) {
                            listener.onSetUserInfo(i, s);
                        }
                    }

                    @Override
                    public void onSuccess() {
                        mTCUserinfo.nickName = nickname;
                        if (null != listener) {
                            listener.onSetUserInfo(0, null);
                        }
                    }
                });
    }

    /**
     * 设置签名
     *
     * @param sign     签名
     * @param listener 设置结果回调
     */
    public void setUserSign(final String sign, final IUserInfoMgrListener listener) {
        if (mTCUserinfo.selfSignature.equals(sign)) {
            if (null != listener)
                listener.onSetUserInfo(0, null);
            return;
        }

        mTCUserinfo.selfSignature = sign;
        TIMFriendshipManager.getInstance().setNickName(sign,
                new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "setUserNickName failed:" + i + "," + s);
                        if (null != listener) {
                            listener.onSetUserInfo(i, s);
                        }
                    }

                    @Override
                    public void onSuccess() {
                        mTCUserinfo.selfSignature = sign;
                        if (null != listener) {
                            listener.onSetUserInfo(0, null);
                        }
                    }
                });
    }


    /**
     * 设置性别
     *
     * @param sex      性别
     * @param listener 设置结果回调
     */
    public void setUserSex(final TIMFriendGenderType sex, final IUserInfoMgrListener listener) {
        if (mTCUserinfo.sex.equals(sex)) {
            if (null != listener)
                listener.onSetUserInfo(0, null);
            return;
        }

        mTCUserinfo.sex = sex;
        TIMFriendshipManager.getInstance().setGender(sex,
                new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "setUserNickName failed:" + i + "," + s);
                        if (null != listener) {
                            listener.onSetUserInfo(i, s);
                        }
                    }

                    @Override
                    public void onSuccess() {
                        mTCUserinfo.sex = sex;
                        if (null != listener) {
                            listener.onSetUserInfo(0, null);
                        }
                    }
                });
    }

    /**
     * 设置头像
     * 设置头像前，首先会将该图片上传到服务器存储，之后服务器返回图片的存储URL，
     * 再调用setUserHeadPic将URL存储到服务器，以后查询头像就使用该URL到服务器下载。
     *
     * @param url      头像的存储URL
     * @param listener 设置结果回调
     */
    public void setUserHeadPic(final String url, final IUserInfoMgrListener listener) {
        mTCUserinfo.headPic = url;
        TIMFriendshipManager.getInstance().setFaceUrl(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "setUserHeadPic failed:" + i + "," + s);
                if (null != listener)
                    listener.onSetUserInfo(i, s);
            }

            @Override
            public void onSuccess() {
                mTCUserinfo.headPic = url;
                if (null != listener)
                    listener.onSetUserInfo(0, null);
            }
        });
    }

    /**
     * 设置直播封面
     * 设置直播封面前，首先会将该图片上传到服务器存储，之后服务器返回图片的存储URL，
     * 再调用setUserCoverPic将URL存储到服务器，以后要查询直播封面就使用该URL到服务器下载
     *
     * @param url      直播封面的存储URL
     * @param listener 设置结果回调
     */
    public void setUserCoverPic(final String url, final IUserInfoMgrListener listener) {
        mTCUserinfo.coverPic = url;
        TIMFriendshipManager.getInstance().setSelfSignature(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "setUserCoverPic failed:" + i + "," + s);
                if (null != listener)
                    listener.onSetUserInfo(i, s);
            }

            @Override
            public void onSuccess() {
                mTCUserinfo.coverPic = url;
                if (null != listener)
                    listener.onSetUserInfo(0, null);
            }
        });
    }

    /**
     * 设置用户定位信息
     *
     * @param location  详细定位信息
     * @param latitude  纬度
     * @param longitude 经度
     * @param listener  设置结果回调
     */
    public void setLocation(@NonNull final String location, final double latitude, final double longitude, final IUserInfoMgrListener listener) {
        if (mTCUserinfo.location != null && mTCUserinfo.location.equals(location)) {
            if (null != listener)
                listener.onSetUserInfo(0, null);
            return;
        }
        mTCUserinfo.latitude = latitude;
        mTCUserinfo.longitude = longitude;
        mTCUserinfo.location = location;
        TIMFriendshipManager.getInstance().setLocation(location, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "setLocation failed:" + i + "," + s);
                if (null != listener)
                    listener.onSetUserInfo(i, s);
            }

            @Override
            public void onSuccess() {
                mTCUserinfo.latitude = latitude;
                mTCUserinfo.longitude = longitude;
                mTCUserinfo.location = location;
                if (null != listener)
                    listener.onSetUserInfo(0, null);
            }
        });
    }

    public String getUserId() {
        return mTCUserinfo.userId;
    }

    public String getNickname() {
        return mTCUserinfo.nickName;
    }

    public String getHeadPic() {
        return mTCUserinfo.headPic;
    }

    public String getCoverPic() {
        return mTCUserinfo.coverPic;
    }

    public TIMFriendGenderType getSex() {
        return mTCUserinfo.sex;
    }

    public String getLocation() {
        return mTCUserinfo.location;
    }

    public String getCosSig() {
        return mTCUserinfo.cosSig;
    }

    public UserInfo getUserInfo() {
        int sex = mTCUserinfo.sex == TIMFriendGenderType.Male ? 0 : 1;
        return new UserInfo(mTCUserinfo.nickName, sex, mTCUserinfo.headPic, mTCUserinfo.userId);
    }

    public void setUserInfo(UserInfo info) {
        if (info != null) {
            if (!TextUtils.isEmpty(info.getUserId())) {
                setUserId(info.getUserId(),null);
            }

            if (!TextUtils.isEmpty(info.getNickname())) {
                setUserNickName(info.getNickname(),null);
            }

            if (!TextUtils.isEmpty(info.getHeadPic())) {
                setUserHeadPic(info.getHeadPic(),null);
            }

            final TIMFriendGenderType sexType = (info.getSex() == 0?TIMFriendGenderType.Male:TIMFriendGenderType.Female);
            setUserSex(sexType,null);
        }
    }
}
