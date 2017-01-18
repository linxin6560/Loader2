package com.levylin.loader.model.impl;

import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.model.IModel;
import com.levylin.loader.model.impl.provider.IProvider;

/**
 * 通用Model实现类
 * Created by LinXin on 2017/1/18 10:12.
 */
public abstract class Model<T> implements IModel<T> {

    private T t;
    private boolean isManualRefresh;
    private IProvider<T> request;

    public Model() {
        request = makeProvider();
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

    protected abstract IProvider<T> makeProvider();
}
