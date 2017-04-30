package com.dali.admin.livestreaming.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.dali.admin.livestreaming.logic.IUserInfoMgrListener;
import com.dali.admin.livestreaming.logic.ImUserInfoMgr;
import com.dali.admin.livestreaming.logic.LocationMgr;
import com.dali.admin.livestreaming.logic.UploadImageMgr;
import com.dali.admin.livestreaming.mvp.presenter.Ipresenter.IPublishPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.IPublishView;
import com.dali.admin.livestreaming.utils.Constants;
import com.dali.admin.livestreaming.utils.OtherUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 发起直播Presenter
 * Created by dali on 2017/4/27.
 */

public class PublishPresenter extends IPublishPresenter {

    private IPublishView mIPublishView;
    private boolean mUploading = false;
    private String TAG = PublishPresenter.class.getSimpleName();

    public PublishPresenter(IPublishView iPublishView) {
        super(iPublishView);
        this.mIPublishView = iPublishView;
    }


    @Override
    public void start() {

    }

    @Override
    public void finish() {
        mIPublishView.finishActivity();
    }

    @Override
    public boolean checkPublishPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(activity
                        , permissions.toArray(new String[0]),
                        Constants.WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkSrcRecordPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 直接调用系统的图片裁剪功能
     * @param uri
     * @return
     */
    @Override
    public Uri cropImage(Uri uri) {
        Uri cropUri = createCoverUri("_crop");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 750);
        intent.putExtra("aspectY", 550);
        intent.putExtra("outputX", 750);
        intent.putExtra("outputY", 550);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        mIPublishView.getActivity().startActivityForResult(intent, Constants.CROP_CHOOSE);
        return cropUri;
    }

    /**
     * 存储封面图片并保存uri地址
     * @param preFileName
     * @return
     */
    private Uri createCoverUri(String preFileName) {
        String filename = ImUserInfoMgr.getInstance().getUserId() + preFileName + ".jpg";
        String path = Environment.getExternalStorageDirectory() + "/cniao_live";

        File outputImage = new File(path, filename);
        System.out.println("img file:"+outputImage.getAbsolutePath());
        if (ContextCompat.checkSelfPermission(mIPublishView.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mIPublishView.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_PERMISSION_REQ_CODE);
            return null;
        }
        try {
            File pathFile = new File(path);
            if (!pathFile.exists())
                pathFile.mkdirs();

            if (outputImage.exists())
                outputImage.delete();
        } catch (Exception e) {
            e.printStackTrace();
            mIPublishView.showMsg("生成封面失败");
        }
        return Uri.fromFile(outputImage);
    }

    @Override
    public void doPublish(String title, int liveType, String location, int bitrateType, boolean isRecord) {
        if (TextUtils.isEmpty(title)) {
            mIPublishView.showMsg("清输入非空直播标题");
        } else if (mUploading) {
            mIPublishView.showMsg("请等待封面上传完成");
        } else if (OtherUtils.getChineseNum(title) > Constants.TV_TITLE_MAX_LEN) {
            mIPublishView.showMsg("直播标题过长，最大长度为" + Constants.TV_TITLE_MAX_LEN);
        } else if (!OtherUtils.checkNetWorkState(mIPublishView.getContext())) {
            mIPublishView.showMsg("当前网络环境不能发布直播");
        } else {
            if (liveType == Constants.RECORD_TYPE_SCREEN) {
                //录屏直播
                mBaseView.showMsg("录屏直播");
            } else {
                mBaseView.showMsg("摄像头直播");
            }
        }
    }

    public LocationMgr.onLocationListener getOnLocationListener() {
        return mOnLocationListener;
    }

    private LocationMgr.onLocationListener mOnLocationListener = new LocationMgr.onLocationListener() {
        @Override
        public void onLocationChanged(int code, double lat1, double long1, String location) {
            if (0 == code) {
                mIPublishView.doLocationSuccess(location);
                ImUserInfoMgr.getInstance().setLocation(location, lat1, long1, new IUserInfoMgrListener() {
                    @Override
                    public void onQueryUserInfo(int error, String errorMsg) {

                    }

                    @Override
                    public void onSetUserInfo(int error, String errorMsg) {
                        if (0 != error) {
                            mIPublishView.showMsg("设置位置信息失败" + errorMsg);
                        }
                    }
                });
            } else {
                mIPublishView.doLocationFailed();
            }
        }
    };

    @Override
    public void doLocation() {
        if (LocationMgr.checkLocationPermission(mIPublishView.getActivity())) {
            boolean success = LocationMgr.getMyLocation(mIPublishView.getActivity(), mOnLocationListener);
            if (!success) {
                mIPublishView.doLocationFailed();
            }
        }
    }

    @Override
    public Uri pickImage(boolean mPermission, int type) {
        Uri fileUri = null;
        if (!mPermission) {
            mIPublishView.showMsg("权限不足");
            return null;
        }
        switch (type) {
            case Constants.PICK_IMAGE_CAMERA:
                fileUri = createCoverUri("");
                Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                mIPublishView.getActivity().startActivityForResult(intent_photo, Constants.PICK_IMAGE_CAMERA);
                break;
            case Constants.PICK_IMAGE_LOCAL:
                fileUri = createCoverUri("_select");
                Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
                intent_album.setType("image/*");
                mIPublishView.getActivity().startActivityForResult(intent_album, Constants.PICK_IMAGE_LOCAL);
                break;
        }
        return fileUri;
    }

    @Override
    public void doUploadPic(String path) {
        mUploading = true;

//        try {
//            final UploadPicRequest request = new UploadPicRequest(1000,
//                    ACache.get(mIPublishView.getContext()).getAsString("user_id"),
//                    Constants.LIVE_COVER_TYPE,new File(path));
//            AsyncHttp.instance().post(request, new AsyncHttp.IHttpListener() {
//                @Override
//                public void onStart(int requestId) {
//
//                }
//
//                @Override
//                public void onSuccess(int requestId, Response response) {
//                    if (response!=null) {
//                        UploadResp resp = (UploadResp) response.getData();
//                        Log.i(TAG, "onSuccess url:" + resp.getUrl());
//                        mIPublishView.doUploadSuccess(resp.getUrl());
//                    }else {
//                        Log.i(TAG, "onSuccess url:");
//                    }
//                }
//
//                @Override
//                public void onFailure(int requestId, int httpStatus, Throwable error) {
//                    Log.i(TAG, "onFailure :" + error);
//                    mIPublishView.doLocationFailed();
//                }
//            });
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        UploadImageMgr uploadImageMgr =  new UploadImageMgr(mIPublishView.getActivity(), new UploadImageMgr.OnUploadListener() {
            @Override
            public void onUploadResult(int code, String imageId, String url) {
                if (0 == code) {
                    ImUserInfoMgr.getInstance().setUserCoverPic(url, new IUserInfoMgrListener() {
                        @Override
                        public void onQueryUserInfo(int error, String errorMsg) {

                        }

                        @Override
                        public void onSetUserInfo(int error, String errorMsg) {

                        }
                    });
                    Log.i("PublishActivity", "onUploadResult url:" + url);
                    mIPublishView.showMsg("上传封面成功");
                    mIPublishView.doUploadSuccess(url);
                } else {
                    mIPublishView.showMsg("上传封面失败，错误码" + code);
                }
                mUploading = false;
            }
        });

        uploadImageMgr.uploadCover(ImUserInfoMgr.getInstance().getUserId(),path,Constants.LIVE_COVER_TYPE);
    }
}
