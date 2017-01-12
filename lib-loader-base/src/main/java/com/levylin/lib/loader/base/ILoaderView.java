package com.levylin.lib.loader.base;

/**
 * 网络请求视图，作为MVP中，有进行网络操作的View的基类
 * Created by LinXin on 2016/8/5 17:52.
 */
public interface ILoaderView {
    /**
     * 取消网络请求
     */
    void destroyLoader();

    /**
     * 设置Loader，在Activity销毁的时候，一起销毁，防止内存泄露
     */
    void setDataLoader(DataLoader loader);
}
