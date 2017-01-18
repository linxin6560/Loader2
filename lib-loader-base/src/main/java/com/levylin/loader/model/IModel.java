package com.levylin.loader.model;

import com.levylin.loader.listener.OnLoadListener;

/**
 * 数据模型
 * T:解析数据类型
 * Created by LinXin on 2016/6/21 10:21.
 */
public interface IModel<T> {

    /**
     * 界面数据是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 设置数据
     *
     * @param response
     */
    void setData(boolean isRefreshing, T response);

    /**
     * 是否手动刷新
     *
     * @return
     */
    boolean isManualRefresh();

    /**
     * 重置手动刷新为false
     */
    void setManualRefresh(boolean isManualRefresh);

    /**
     * 刷新前要做的操作,一般用于改变缓存类型
     */
    void preRefresh();

    /**
     * 重新加载前要做的操作,一般用于改变缓存类型
     */
    void preReLoad();

    /**
     * 加载数据
     *
     * @param listener 加载监听
     */
    void load(OnLoadListener<T> listener);

    /**
     * 取消加载
     */
    void cancel();
}
