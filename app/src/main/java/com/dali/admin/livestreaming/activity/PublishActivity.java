package com.dali.admin.livestreaming.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseActivity;
import com.dali.admin.livestreaming.logic.LocationMgr;
import com.dali.admin.livestreaming.mvp.presenter.PublishPresenter;
import com.dali.admin.livestreaming.mvp.view.Iview.IPublishView;
import com.dali.admin.livestreaming.ui.customviews.CustomSwitch;
import com.dali.admin.livestreaming.utils.AsimpleCache.ACache;
import com.dali.admin.livestreaming.utils.Constants;
import com.dali.admin.livestreaming.utils.OtherUtils;
import com.dali.admin.livestreaming.utils.ToastUtils;

import java.io.File;

public class PublishActivity extends BaseActivity implements View.OnClickListener, IPublishView, RadioGroup.OnCheckedChangeListener {

    //               返回       发起直播      图片封面文字  是否录制文本 直播标题    位置信息显示
    private TextView mBtnBack, mBtnPublish, mTvPicTip, mTvRecord, mTvTitle, mTvLBS;
    private Dialog mPicDialog;//选择封面对话框（照相机、相册、取消）
    private ImageView mImgCover;//封面图
    private Uri mFileUri, mCropUri;//原始图片文件uri，裁剪之后的图片文件uri
    private CustomSwitch mBtnRecord, mBtnLBS;//录制，定位
    private RadioGroup mRGRecordType, mRGBitrate;//录制类型（摄像头直播、录屏直播），码率（流畅、高清、超清）
    private RelativeLayout mRLBitrate;//码率布局
    private boolean mPermission = false;//权限监测
    //直播类型，默认摄像头直播
    private int mRecordType = Constants.RECORD_TYPE_CAMERA;
    //码率，默认流畅
    private int mBitrateType = Constants.BITRATE_NORMAL;

    private PublishPresenter mPublishPresenter;
    private String TAG = PublishActivity.class.getSimpleName();


    @Override
    protected void setActionBar() {

    }

    @Override
    protected void setListener() {
        mBtnRecord.setOnClickListener(this);
        mRGBitrate.setOnCheckedChangeListener(this);
        mRGRecordType.setOnCheckedChangeListener(this);
        mImgCover.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mBtnPublish.setOnClickListener(this);
        mBtnLBS.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPublishPresenter = new PublishPresenter(this);
        mPermission = mPublishPresenter.checkPublishPermission(this);
        String strCover = ACache.get(this).getAsString("head_pic");
        if (!TextUtils.isEmpty(strCover)) {
            Log.e(TAG, "head_pic:" + strCover);
            Glide.with(this).load(strCover).into(mImgCover);
            mTvPicTip.setVisibility(View.GONE);
        } else {
            mImgCover.setImageResource(R.drawable.publish_background);
        }
    }

    @Override
    protected void initView() {
        mTvTitle = obtainView(R.id.live_title);
        mTvPicTip = obtainView(R.id.tv_pic_tip);
        mTvLBS = obtainView(R.id.address);
        mTvRecord = obtainView(R.id.tv_record);
        mBtnBack = obtainView(R.id.btn_cancel);
        mBtnPublish = obtainView(R.id.btn_publish);
        mImgCover = obtainView(R.id.cover);
        mBtnLBS = obtainView(R.id.btn_lbs);
        mBtnRecord = obtainView(R.id.btn_record);
        mRGRecordType = obtainView(R.id.rg_record_type);
        mRGBitrate = obtainView(R.id.rg_bitrate);
        mRLBitrate = obtainView(R.id.rl_bitrate);
        //初始化图片选择对话框
        initPhotoDialog();
    }

