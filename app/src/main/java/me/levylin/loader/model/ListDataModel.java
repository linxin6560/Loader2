package me.levylin.loader.model;

import com.levylin.lib.loader.ListModel;

import java.util.ArrayList;
import java.util.List;

import me.levylin.loader.api.ApiManager;
import me.levylin.loader.api.MainApi;
import retrofit2.Call;

/**
 * Created by LinXin on 2016/12/28 16:56.
 */
public class ListDataModel extends ListModel<String, String> {

    public ListDataModel(List<String> list) {
        super(list);
    }

    @Override
    public boolean ensureHasNext(String response, List<String> mapList) {
        return page < 10;
    }

    @Override
    public List<String> map(String response) {
        List<String> list = new ArrayList<>();
        int size = mList.size();
        for (int i = 0; i < 20; i++) {
            list.add((i + size) + ":XXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        return list;
    }

    @Override
    protected Call<String> getModelCall() {
        MainApi api = ApiManager.getInstance().getMainApi();
        return api.getTestCall();
    }
}
