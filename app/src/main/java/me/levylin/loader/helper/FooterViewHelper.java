package me.levylin.loader.helper;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.levylin.lib.loader.base.helper.intf.IFooterViewHelper;
import com.levylin.lib.loader.base.helper.listener.OnReloadListener;
import com.levylin.lib.loader.base.helper.state.LoadMoreState;

import me.levylin.loader.R;


/**
 * 底部加载更多帮助类
 * Created by LinXin on 2016/6/27 14:38.
 */
public class FooterViewHelper implements IFooterViewHelper, View.OnClickListener {

    private View mFooterView;
    private ImageView mProgressView;
    private AnimationDrawable mLoadingDrawable;
    private TextView mLoadMoreTv;
    private OnReloadListener mReloadListener;
    private LoadMoreState state = LoadMoreState.IDLE;

    private int noMoreTextResId;

    public FooterViewHelper(ViewGroup group) {
        Context context = group.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mFooterView = inflater.inflate(R.layout.item_load_more_recycleview, group, false);
        mFooterView.setOnClickListener(this);
        mProgressView = (ImageView) mFooterView.findViewById(R.id.item_load_more_recycleview_imv);
        mLoadingDrawable = (AnimationDrawable) mProgressView.getDrawable();
        mLoadMoreTv = (TextView) mFooterView.findViewById(R.id.item_load_more_recycleview_tv);
        setNoMoreTextResId(R.string.no_more);
    }

    public void setNoMoreTextResId(@StringRes int noMoreTextResId) {
        this.noMoreTextResId = noMoreTextResId;
    }

    @Override
    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public void setOnReloadListener(OnReloadListener listener) {
        mReloadListener = listener;
    }

    @Override
    public void showIdle() {
        this.state = LoadMoreState.IDLE;
        mProgressView.setVisibility(View.GONE);
        mLoadingDrawable.stop();
        mLoadMoreTv.setText("");
    }

    @Override
    public void showLoading() {
        this.state = LoadMoreState.LOADING;
        mProgressView.setVisibility(View.VISIBLE);
        mLoadingDrawable.start();
        mLoadMoreTv.setText(R.string.common_load_more);
    }

    @Override
    public void showNoMore() {
        this.state = LoadMoreState.NO_MORE;
        mProgressView.setVisibility(View.GONE);
        mLoadingDrawable.stop();
        if (noMoreTextResId != 0) {
            mLoadMoreTv.setText(noMoreTextResId);
        } else {
            mLoadMoreTv.setText("");
        }
    }

    @Override
    public void showError() {
        this.state = LoadMoreState.ERROR;
        mProgressView.setVisibility(View.GONE);
        mLoadingDrawable.stop();
        mLoadMoreTv.setText(R.string.common_load_more_error);
    }

    @Override
    public boolean isIdle() {
        return state == LoadMoreState.IDLE;
    }

    @Override
    public boolean isLoadingMore() {
        return state == LoadMoreState.LOADING;
    }

    @Override
    public boolean isGoneWhenNoMore() {
        return noMoreTextResId == 0;
    }

    @Override
    public void onClick(View v) {
        if (state == LoadMoreState.ERROR && mReloadListener != null) {
            showLoading();
            mReloadListener.onReLoad();
        }
    }
}
