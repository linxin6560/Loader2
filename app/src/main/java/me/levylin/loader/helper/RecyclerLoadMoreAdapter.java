package me.levylin.loader.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 加载更多Adapter
 * 可以显示加载中，无数据，加载失败三个状态
 * Created by LinXin on 2016/4/27 9:42.
 * edit by LinXin on 2016/8/5 13:39 增加适配GridLayoutManager和StaggeredGridLayoutManager
 */
@SuppressWarnings("unchecked")
public class RecyclerLoadMoreAdapter extends RecyclerView.Adapter {

    //加载更多ViewType
    private static final int VIEW_TYPE_LOAD_MORE = 1000;
    private RecyclerView.Adapter delegate;
    private View footerView;
    private boolean showFooterView = false;

    public RecyclerLoadMoreAdapter(RecyclerView recyclerView, View footerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        this.delegate = recyclerView.getAdapter();
        RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }
        };
        this.delegate.registerAdapterDataObserver(dataObserver);
        this.footerView = footerView;
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup o = manager.getSpanSizeLookup();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (type == VIEW_TYPE_LOAD_MORE)
                        return manager.getSpanCount();
                    return o.getSpanSize(position);
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            int type = getItemViewType(holder.getLayoutPosition());
            if (type == VIEW_TYPE_LOAD_MORE) {
                p.setFullSpan(true);
            }
        }
    }

    /**
     * 设置是否显示底部
     *
     * @param showFooterView
     */
    public void setShowFooterView(boolean showFooterView) {
        this.showFooterView = showFooterView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            return new LoadMoreHolder(footerView);
        }
        return delegate.onCreateViewHolder(parent, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (showFooterView && position == getItemCount() - 1) {
            return;
        }
        delegate.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (showFooterView)
            return delegate.getItemCount() + 1;
        return delegate.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (showFooterView && position == getItemCount() - 1) {
            return VIEW_TYPE_LOAD_MORE;
        }
        return delegate.getItemViewType(position);
    }

    public RecyclerView.Adapter getDelegate() {
        return delegate;
    }

    /**
     * 加载更多holder
     */
    private static class LoadMoreHolder extends RecyclerView.ViewHolder {

        public LoadMoreHolder(View itemView) {
            super(itemView);
        }
    }
}
