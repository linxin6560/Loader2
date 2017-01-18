package com.levylin.loader.helper.state;

/**
 * 加载更多状态
 * Created by LinXin on 2016/7/19 14:14.
 */
public enum LoadMoreState {

    /**
     * 加载中
     */
    LOADING,
    /**
     * 加载更多状态空闲，可继续加载下一页
     */
    IDLE,
    /**
     * 加载更多为空
     */
    NO_MORE,
    /**
     * 加载更多失败
     */
    ERROR
}
