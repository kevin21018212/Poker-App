package test.connect.myapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFactory {

    static Retrofit apiClientSeed = null;

    static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    //postman url?
                    //remote server name: http://coms-309-064.class.las.iastate.edu:8080
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }


    public static PostApi GetPostApi(){
        return GetApiClientSeed().create(PostApi.class);
    }

    public static TriviaApi GetTrivaApi(){
        return GetApiClientSeed().create(TriviaApi.class);
    }
}
