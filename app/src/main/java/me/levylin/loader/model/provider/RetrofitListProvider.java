package me.levylin.loader.model.provider;

import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.model.impl.provider.IListProvider;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LinXin on 2017/1/18 10:41.
 */
public abstract class RetrofitListProvider<INFO, ITEM> implements IListProvider<INFO, ITEM> {

    private static final int PAGE_SIZE = 10;
    private Call<INFO> call;
    protected int page;

    @Override
    public void preRefresh() {
        page = 0;
    }

    @Override
    public void preReLoad() {

    }

    @Override
    public void preLoadNext() {
        page++;
    }

    @Override
    public boolean ensureHasNext(INFO response, List<ITEM> mapList) {
        return mapList != null && mapList.size() == PAGE_SIZE;
    }

    @Override
    public void load(final OnLoadListener<INFO> listener) {
        System.out.println("RetrofitListRequest....load");
        listener.onStart();
        if (call == null) {
            call = makeCall();
        }
        call.enqueue(new Callback<INFO>() {
            @Override
            public void onResponse(Call<INFO> call, Response<INFO> response) {
                System.out.println("response=" + response.body());
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<INFO> call, Throwable t) {
                listener.onError(t);
            }
        });
    }

    @Override
    public void cancel() {
        if (call == null)
            return;
        call.cancel();
        call = null;
    }

    protected abstract Call<INFO> makeCall();
}
