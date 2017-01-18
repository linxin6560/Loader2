package me.levylin.loader.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.levylin.lib.loader.base.helper.intf.ILoadStateHelper;
import com.levylin.lib.loader.base.helper.listener.OnReloadListener;

import me.levylin.loader.R;

/**
 * 加载帮助类
 * Created by LinXin on 2016/12/28 15:37.
 */
public class LoadStateHelper implements ILoadStateHelper {

    private View mContentView;
    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;

    public LoadStateHelper(View contentView) {
        this.mContentView = contentView;
        ViewGroup parent = (ViewGroup) contentView.getParent();
        Context context = contentView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.lsh_empty, parent, true);
        inflater.inflate(R.layout.lsh_error, parent, true);
        inflater.inflate(R.layout.lsh_loading, parent, true);

        mEmptyView = parent.findViewById(R.id.lsh_empty_ll);
        mErrorView = parent.findViewById(R.id.lsh_error_ll);
        mLoadingView = parent.findViewById(R.id.lsh_loading_ll);
    }

    @Override
    public void showContent() {
        mContentView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showError(boolean isEmpty, Throwable t) {
        if (isEmpty) {
            mErrorView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            Toast.makeText(mContentView.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setReloadListener(final OnReloadListener listener) {
        mErrorView.setOnClickListener(v -> listener.onReLoad());
    }
}
