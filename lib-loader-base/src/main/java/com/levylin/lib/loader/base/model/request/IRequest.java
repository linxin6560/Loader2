package com.levylin.lib.loader.base.model.request;

import com.levylin.lib.loader.base.listener.OnLoadListener;

/**
 * 可取消操作的请求
 * Created by LinXin on 2017/1/18 10:16.
 */
public interface IRequest<T> {

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
