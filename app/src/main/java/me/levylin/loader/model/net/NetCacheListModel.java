package me.levylin.loader.model.net;

import com.levylin.lib.loader.cache.CacheStrategy;
import com.levylin.lib.loader.cache.LoadUtils;
import com.levylin.loader.listener.OnLoadListener;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 带缓存的列表数据加载
 * Created by LinXin on 2017/1/18 16:51.
 */
public abstract class NetCacheListModel<INFO, ITEM> extends NetListModel<INFO, ITEM> {

    private Disposable mDisposable;
    private CacheStrategy<INFO> cacheStrategy;

    public NetCacheListModel(List<ITEM> items) {
        super(items);
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

    protected abstract CacheStrategy<INFO> makeCacheStrategy();

    @Override
    public void load(OnLoadListener<INFO> listener) {
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
