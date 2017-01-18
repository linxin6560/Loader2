package com.levylin.loader.model.impl.provider;

import java.util.List;

/**
 * 列表数据提供器
 * Created by LinXin on 2017/1/18 10:27.
 */
public interface IListProvider<INFO, ITEM> extends IProvider<INFO> {

    /**
     * 加载下一页
     */
    void preLoadNext();

    /**
     * 确认是否有下一页
     *
     * @param response
     * @param mapList
     * @return
     */
    boolean ensureHasNext(INFO response, List<ITEM> mapList);
}
