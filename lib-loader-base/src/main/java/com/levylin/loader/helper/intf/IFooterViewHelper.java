package com.levylin.loader.helper.intf;

import android.view.View;

import com.levylin.loader.helper.listener.OnReloadListener;

/**
 * 加载更多footer帮助类
 * Created by LinXin on 2016/4/27 18:57.
 */
public interface IFooterViewHelper {

    /**
     * 获取底部视图
     *
     * @return 底部视图
     */
    View getFooterView();

    /**
     * 设置加载更多时失败，重新加载监听
     *
     * @param listener
     */
    void setOnReloadListener(OnReloadListener listener);

    /**
     * 显示空闲状态
     */
    void showIdle();

    /**
     * 显示加载中状态
     */
    void showLoading();

    /**
     * 显示无更多状态
     */
    void showNoMore();

    /**
     * 显示错误状态
     */
    void showError();

    /**
     * 是否空闲状态
     *
     * @return true:是 false:否
     */
    boolean isIdle();

    /**
     * 是否是加载状态
     *
     * @return true:是,false:否
     */
    boolean isLoadingMore();

    /**
     * 没有更多时是否隐藏
     *
     * @return
     */
    boolean isGoneWhenNoMore();
}
