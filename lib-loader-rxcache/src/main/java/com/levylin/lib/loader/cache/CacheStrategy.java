package com.levylin.lib.loader.cache;

import okhttp3.Request;

/**
 * 缓存策略
 * Created by LinXin on 2016/7/25 16:18.
 * edit by LinXin on 2016/8/11 9:30 去掉设置缓存及缓存时间的操作，改为只在构造函数中控制，目的是防止类型被改变，可通过设置加载状态判断是否加载与保存缓存
 */
public abstract class CacheStrategy<T> {
    private static final int STATUS_DEFAULT = -1;//默认状态，该状态下，通过cache type自行判断是否需要读取保和存缓存
    private static final int STATUS_TRUE = 1;//生效状态，不以cache type判断存取，改为以该状态为主
    private static final int STATUS_FALSE = 0;//不生效状态，不以cache type判断存取，改为以改状态为主
    private int readCacheStatus = STATUS_DEFAULT, saveCacheStatus = STATUS_DEFAULT;
    //缓存类型
    private CacheType cacheType = CacheType.READ_CACHE_ONLY_NOT_EXPIRES;
    protected int cacheTimeOut;
    protected Request request;

    public CacheStrategy(Request request, CacheType cacheType, int cacheTimeOut) {
        this.request = request;
        this.cacheType = cacheType;
        this.cacheTimeOut = cacheTimeOut;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    /**
     * 设置是否需要读取缓存
     *
     * @param isReadCache 是否需要读取缓存
     */
    public void setIsReadCache(boolean isReadCache) {
        if (isReadCache) {
            readCacheStatus = STATUS_TRUE;
        } else {
            readCacheStatus = STATUS_FALSE;
        }
    }

    /**
     * 是否需要读取缓存
     *
     * @return 是否需要读取缓存
     */
    public boolean isReadCache() {
        if (readCacheStatus == STATUS_DEFAULT)
            return !cacheType.equals(CacheType.NO_CACHE);
        return readCacheStatus == STATUS_TRUE;
    }

    /**
     * 设置是否需要保存缓存
     *
     * @param isSaveCache 是否需要保存缓存
     */
    public void setIsSaveCache(boolean isSaveCache) {
        if (isSaveCache) {
            saveCacheStatus = STATUS_TRUE;
        } else {
            saveCacheStatus = STATUS_FALSE;
        }
    }

    /**
     * 是否需要保存缓存
     *
     * @return 是否需要保存缓存
     */
    public boolean isSaveCache() {
        if (saveCacheStatus == STATUS_DEFAULT)
            return !cacheType.equals(CacheType.NO_CACHE);
        return saveCacheStatus == STATUS_TRUE;
    }

    /**
     * 重置状态
     */
    public void reset() {
        readCacheStatus = STATUS_DEFAULT;
        saveCacheStatus = STATUS_DEFAULT;
    }

    /**
     * 缓存是否过期
     *
     * @return
     */
    public abstract boolean isTimeOut();

    /**
     * 读取缓存
     *
     * @return
     */
    public abstract T readCache();

    /**
     * 保存缓存
     *
     * @param t
     */
    public abstract void saveCache(T t);

    /**
     * 缓存的枚举类型
     */
    public enum CacheType {
        //不读缓存
        NO_CACHE,
        //优先读缓存，判断缓存是否过期，如果过期则去服务器上读数据
        READ_CACHE_ONLY_NOT_EXPIRES,
        //先读缓存，然后把数据返回给UI层，并且判断缓存是否过期，如果过期，则去服务器上读数据并返回给UI层
        READ_CACHE_UPDATE_UI_THEN_NET_WHEN_EXPIRES,
        //先读缓存，然后把数据返回给UI层，然后不管缓存是否过期，都去服务器上读数据并返回给UI层
        READ_CACHE_UPDATE_UI_THEN_NET
    }
}