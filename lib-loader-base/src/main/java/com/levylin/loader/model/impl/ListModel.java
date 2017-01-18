package com.levylin.loader.model.impl;

import com.levylin.loader.model.IListModel;

import java.util.List;

/**
 * 通用ListModel实现类
 * Created by LinXin on 2017/1/18 10:12.
 */
public abstract class ListModel<INFO, ITEM> extends Model<INFO> implements IListModel<INFO, ITEM> {
    protected static int FIRST_PAGE = 0;
    protected static int PAGE_SIZE = 10;
    protected List<ITEM> mList;
    protected int page;
    private boolean hasNext;

    public ListModel(List<ITEM> itemList) {
        this.mList = itemList;
    }

    @Override
    public boolean isEmpty() {
        return mList == null || mList.isEmpty();
    }

    protected boolean ensureHasNext(INFO response, List<ITEM> mapList) {
        return mapList != null && mapList.size() == PAGE_SIZE;
    }

    @Override
    public void clear() {
        mList.clear();
    }

    @Override
    public void preLoadNext() {
        page++;
    }

    @Override
    public void preRefresh() {
        page = FIRST_PAGE;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public void setData(boolean isRefreshing, INFO response) {
        if (isRefreshing) {
            clear();
        }
        List<ITEM> mapList = map(response);
        hasNext = ensureHasNext(response, mapList);
        if (mapList == null) {
            return;
        }
        mList.addAll(mapList);
    }
}