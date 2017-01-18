package com.levylin.loader;

import com.levylin.loader.helper.intf.ILoadStateHelper;
import com.levylin.loader.helper.intf.IRefreshViewHelper;
import com.levylin.loader.helper.listener.OnRefreshListener;
import com.levylin.loader.helper.listener.OnReloadListener;
import com.levylin.loader.listener.OnLoadFailureListener;
import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.listener.OnLoadSuccessListener;
import com.levylin.loader.model.IModel;

/**
 * 页面内容加载器
 * Created by LinXin on 2016/6/14 10:22.
 */
public class DataLoader<T> implements OnRefreshListener, OnReloadListener {

    ILoadStateHelper mLoadStateViewHelper;
    IRefreshViewHelper mRefreshViewHelper;
    OnLoadSuccessListener<T> onLoadSuccessListener;
    OnLoadFailureListener onLoadFailureListener;
    boolean isSilenceRefresh;//是否手动刷新
    private IModel<T> model;
    private ILoaderView view;

    public DataLoader(ILoaderView view, IModel<T> model) {
        this.view = view;
        this.model = model;
        this.view.setDataLoader(this);
    }

    public void setLoadStateHelper(ILoadStateHelper helper) {
        this.mLoadStateViewHelper = helper;
        this.mLoadStateViewHelper.setReloadListener(this);
    }

    public void setRefreshViewHelper(IRefreshViewHelper helper) {
        this.mRefreshViewHelper = helper;
        mRefreshViewHelper.setOnRefreshListener(this);
    }

    public void setOnLoadSuccessListener(OnLoadSuccessListener<T> listener) {
        this.onLoadSuccessListener = listener;
    }

    public void setOnLoadFailureListener(OnLoadFailureListener listener) {
        this.onLoadFailureListener = listener;
    }


    /**
     * 是否手动刷新
     *
     * @return
     */
    public boolean isSilenceRefresh() {
        return isSilenceRefresh;
    }

    /**
     * 重置手动刷新为false
     */
    public void setSilenceRefresh(boolean isManualRefresh) {
        this.isSilenceRefresh = isManualRefresh;
    }

    public void load() {
        cancel();
        model.load(new OnLoadListener<T>() {
            @Override
            public void onStart() {
                showLoading();
            }

            @Override
            public void onSuccess(T response) {
                showContent(response);
                isSilenceRefresh = false;
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                showError(throwable);
                isSilenceRefresh = false;
            }
        });
    }

    /**
     * 静默刷新
     */
    public void silenceRefresh() {
        isSilenceRefresh = true;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        model.preRefresh();
        load();
    }

    @Override
    public void onReLoad() {
        model.preReLoad();
        load();
    }

    /**
     * 取消加载
     */
    public void cancel() {
        model.cancel();
    }

    /**
     * 显示加载状态
     */
    private void showLoading() {
        if (isRefreshing())
            return;
        if (mLoadStateViewHelper == null)
            return;
        mLoadStateViewHelper.showLoading();
    }

    /**
     * 是否在刷新中
     *
     * @return true:是，false:否
     */
    protected boolean isRefreshing() {
        return (mRefreshViewHelper != null && mRefreshViewHelper.isRefreshing()) || isSilenceRefresh;
    }

    /**
     * 显示内容
     */
    private void showContent(T response) {
        if (isRefreshing()) {
            if (mRefreshViewHelper != null) {
                mRefreshViewHelper.refreshComplete(true);
            }
        }
        model.setData(isRefreshing(), response);
        if (mLoadStateViewHelper != null) {
            if (model.isEmpty()) {
                mLoadStateViewHelper.showEmpty();
            } else {
                mLoadStateViewHelper.showContent();
            }
        }
        if (onLoadSuccessListener != null) {
            onLoadSuccessListener.onSuccess(isRefreshing(), response);
        }
    }

    /**
     * 显示错误
     */
    private void showError(Throwable t) {
        if (isRefreshing()) {
            if (mRefreshViewHelper != null) {
                mRefreshViewHelper.refreshComplete(false);
            }
        } else if (mLoadStateViewHelper != null) {
            //界面为空
            mLoadStateViewHelper.showError(model.isEmpty(), t);
        }
        if (onLoadFailureListener != null) {
            onLoadFailureListener.onFailure(isRefreshing(), t);
        }
    }

    public void onDestroy() {
        cancel();
        view = null;
        mLoadStateViewHelper = null;
        mRefreshViewHelper = null;
    }
}
