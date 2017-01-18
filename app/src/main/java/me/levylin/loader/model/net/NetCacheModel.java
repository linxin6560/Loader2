package me.levylin.loader.model.net;

import com.levylin.lib.loader.cache.CacheStrategy;
import com.levylin.lib.loader.cache.LoadUtils;
import com.levylin.loader.listener.OnLoadListener;

import io.reactivex.disposables.Disposable;

/**
 * 带缓存的数据加载
 * Created by LinXin on 2017/1/18 16:45.
 */
public abstract class NetCacheModel<T> extends NetModel<T> {

    private Disposable mDisposable;
    private CacheStrategy<T> cacheStrategy;

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

    protected abstract CacheStrategy<T> makeCacheStrategy();

    @Override
    public void load(OnLoadListener<T> listener) {
        if (cacheStrategy == null) {
            cacheStrategy = makeCacheStrategy();
        }
        mDisposable = LoadUtils.load(makeCall(), cacheStrategy, listener);
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
}
