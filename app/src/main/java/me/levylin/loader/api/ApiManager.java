package me.levylin.loader.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by LinXin on 2016/12/28 16:03.
 */
public class ApiManager {

    private static final String BASE_URL = "https://www.baidu.com/";

    private static ApiManager instance;
    private MainApi mainApi;

    private ApiManager() {
        mainApi = create(BASE_URL, MainApi.class);
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            synchronized (ApiManager.class) {
                if (instance == null) {
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }

    public MainApi getMainApi() {
        return mainApi;
    }

    public static <T> T create(final String baseUrl, Class<T> clazz) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
