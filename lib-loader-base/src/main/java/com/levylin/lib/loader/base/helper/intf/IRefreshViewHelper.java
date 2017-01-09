package com.levylin.lib.loader.base.helper.intf;

import com.levylin.lib.loader.base.helper.listener.OnRefreshListener;

/**
 * 刷新控件帮助类
 * Created by LinXin on 2016/5/3 16:28.
 */
public interface IRefreshViewHelper {

    /**
     * 刷新完成
     *
     * @param isSuccess 是否加载成功
     */
    void refreshComplete(boolean isSuccess);

    /**
     * 设置刷新监听
     *
     * @param listener 刷新监听
     */
    void setOnRefreshListener(OnRefreshListener listener);

    /**
     * 是否在刷新中
     *
     * @return true:刷新，false:不刷新
     */
    boolean isRefreshing();
}
