package me.levylin.loader.helper;

import android.support.v7.widget.RecyclerView;

import com.levylin.lib.loader.base.helper.intf.IFooterViewHelper;


/**
 * 列表视图帮助类
 * Created by LevyLin on 2016/1/14. 13:56
 */
public class RecyclerViewHelper extends BaseRecyclerViewHelper {

    private RecyclerLoadMoreAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public RecyclerViewHelper(RecyclerView recyclerView) {
        this(recyclerView, new FooterViewHelper(recyclerView));
    }

    public RecyclerViewHelper(RecyclerView recyclerView, IFooterViewHelper footerViewHelper) {
        super(recyclerView, footerViewHelper);
        this.mRecyclerView = recyclerView;
        initFooterViewHelper(footerViewHelper);
    }


    /**
     * 初始化底部视图
     */
    private void initFooterViewHelper(IFooterViewHelper mFooterViewHelper) {
        this.mFooterViewHelper = mFooterViewHelper;
        initAdapter(mRecyclerView);
        showLoadMoreIdle();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter(RecyclerView recyclerView) {
        mAdapter = new RecyclerLoadMoreAdapter(recyclerView, mFooterViewHelper.getFooterView());
        mAdapter.setShowFooterView(false);
        recyclerView.setAdapter(mAdapter);
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
