package com.levylin.loader.helper.intf;

import com.levylin.loader.helper.listener.OnLoadMoreListener;
import com.levylin.loader.helper.listener.OnReloadListener;

/**
 * 列表界面帮助类
 * Created by LevyLin on 2016/1/20. 10:11
 */
public interface IListViewHelper {

    /**
     * 刷新adapter
     *
     * @param oldCount       旧总数
     * @param newAddDataSize 新增数据的大小
     */
    void notifyAdapter(int oldCount, int newAddDataSize);

    /**
     * 显示空闲状态
     */
    void showLoadMoreIdle();

    /**
     * 显示加载中状态
     */
    void showLoadMoreLoading();

    /**
     * 显示无更多状态
     */
    void showLoadMoreNoMore();

    /**
     * 显示错误状态
     */
    void showLoadMoreError();

    /**
     * 设置加载更多监听
     *
     * @param listener 加载更多监听
     */
    void setOnLoadMoreListener(OnLoadMoreListener listener);

    /**
     * 设置重新加载更多监听
     *
     * @param listener 重新加载更多监听
     */
    void setOnReLoadMoreListener(OnReloadListener listener);

    /**
     * 是否加载更多
     *
     * @return 是否加载更多
     */
    boolean isLoadingMore();
}
