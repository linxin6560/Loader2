package com.levylin.loader.model.impl;

import com.levylin.loader.model.IListModel;
import com.levylin.loader.model.impl.provider.IListProvider;

import java.util.List;

/**
 * 通用ListModel实现类
 * Created by LinXin on 2017/1/18 10:12.
 */
public abstract class ListModel<INFO, ITEM> extends Model<INFO> implements IListModel<INFO, ITEM> {
    protected List<ITEM> mList;
    private IListProvider<INFO, ITEM> request;
    private boolean hasNext;

    public ListModel(List<ITEM> itemList) {
        this.mList = itemList;
        request = makeProvider();
    }

    @Override
    public boolean isEmpty() {
        return mList == null || mList.isEmpty();
    }

    @Override
    public void clear() {
        mList.clear();
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
        System.out.println("isRefreshing=" + isRefreshing);
        if (isRefreshing) {
            clear();
        }
        List<ITEM> mapList = map(response);
        hasNext = request.ensureHasNext(response, mapList);
        if (mapList == null) {
            return;
        }
        mList.addAll(mapList);
    }

    /**
     * 生成请求器
     *
     * @return
     */
    protected abstract IListProvider<INFO, ITEM> makeProvider();
}