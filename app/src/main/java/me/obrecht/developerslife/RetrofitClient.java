package me.obrecht.developerslife;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final DevelopersLifeService api;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(DevelopersLifeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(DevelopersLifeService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public DevelopersLifeService getApi() {
        return api;
    }
}
