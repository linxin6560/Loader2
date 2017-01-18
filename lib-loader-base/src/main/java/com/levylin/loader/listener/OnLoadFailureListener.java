package com.levylin.loader.listener;

/**
 * 加载失败监听
 * Created by LinXin on 2016/6/20 9:55.
 */
public interface OnLoadFailureListener {

    /**
     * 加载失败
     *
     * @param isRefreshing 是否刷新
     * @param e            错误异常
     */
    void onFailure(boolean isRefreshing, Throwable e);
}
