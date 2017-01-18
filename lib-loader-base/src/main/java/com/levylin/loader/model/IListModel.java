package com.levylin.loader.model;

import java.util.List;

/**
 * 列表数据
 * Created by LinXin on 2016/6/21 9:23.
 */
public interface IListModel<INFO, ITEM> extends IModel<INFO> {

    /**
     * 清除数据
     */
    void clear();

    /**
     * 加载下一页
     */
    void preLoadNext();

    /**
     * 是否有下一页
     *
     * @return
     */
    boolean hasNext();

    /**
     * 数据转换
     *
     * @param response
     * @return
     */
    List<ITEM> map(INFO response);
}