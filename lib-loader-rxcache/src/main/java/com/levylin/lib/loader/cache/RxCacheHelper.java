package com.levylin.lib.loader.cache;

import com.levylin.lib.loader.ICacheHelper;
import com.levylin.loader.listener.OnLoadListener;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;

/**
 * 缓存帮助类
 * Created by LinXin on 2017/1/9 10:03.
 */
public abstract class RxCacheHelper<T> implements ICacheHelper<T> {

    private Disposable mDisposable;
    private CacheStrategy<T> cacheStrategy;

    public RxCacheHelper() {
        cacheStrategy = getCacheStrategy();
    }

    @Override
    public void preRefresh() {
        cacheStrategy.reset();//先重置状态，防止读取状态在别的地方已修改过
        cacheStrategy.setIsReadCache(false);
    }

    @Override
    public void preReLoad() {
        cacheStrategy.reset();//先重置状态，防止读取状态在别的地方已修改过
        cacheStrategy.setIsReadCache(false);
    }

    @Override
    public void preLoadNext() {
        cacheStrategy.setIsReadCache(false);
        cacheStrategy.setIsSaveCache(false);
    }

    @Override
    public void cancel() {
        if (mDisposable == null)
            return;
        if (mDisposable.isDisposed())
            return;
        mDisposable.dispose();
        mDisposable = null;
    }

    @Override
    public void load(Call<T> call, OnLoadListener<T> listener) {
        mDisposable = LoadUtils.load(call, cacheStrategy, listener);
    }

    /**
     * 获取缓存策略,默认不需要缓存
     *
     * @return
     */
    protected abstract CacheStrategy<T> getCacheStrategy();
}
