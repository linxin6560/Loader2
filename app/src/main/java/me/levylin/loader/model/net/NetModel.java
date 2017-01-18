package me.levylin.loader.model.net;

import com.levylin.loader.listener.OnLoadListener;
import com.levylin.loader.model.impl.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LinXin on 2017/1/18 10:41.
 */
public abstract class NetModel<T> extends Model<T> {

    private Call<T> call;

    @Override
    public void preRefresh() {

    }

    @Override
    public void preReLoad() {

    }

    @Override
    public void load(final OnLoadListener<T> listener) {
        System.out.println("RetrofitRequest....load");
        listener.onStart();
        if (call == null) {
            call = makeCall();
        }
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                System.out.println("response=" + response.body());
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
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

    protected abstract Call<T> makeCall();
}