    /**
     * 封面图片选择对话框
     */
    private void initPhotoDialog() {
        mPicDialog = new Dialog(this, R.style.float_dialog);
        mPicDialog.setContentView(R.layout.dialog_pic_choose);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = mPicDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = display.getWidth();
        mPicDialog.getWindow().setAttributes(lp);
        mPicDialog.findViewById(R.id.tv_chose_camera).setOnClickListener(this);
        mPicDialog.findViewById(R.id.tv_pic_lib).setOnClickListener(this);
        mPicDialog.findViewById(R.id.tv_dialog_cancel).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //录制
            case R.id.btn_record:
                if (mBtnRecord.isChecked()) {
                    mBtnRecord.setChecked(false, true);
                    mTvRecord.setText("不进行录制");
                } else {
                    mBtnRecord.setChecked(true, true);
                    mTvRecord.setText("进行录制");
                }
                break;
            //取消直播，结束当前activity
            case R.id.btn_cancel:
                finish();
                break;
            //发起直播
            case R.id.btn_publish:
                String location = mTvLBS.getText().toString().equals("定位失败")
                        || mTvLBS.getText().toString().equals("定位中") ?
                        "不显示地理位置" : mTvLBS.getText().toString();
                mPublishPresenter.doPublish(mTvTitle.getText().toString().trim(), mRecordType, location, mBitrateType, false);
                break;
            //封面图片选择
            case R.id.cover:
                mPicDialog.show();
                break;
            //定位
            case R.id.btn_lbs:
                if (mBtnLBS.isChecked()) {
                    mBtnLBS.setChecked(false, true);
                    mTvLBS.setText("不显示地理位置");
                } else {
                    mBtnLBS.setChecked(true, true);
                    mTvLBS.setText("正在定位中");
                    mPublishPresenter.doLocation();
                }
                break;
            //相机，对拍摄图片地址进行存储，并对图片进行裁剪
            case R.id.tv_chose_camera:
                mFileUri = mPublishPresenter.pickImage(mPermission, Constants.PICK_IMAGE_CAMERA);
                mPicDialog.dismiss();
                break;
            //相册，从本地相册选择图片作为封面，并对图片进行裁剪，并将地址进行保存
            case R.id.tv_pic_lib:
                mFileUri = mPublishPresenter.pickImage(mPermission, Constants.PICK_IMAGE_LOCAL);
                mPicDialog.dismiss();
                break;
            //取消对话框按钮，表示不添加封面图片
            case R.id.tv_dialog_cancel:
                mPicDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //相机
                case Constants.PICK_IMAGE_CAMERA:
                    mCropUri = mPublishPresenter.cropImage(mFileUri);
                    Log.d("PublishActivity", "cropImage->path camera:" + mCropUri.getPath());
                    System.out.println(TAG+"PICK_IMAGE_CAMERA 选择相机图片成功");
                    Log.d("PublishActivity","PICK_IMAGE_CAMERA 选择相机图片成功");
                    break;
                //本地相册
                case Constants.PICK_IMAGE_LOCAL:
                    String path = OtherUtils.getPath(this, data.getData());
                    if (null != path) {
                        Log.d("PublishActivity", "cropImage->path local:" + path);
                        File file = new File(path);
                        mCropUri = mPublishPresenter.cropImage(Uri.fromFile(file));
                    }
                    Log.e("PublishActivity","PICK_IMAGE_LOCAL 选择相册图片成功");
                    break;
                //加载已有封面
                case Constants.CROP_CHOOSE:
                    mTvPicTip.setVisibility(View.GONE);
                    Log.d("PublishActivity", "cropImage->path crop:" + mCropUri.getPath());
                    mPublishPresenter.doUploadPic(mCropUri.getPath());
                    Log.d("PublishActivity","CROP_CHOOSE 上传图片成功");
                    break;
            }
        }
    }

    /**
     * 权限验证回调
     * <p>
     * 1、权限通过 ActivityCompat 类的 checkSelfPermission() 方法判断是否有所需权限。PublishPresenter.java # checkPublishPermission()
     * 2、权限请求是通过 ActivityCompat 类中的 requestPermissions() 方法，
     * 在 OnRequestPermissionsResultCallback # onRequestPermissionsResult() 方法中回调。PublishActivity.java # onRequestPermissionsResult()
     * 3、应用程序可以提供一个额外的合理的使用权限调用 Activitycompat # shouldShowRequestPermissionRationale() 方法。
     * Android 原生系统中，如果第二次弹出权限申请的对话框，会出现「以后不再弹出」的提示框，如果用户勾选了，你再申请权限，
     * 则 shouldShowRequestPermissionRationale() 返回 true，意思是说要给用户一个解释，告诉用户为什么要这个权限。
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 授予结果数组
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //定位权限
            case Constants.LOCATION_PERMISSION_REQ_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!LocationMgr.getMyLocation(this, mPublishPresenter.getOnLocationListener())) {
                        mTvLBS.setText("定位失败");
                        mBtnLBS.setChecked(false, false);
                    }
                }
                break;
            //写入外部存储权限
            case Constants.WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mPermission = true;
                break;
        }
    }

    //RadioGroup 选择事件
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            //流畅
            case R.id.rb_bitrate_slow:
                mBitrateType = Constants.BITRATE_SLOW;
                break;
            //高清
            case R.id.rb_bitrate_normal:
                mBitrateType = Constants.BITRATE_NORMAL;
                break;
            //超清
            case R.id.rb_bitrate_fast:
                mBitrateType = Constants.BITRATE_FAST;
                break;
            //摄像头直播
            case R.id.rb_record_camera:
                //摄像头直播
                mRecordType = Constants.RECORD_TYPE_CAMERA;
                //隐藏码率类型
                if (mRLBitrate != null) {
                    mRLBitrate.setVisibility(View.GONE);
                }
                break;
            //录屏直播
            case R.id.rb_record_screen:
                if (!mPublishPresenter.checkSrcRecordPermission()) {
                    showMsg("当前安卓系统版本过低，仅支持5.0以上系统");
                    mRGRecordType.check(R.id.rb_record_camera);
                    return;
                }
                try {
                    //监测悬浮窗权限
                    OtherUtils.checkFloatWindowPermission(PublishActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //显示码率类型
                if (mRLBitrate != null) {
                    mRLBitrate.setVisibility(View.VISIBLE);
                }
                //录屏直播
                mRecordType = Constants.RECORD_TYPE_SCREEN;
                break;
            default:
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void doLocationSuccess(String location) {
        mTvLBS.setText(location);
    }

    @Override
    public void doLocationFailed() {
        mTvLBS.setText("定位失败");
        mBtnLBS.setChecked(false, false);

    }

    @Override
    public void doUploadSuccess(String url) {
        Glide.with(this).load(url).into(mImgCover);
    }

    @Override
    public void doUploadFailed(String url) {
        showMsg("直播封面上传失败");
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showMsg(String msg) {
        Log.e(TAG, msg);
        ToastUtils.showShort(this, msg);
    }

    @Override
    public void showMsg(int msgId) {
        ToastUtils.showShort(this, getString(msgId));
    }

    @Override
    public Context getContext() {
        return this;
    }
}
