package me.levylin.loader.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 接口
 * Created by LinXin on 2016/12/28 16:01.
 */
public interface MainApi {

    @GET("https://www.baidu.com/")
    Call<String> getTestCall();
}
