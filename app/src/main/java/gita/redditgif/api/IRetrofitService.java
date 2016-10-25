package gita.redditgif.api;


import gita.redditgif.api.bean.response.GifResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by alex on 7/13/16.
 */
public interface IRetrofitService {
    String BASE = "r/";

    @GET(BASE + "gif/.json")
    Call<GifResponse> getGifJson();

}
