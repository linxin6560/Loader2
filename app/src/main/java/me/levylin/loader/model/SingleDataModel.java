package me.levylin.loader.model;

import me.levylin.loader.api.ApiManager;
import me.levylin.loader.api.MainApi;
import me.levylin.loader.model.net.NetModel;
import retrofit2.Call;

/**
 * Created by LinXin on 2016/12/28 16:08.
 */
public class SingleDataModel extends NetModel<String> {

    @Override
    protected Call<String> makeCall() {
        MainApi api = ApiManager.getInstance().getMainApi();
        return api.getTestCall();
    }
}
