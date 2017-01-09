package com.levylin.lib.loader.base.listener;

/**
 * 加载监听
 * Created by LinXin on 2016/8/2 15:13.
 */
public interface OnLoadListener<T> {
    /**
     * 开始加载
     */
    void onStart();

    /**
     * 加载成功
     *
     * @param response 响应结果
     */
    void onSuccess(T response);

    /**
     * 加载失败
     *
     * @param throwable 异常
     */
    void onError(Throwable throwable);
}