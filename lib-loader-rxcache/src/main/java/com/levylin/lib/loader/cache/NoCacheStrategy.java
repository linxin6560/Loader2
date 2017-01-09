package com.levylin.lib.loader.cache;

/**
 * 无缓存的策略
 */
public class NoCacheStrategy<T> extends CacheStrategy<T> {

    public NoCacheStrategy() {
        super(null, CacheType.NO_CACHE, 0);
    }

    @Override
    public boolean isTimeOut() {
        return false;
    }

    @Override
    public T readCache() {
        return null;
    }

    @Override
    public void saveCache(Object o) {

    }
}