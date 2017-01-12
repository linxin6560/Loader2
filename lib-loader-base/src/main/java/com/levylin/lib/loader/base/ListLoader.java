package com.levylin.lib.loader.base;

import com.levylin.lib.loader.base.helper.intf.IListViewHelper;
import com.levylin.lib.loader.base.helper.listener.OnLoadMoreListener;
import com.levylin.lib.loader.base.listener.OnLoadListener;
import com.levylin.lib.loader.base.model.IListModel;

/**
 * 分页页面数据加载
 * Created by LinXin on 2016/6/20 10:35.
 */
public class ListLoader<INFO, ITEM> extends DataLoader<INFO> implements OnLoadMoreListener {

    private IListModel<INFO, ITEM> model;
    private IListViewHelper mListViewHelper;

    public ListLoader(ILoaderView view, IListModel<INFO, ITEM> listModel) {
        super(view, listModel);
        this.model = listModel;
    }

    public void setListViewHelper(IListViewHelper helper) {
        this.mListViewHelper = helper;
        this.mListViewHelper.setOnLoadMoreListener(this);
        this.mListViewHelper.setOnReLoadMoreListener(this);
    }

    @Override
    public void load() {
        cancel();
        model.load(new OnLoadListener<INFO>() {
            @Override
            public void onStart() {
                showLoading();
            }

            @Override
            public void onSuccess(INFO response) {
                showContent(response);
                model.setManualRefresh(false);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                showError(throwable);
                model.setManualRefresh(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        model.preRefresh();
        load();
    }


    @Override
    public void onLoadMore() {
        model.preLoadNext();
        load();
    }

    /**
     * 显示加载状态
     */
    private void showLoading() {
        if (isRefreshing())
            return;
        if (isLoadingMore())
            return;
        if (mLoadStateViewHelper == null)
            return;
        mLoadStateViewHelper.showLoading();
    }

    /**
     * 是否加载更多
     *
     * @return true:是,false:否
     */
    private boolean isLoadingMore() {
        return mListViewHelper != null && mListViewHelper.isLoadingMore();
    }

    /**
     * 显示内容
     */
    private void showContent(INFO responseModel) {
        if (isRefreshing() && mRefreshViewHelper != null) {
            mRefreshViewHelper.refreshComplete(true);
        }
        model.setData(isRefreshing(), responseModel);
        if (mListViewHelper != null) {
            mListViewHelper.notifyAdapter();
        }
        setLoadMoreState(responseModel);
        if (mLoadStateViewHelper != null) {
            if (model.isEmpty()) {
                mLoadStateViewHelper.showEmpty();
            } else {
                mLoadStateViewHelper.showContent();
            }
        }
        if (onLoadSuccessListener != null) {
            onLoadSuccessListener.onSuccess(isRefreshing(), responseModel);
        }
    }

    /**
     * 设置加载更多状态
     */
    private void setLoadMoreState(INFO response) {
        if (mListViewHelper == null)
            return;
        if (model.hasNext()) {
            mListViewHelper.showLoadMoreIdle();
        } else {
            mListViewHelper.showLoadMoreNoMore();
        }
    }

    /**
     * 显示加载错误
     *
     * @param t 异常类型
     */
    private void showError(Throwable t) {
        if (isRefreshing() && mRefreshViewHelper != null) {
            mRefreshViewHelper.refreshComplete(false);
        } else if (isLoadingMore()) {
            mListViewHelper.showLoadMoreError();
        } else if (mLoadStateViewHelper != null) {
            mLoadStateViewHelper.showError(model.isEmpty(), t);
        }
        if (onLoadFailureListener != null) {
            onLoadFailureListener.onFailure(isRefreshing(), t);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListViewHelper = null;
    }
}