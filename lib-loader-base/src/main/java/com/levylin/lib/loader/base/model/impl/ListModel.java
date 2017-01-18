package com.levylin.lib.loader.base.model.impl;

import com.levylin.lib.loader.base.model.IListModel;
import com.levylin.lib.loader.base.model.request.IListRequest;

import java.util.List;

/**
 * 通用ListModel实现类
 * Created by LinXin on 2017/1/18 10:12.
 */
public abstract class ListModel<INFO, ITEM> extends Model<INFO> implements IListModel<INFO, ITEM> {
    private List<ITEM> itemList;
    private IListRequest<INFO> request;
    private boolean hasNext;

    public ListModel(List<ITEM> itemList) {
        this.itemList = itemList;
        request = makeRequest();
    }

    @Override
    public void clear() {
        itemList.clear();
    }

    @Override
    public void preLoadNext() {
        request.preLoadNext();
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
        itemList.addAll(mapList);
    }

    @Override
    public boolean ensureHasNext(INFO response, List<ITEM> mapList) {
        return mapList != null && mapList.size() == getPageSize();
    }

    /**
     * 生成请求器
     *
     * @return
     */
    protected abstract IListRequest<INFO> makeRequest();

    /**
     * 获取每页数量
     *
     * @return
     */
    protected abstract int getPageSize();
}