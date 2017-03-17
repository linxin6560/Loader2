package com.levylin.lib.loader.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.AbsListView;

import com.levylin.loader.helper.intf.IFooterViewHelper;
import com.levylin.loader.helper.intf.IListViewHelper;
import com.levylin.loader.helper.listener.OnLoadMoreListener;
import com.levylin.loader.helper.listener.OnReloadListener;

/**
 * RecyclerView帮助类基类
 * Created by LinXin on 2016/11/1 11:08.
 */
public class RecyclerViewHelper implements IListViewHelper {

    //底部加载更多帮助类
    IFooterViewHelper mFooterViewHelper;
    private RecyclerView mRecyclerView;
    //最后一项可见监听
    private OnLoadMoreListener mLoadMoreListener;
    private RecyclerLoadMoreAdapter mAdapter;
    //当前滑动状态
    private int mCurrentScrollState = -1;

    public RecyclerViewHelper(final RecyclerView recyclerView, IFooterViewHelper footerViewHelper) {
        mRecyclerView = recyclerView;
        mFooterViewHelper = footerViewHelper;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mCurrentScrollState = newState;
                ifNeedLoadMore(recyclerView);
            }
        });
        initFooterViewHelper(footerViewHelper);
    }

    /**
     * 初始化底部视图
     */
    private void initFooterViewHelper(IFooterViewHelper mFooterViewHelper) {
        this.mFooterViewHelper = mFooterViewHelper;
        mAdapter = new RecyclerLoadMoreAdapter(mRecyclerView, mFooterViewHelper.getFooterView());
        mAdapter.setShowFooterView(false);
        mRecyclerView.setAdapter(mAdapter);
        showLoadMoreIdle();
    }

    /**
     * Description 是否需要自动加载更多
     */
    private void ifNeedLoadMore(RecyclerView recyclerView) {
        boolean isNeedLoadMore;
        int lastVisiblePosition = getLastVisibleItem(recyclerView);
        final int count = recyclerView.getAdapter().getItemCount();// 列表中子项总数
        isNeedLoadMore = mCurrentScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastVisiblePosition == count - 1;
        if (isNeedLoadMore && mAdapter.getItemCount() != 0 && mFooterViewHelper.isIdle() && mLoadMoreListener != null) {//正在加载中，无更多，加载失败三种状态均不自动执行该动作
            showLoadMoreLoading();
            mLoadMoreListener.onLoadMore();
        }
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }


    @Override
    public void notifyAdapter(int oldCount, int newAddCount) {
        if (oldCount == 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyItemRangeInserted(oldCount, newAddCount);
        }
    }

    @Override
    public void setOnReLoadMoreListener(OnReloadListener listener) {
        mFooterViewHelper.setOnReloadListener(listener);
    }

    @Override
    public boolean isLoadingMore() {
        return mFooterViewHelper.isLoadingMore();
    }

    /**
     * 判断是否要显示没有更多
     */
    boolean isNeedShowNoMore() {
        RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
        int firstVisiblePosition = 0, lastVisiblePosition = 0;
        int count = lm.getItemCount();
        if (lm instanceof LinearLayoutManager) {
            firstVisiblePosition = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
            lastVisiblePosition = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
        } else if (lm instanceof StaggeredGridLayoutManager) {
            int[] positions = ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(null);
            firstVisiblePosition = getMin(positions);
            lastVisiblePosition = getMax(positions);
        }
        if (firstVisiblePosition > 0)//第一个可见项大于0.说明有翻屏，则显示END
            return true;
        if (lastVisiblePosition == -1)//最后一个可见项若是==-1，说明在第一页的时候，就没有下一页了，故不显示END
            return false;
        int visibleCount = lastVisiblePosition + 1;//可见的Item总数
        return count > visibleCount;
    }

    /**
     * 取出数组中的最大值
     *
     * @param arr
     * @return
     */
    private static int getMin(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    /**
     * 取出数组中的最大值
     *
     * @param arr
     * @return
     */
    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /**
     * 获取最后一项的位置
     */
    private static int getLastVisibleItem(RecyclerView recyclerView) {
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int lastVisiblePosition = 0;
        if (lm instanceof LinearLayoutManager) {
            lastVisiblePosition = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
        } else if (lm instanceof StaggeredGridLayoutManager) {
            int[] positions = ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(null);
            lastVisiblePosition = getMax(positions);
        }
        return lastVisiblePosition;
    }

    @Override
    public void showLoadMoreError() {
        if (mAdapter.getDelegate().getItemCount() > 1) {
            mAdapter.setShowFooterView(true);
            mFooterViewHelper.showError();
        }
    }

    @Override
    public void showLoadMoreIdle() {
        if (mAdapter.getDelegate().getItemCount() > 1) {
            mAdapter.setShowFooterView(true);
            mFooterViewHelper.showIdle();
        }
    }

    @Override
    public void showLoadMoreLoading() {
        mAdapter.setShowFooterView(true);
        mFooterViewHelper.showLoading();
    }

    @Override
    public void showLoadMoreNoMore() {
        mAdapter.setShowFooterView(!mFooterViewHelper.isGoneWhenNoMore() && isNeedShowNoMore());
        mFooterViewHelper.showNoMore();
    }
}
