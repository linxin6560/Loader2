package me.levylin.loader.model;

import com.levylin.lib.loader.base.model.impl.Model;
import com.levylin.lib.loader.base.model.request.IRequest;

import me.levylin.loader.api.ApiManager;
import me.levylin.loader.api.MainApi;
import retrofit2.Call;

/**
 * Created by LinXin on 2016/12/28 16:08.
 */
public class SingleDataModel2 extends Model<String> {

    @Override
    protected IRequest<String> makeRequest() {
        return new RetrofitRequest<String>() {

            @Override
            protected Call<String> makeCall() {
                MainApi api = ApiManager.getInstance().getMainApi();
                return api.getTestCall();
            }
        };
    }
}
