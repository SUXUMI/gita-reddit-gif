package gita.redditgif.api;

import gita.redditgif.api.bean.response.GifResponse;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by alex on 10/25/16.
 */

public class RedditApi {

    public static IRetrofitService retrofitService;

    public RedditApi() {
        if (retrofitService == null) retrofitService = new RetrofitClient().getService();
    }

    public void getGifJson(Callback<GifResponse> callback) {
        Call<GifResponse> call = retrofitService.getGifJson();
        call.enqueue(callback);
    }

}
