package me.levylin.loader.helper;

import android.support.v4.widget.SwipeRefreshLayout;

import com.levylin.loader.helper.intf.IRefreshViewHelper;
import com.levylin.loader.helper.listener.OnRefreshListener;

/**
 * 刷新帮助类
 * Created by LinXin on 2016/12/28 15:56.
 */
public class RefreshHelper implements IRefreshViewHelper {

    private SwipeRefreshLayout mRefreshLayout;

    public RefreshHelper(SwipeRefreshLayout layout) {
        this.mRefreshLayout = layout;
    }

    @Override
    public void refreshComplete(boolean isSuccess) {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefresh();
            }
        });
    }

    @Override
    public boolean isRefreshing() {
        return mRefreshLayout.isRefreshing();
    }
}
