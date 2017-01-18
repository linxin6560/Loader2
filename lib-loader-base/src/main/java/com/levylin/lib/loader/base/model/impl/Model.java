package com.levylin.lib.loader.base.model.impl;

import com.levylin.lib.loader.base.listener.OnLoadListener;
import com.levylin.lib.loader.base.model.IModel;
import com.levylin.lib.loader.base.model.request.IRequest;

/**
 * 通用Model实现类
 * Created by LinXin on 2017/1/18 10:12.
 */
public abstract class Model<T> implements IModel<T> {

    private T t;
    private boolean isManualRefresh;
    private IRequest<T> request;

    public Model() {
        request = makeRequest();
    }

    @Override
    public boolean isEmpty() {
        return t == null;
    }

    @Override
    public void setData(boolean isRefreshing, T response) {
        this.t = response;
    }

    @Override
    public boolean isManualRefresh() {
        return isManualRefresh;
    }

    @Override
    public void setManualRefresh(boolean isManualRefresh) {
        this.isManualRefresh = isManualRefresh;
    }

    @Override
    public void preRefresh() {
        request.preRefresh();
    }

    @Override
    public void preReLoad() {
        request.preReLoad();
    }

    @Override
    public void load(OnLoadListener<T> listener) {
        request.load(listener);
    }

    @Override
    public void cancel() {
        request.cancel();
    }

    protected abstract IRequest<T> makeRequest();
}
