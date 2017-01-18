package me.levylin.loader.model;


import com.levylin.loader.model.impl.ListModel;
import com.levylin.loader.model.impl.provider.IListProvider;

import java.util.ArrayList;
import java.util.List;

import me.levylin.loader.api.ApiManager;
import me.levylin.loader.api.MainApi;
import me.levylin.loader.model.provider.RetrofitListProvider;
import retrofit2.Call;

/**
 * Created by LinXin on 2016/12/28 16:56.
 */
public class ListDataModel extends ListModel<String, String> {

    public ListDataModel(List<String> list) {
        super(list);
    }

    @Override
    protected IListProvider<String, String> makeProvider() {
        return new RetrofitListProvider<String, String>() {

            @Override
            public boolean ensureHasNext(String response, List<String> mapList) {
                System.out.println("ensureHasNext=" + mapList);
                return page < 10;
            }


            @Override
            protected Call<String> makeCall() {
                MainApi api = ApiManager.getInstance().getMainApi();
                return api.getTestCall();
            }
        };
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
}
