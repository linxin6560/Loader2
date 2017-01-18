package me.levylin.loader.model;

import com.levylin.loader.model.impl.Model;
import com.levylin.loader.model.impl.provider.IProvider;

import me.levylin.loader.api.ApiManager;
import me.levylin.loader.api.MainApi;
import me.levylin.loader.model.provider.RetrofitProvider;
import retrofit2.Call;

/**
 * Created by LinXin on 2016/12/28 16:08.
 */
public class SingleDataModel extends Model<String> {

    @Override
    protected IProvider<String> makeProvider() {
        return new RetrofitProvider<String>() {

            @Override
            protected Call<String> makeCall() {
                MainApi api = ApiManager.getInstance().getMainApi();
                return api.getTestCall();
            }
        };
    }
}
