package me.obrecht.developerslife.ui.post;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

import me.obrecht.developerslife.PostRepository;
import me.obrecht.developerslife.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    private PostRepository repository;
    private List<Post> postList;
    private int currentPost = -1;
    MutableLiveData<Post> post;

    PostViewModel.LoadingState loadingState;

    public PostViewModel() {
        repository = new PostRepository();
        postList = new LinkedList<>();
    }

    public MutableLiveData<Post> getRandomPost() {
        if (post == null) {
            post = new MutableLiveData<>();
        }
        repository.getRandomPost().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                loadingState = LoadingState.SUCCESS;
                Post _post = response.body();
                postList.add(_post);
                currentPost += 1;
                post.setValue(_post);
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                loadingState = LoadingState.ERROR;
                post.setValue(null);
            }
        });
        return post;
    }

    void loadPreviousGif() {
        if (currentPost < 1) {
            return;
        }

        loadingState = PostViewModel.LoadingState.LOADING;

        Post _post = postList.get(currentPost - 1);

        currentPost -= 1;

        loadingState = PostViewModel.LoadingState.SUCCESS;
        post.setValue(_post);
    }

    void loadNextGif() {
        loadingState = PostViewModel.LoadingState.LOADING;

        if (currentPost < (postList.size() - 1)) {
            Post _post = postList.get(currentPost + 1);

            currentPost += 1;

            loadingState = PostViewModel.LoadingState.SUCCESS;
            post.setValue(_post);
            return;
        }

        getRandomPost();
    }

    public int getCurrentPostIndex() {
        return currentPost;
    }

    enum LoadingState {
        ERROR,
        LOADING,
        SUCCESS
    }
}