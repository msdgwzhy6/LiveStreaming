package com.dali.admin.livestreaming.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dali.admin.livestreaming.R;
import com.dali.admin.livestreaming.base.BaseViewHolder;
import com.dali.admin.livestreaming.base.LiveBaseAdapter;
import com.dali.admin.livestreaming.model.LiveInfo;
import com.dali.admin.livestreaming.utils.OtherUtils;

import java.util.List;

/**
 * 直播列表Adapter
 * Created by dali on 2017/5/7.
 */

public class LiveListAdapter extends LiveBaseAdapter<LiveInfo> {


    public LiveListAdapter(List<LiveInfo> datas, Context context) {
        super(datas, context, R.layout.list_live_item);
    }

    @Override
    public void bindData(BaseViewHolder holder, final LiveInfo liveInfo) {

        //直播标题
        holder.setText(R.id.live_title,liveInfo.getTitle())
                //主播昵称
                .setText(R.id.host_name, TextUtils.isEmpty(liveInfo.getUserInfo().getNickname())? OtherUtils.getLimitString(liveInfo.getUserInfo().getUserId(),10):OtherUtils.getLimitString(liveInfo.getUserInfo().getNickname(),10))
                //直播观看人数
                .setText(R.id.live_members,liveInfo.getViewCount()+"")
                //直播点赞数
                .setText(R.id.praises,liveInfo.getLikeCount()+"")
                //主播地址
                .setText(R.id.live_lbs,TextUtils.isEmpty(liveInfo.getUserInfo().getLocation())?"不显示地理位置":OtherUtils.getLimitString(liveInfo.getUserInfo().getLocation(),9))
                //直播封面
                .setImagePath(R.id.cover, new BaseViewHolder.ImageLoder(liveInfo.getLiveCover()) {
                    @Override
                    public void loadImage(ImageView imageView, String path) {
                        Glide.with(mContext).load(path).placeholder(R.drawable.bg_dark).into(imageView);
                    }
                })
                //直播logo
                .setImageResource(R.id.live_logo,R.drawable.icon_live);
        //主播头像（圆角显示图片）
        OtherUtils.showPicWithUrl(mContext,(ImageView) holder.getView(R.id.avatar),liveInfo.getUserInfo().getHeadPic(),R.drawable.default_head);
    }

}
