package me.obrecht.developerslife;

import java.util.List;

import me.obrecht.developerslife.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DevelopersLifeService {
    String BASE_URL = "https://developerslife.ru";

    @GET("random?json=true")
    Call<Post> getRandomPost();

    @GET("{category}/{page}?json=true")
    Call<List<Post>> getCategoryPostList(@Path("category") String category, @Path("page") int page);
}
