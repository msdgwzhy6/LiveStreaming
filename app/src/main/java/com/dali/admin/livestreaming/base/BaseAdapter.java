package com.dali.admin.livestreaming.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by dali on 2017/3/10. 21:59
 */
public abstract class BaseAdapter<T> extends ArrayAdapter<T> {

    public BaseAdapter(Context context, List<T> dataList) {
        super(context,0,dataList);
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        T data = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(getViewLayoutId(),parent,false);
            viewHolder = new ViewHolder(getContext(),convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        initData(viewHolder,data,position);

        return convertView;

    }

    protected abstract void initData(ViewHolder viewHolder,T data,int position);

    protected abstract int getViewLayoutId();

    protected void initItemView(ViewHolder viewHolder,T data){}

    public static class ViewHolder{
        private SparseArray<View> mViews;
        private View mItemView;
        private int mLayoutId;

        public ViewHolder(Context context,View itemView){
            mItemView = itemView;
            mViews = new SparseArray<View>();
        }

        /**
         * 通过viewId获取控件
         * @param viewId
         * @param <T>
         * @return
         */
        public <T extends View> T getView(int viewId){
            View view = mViews.get(viewId);
            if (view == null){
                view = mItemView.findViewById(viewId);
                mViews.put(viewId,view);
            }

            return (T) view;
        }

        public View getConvertView(){
            return mItemView;
        }

        public int getLayoutId(){
            return mLayoutId;
        }


        /**
         * 点击事件
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener){

            View view = this.getView(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        /**
         * 触摸事件
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener){

            View view = this.getView(viewId);
            view.setOnTouchListener(listener);
            return this;
        }

        /**
         * 长按事件
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener){

            View view = this.getView(viewId);
            view.setOnLongClickListener(listener);
            return this;
        }
    }

}
