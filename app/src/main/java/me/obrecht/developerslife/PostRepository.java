package me.obrecht.developerslife;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import me.obrecht.developerslife.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private final DevelopersLifeService apiService;

    public PostRepository() {
        apiService = RetrofitClient.getInstance().getApi();
    }

    public MutableLiveData<Post> getRandomPost(MutableLiveData<Post> post) {
        Call<Post> call = apiService.getRandomPost();
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                post.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                post.setValue(null);
            }
        });

        return post;
    }
}
