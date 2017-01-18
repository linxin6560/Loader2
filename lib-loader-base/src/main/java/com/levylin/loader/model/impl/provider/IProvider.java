package com.levylin.loader.model.impl.provider;

import com.levylin.loader.listener.OnLoadListener;

/**
 * 数据提供器，用于提供数据
 * Created by LinXin on 2017/1/18 10:16.
 */
public interface IProvider<T> {

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
