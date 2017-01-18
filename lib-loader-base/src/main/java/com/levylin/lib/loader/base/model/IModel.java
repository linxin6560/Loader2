package com.levylin.lib.loader.base.model;

import com.levylin.lib.loader.base.model.request.IRequest;

/**
 * 数据模型
 * T:解析数据类型
 * Created by LinXin on 2016/6/21 10:21.
 */
public interface IModel<T> extends IRequest<T> {

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
}
