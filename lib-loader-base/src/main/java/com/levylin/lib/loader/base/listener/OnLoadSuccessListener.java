package com.levylin.lib.loader.base.listener;

/**
 * 加载成功监听
 * Created by LinXin on 2016/6/20 9:55.
 */
public interface OnLoadSuccessListener<T> {

    /**
     * 加载成功
     *
     * @param response 响应结果
     */
    void onSuccess(boolean isRefreshing, T response);
}
