package com.levylin.loader.helper.intf;

import com.levylin.loader.helper.listener.OnReloadListener;

/**
 * 加载状态视图代理
 * Created by LinXin on 2016/4/19 16:16.
 */
public interface ILoadStateHelper {

    /**
     * 显示内容视图
     */
    void showContent();

    /**
     * 显示加载中视图
     */
    void showLoading();

    /**
     * 显示空界面视图
     */
    void showEmpty();

    /**
     * 显示错误视图
     */
    void showError(boolean isEmpty, Throwable t);

    /**
     * 重新加载监听
     *
     * @param listener
     */
    void setReloadListener(OnReloadListener listener);
}
