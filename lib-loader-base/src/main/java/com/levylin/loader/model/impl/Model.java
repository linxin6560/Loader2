package com.levylin.loader.model.impl;

import com.levylin.loader.model.IModel;

/**
 * 通用Model实现类
 * Created by LinXin on 2017/1/18 10:12.
 */
public abstract class Model<T> implements IModel<T> {

    private T t;

    @Override
    public boolean isEmpty() {
        return t == null;
    }

    @Override
    public void setData(boolean isRefreshing, T response) {
        this.t = response;
    }

    @Override
    public void preRefresh() {
    }

    @Override
    public void preReLoad() {
    }
}
