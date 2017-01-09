package me.levylin.loader.model;

import android.text.TextUtils;

import com.levylin.lib.loader.Model;

import me.levylin.loader.api.ApiManager;
import me.levylin.loader.api.MainApi;
import retrofit2.Call;

/**
 * Created by LinXin on 2016/12/28 16:08.
 */
public class SingleDataModel extends Model<String> {

    private String response;

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(response);
    }

    @Override
    public void setData(boolean isRefreshing, String response) {
        this.response = response;
    }

    @Override
    protected Call<String> getModelCall() {
        MainApi api = ApiManager.getInstance().getMainApi();
        return api.getTestCall();
    }
}
