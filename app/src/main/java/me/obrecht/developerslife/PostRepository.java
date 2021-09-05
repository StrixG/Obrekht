package me.obrecht.developerslife;

import me.obrecht.developerslife.model.Post;
import retrofit2.Call;

public class PostRepository {
    private final DevelopersLifeService apiService;

    public PostRepository() {
        apiService = RetrofitClient.getInstance().getApi();
    }

    public Call<Post> getRandomPost() {
        // TODO: сделать по нормальному
        Call<Post> call = apiService.getRandomPost();
        return call;
    }
}
