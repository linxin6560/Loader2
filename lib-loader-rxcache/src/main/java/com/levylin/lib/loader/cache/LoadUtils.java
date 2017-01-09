package com.levylin.lib.loader.cache;

import com.levylin.lib.loader.base.listener.OnLoadListener;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Call;

/**
 * 数据加载器
 * Created by LinXin on 2016/4/6 15:09.
 */
public class LoadUtils {

    /**
     * 通过 call直接请求
     *
     * @param call     请求call
     * @param listener 请求监听
     * @param <T>      数据模型类型
     * @return
     */
    public static <T> Disposable load(Call<T> call, CacheStrategy<T> config, final OnLoadListener<T> listener) {
        ResponseSubscriber<T> subscriber = new ResponseSubscriber<>(listener);
        Flowable.create(new RequestOnSubscribe<>(call, config), BackpressureStrategy.BUFFER)
                .materialize()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .<T>dematerialize()
                .subscribe(subscriber);
        return subscriber;
    }

    /**
     * 通过 call直接同步请求
     *
     * @param call     请求call
     * @param listener 请求监听
     * @param <T>      数据模型类型
     * @return
     */
    public static <T> ResourceSubscriber loadSync(Call<T> call, CacheStrategy<T> config, OnLoadListener<T> listener) {
        ResponseSubscriber<T> subscriber = new ResponseSubscriber<>(listener);
        Flowable.create(new RequestOnSubscribe<>(call, config), BackpressureStrategy.BUFFER)
                .subscribe(subscriber);
        return subscriber;
    }
}
