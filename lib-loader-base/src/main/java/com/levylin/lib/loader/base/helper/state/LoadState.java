package com.levylin.lib.loader.base.helper.state;

/**
 * 加载状态
 * 因很多地方用到，故改为枚举
 */
public enum LoadState {
    /**
     * 显示内容
     */
    CONTENT,
    /**
     * 显示加载中
     */
    LOADING,
    /**
     * 显示空界面
     */
    EMPTY,
    /**
     * 显示错误
     */
    ERROR
}
