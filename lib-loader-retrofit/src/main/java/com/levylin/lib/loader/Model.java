package com.levylin.lib.loader;

import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.model.IModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 数据模型
 * T:解析数据类型
 * Created by LinXin on 2016/6/21 10:21.
 */
public abstract class Model<T> implements IModel<T> {

    private Call<T> mCall;
    protected ICacheHelper<T> cacheHelper;

    /**
     * 刷新前要做的操作,一般用于改变缓存类型
     */
    @Override
    public void preRefresh() {
        if (cacheHelper != null) {
            cacheHelper.preRefresh();
        }
    }

    /**
     * 重新加载前要做的操作,一般用于改变缓存类型
     */
    @Override
    public void preReLoad() {
        if (cacheHelper != null) {
            cacheHelper.preReLoad();
        }
    }

    /**
     * 加载数据
     *
     * @param listener 加载监听
     */
    @Override
    public final void load(final OnLoadListener<T> listener) {
        if (cacheHelper != null) {
            cacheHelper.load(getModelCall(), listener);
        } else {
            listener.onStart();
            mCall = getModelCall();
            mCall.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    listener.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    listener.onError(t);
                }
            });
        }
    }

    /**
     * 取消加载
     */
    @Override
    public void cancel() {
        if (cacheHelper != null) {
            cacheHelper.cancel();
        } else if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * 获取数据模型请求
     *
     * @return
     */
    protected abstract Call<T> getModelCall();

    public void setCacheHelper(ICacheHelper<T> cacheHelper) {
        this.cacheHelper = cacheHelper;
    }
}
