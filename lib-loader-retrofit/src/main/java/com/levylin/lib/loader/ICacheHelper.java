package com.levylin.lib.loader;

import com.levylin.lib.loader.base.listener.OnLoadListener;

import retrofit2.Call;

/**
 * 缓存帮助类
 * Created by LinXin on 2017/1/9 9:51.
 */
public interface ICacheHelper<T> {

    void preRefresh();

    void preReLoad();

    void preLoadNext();

    void cancel();

    void load(Call<T> call, OnLoadListener<T> listener);
}