package com.levylin.loader;

import android.util.Log;

import com.levylin.loader.helper.intf.IListViewHelper;
import com.levylin.loader.helper.listener.OnLoadMoreListener;
import com.levylin.loader.helper.listener.OnReloadListener;
import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.model.IListModel;

/**
 * 分页页面数据加载
 * Created by LinXin on 2016/6/20 10:35.
 */
public class ListLoader<INFO, ITEM> extends DataLoader<INFO> {

    private boolean debug = false;
    private IListModel<INFO, ITEM> model;
    private IListViewHelper mListViewHelper;

    public ListLoader(IListModel<INFO, ITEM> listModel) {
        super(listModel);
        this.model = listModel;
    }

    public void setListViewHelper(IListViewHelper helper) {
        this.mListViewHelper = helper;
        this.mListViewHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                model.preLoadNext();
                load();
            }
        });
        this.mListViewHelper.setOnReLoadMoreListener(new OnReloadListener() {
            @Override
            public void onReLoad() {
                model.preReLoad();
                load();
            }
        });
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

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 显示加载状态
     */
    private void showLoading() {
        log("showLoading...");
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
        log("showContent:responseModel=" + responseModel);
        model.setData(isRefreshing(), responseModel);
        if (isRefreshing() && mRefreshViewHelper != null) {
            mRefreshViewHelper.refreshComplete(true);
        }
        if (mListViewHelper != null) {
            log("oldCount:" + model.getOldCount() + ",newAddCount:" + model.getNewAddCount());
            mListViewHelper.notifyAdapter(model.getOldCount(), model.getNewAddCount());
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
        log("showError:t=" + t);
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
    public void detachView() {
        super.detachView();
        mListViewHelper = null;
    }

    private void log(String content) {
        if (debug) {
            Log.i("ListLoader", content);
        }
    }
}