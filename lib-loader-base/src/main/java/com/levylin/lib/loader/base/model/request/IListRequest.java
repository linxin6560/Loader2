package com.levylin.lib.loader.base.model.request;

/**
 * 列表请求
 * Created by LinXin on 2017/1/18 10:27.
 */
public interface IListRequest<T> extends IRequest<T> {

    void preLoadNext();
}
