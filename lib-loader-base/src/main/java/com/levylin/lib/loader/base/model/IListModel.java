package com.levylin.lib.loader.base.model;

import java.util.List;

/**
 * 列表数据
 * Created by LinXin on 2016/6/21 9:23.
 */
public interface IListModel<INFO, ITEM> extends IModel<INFO> {

    void clear();

    void preLoadNext();

    boolean hasNext();

    /**
     * 确认是否有下一页
     *
     * @param response
     * @param mapList
     * @return
     */
    boolean ensureHasNext(INFO response, List<ITEM> mapList);

    /**
     * 数据转换
     *
     * @param response
     * @return
     */
    List<ITEM> map(INFO response);
}