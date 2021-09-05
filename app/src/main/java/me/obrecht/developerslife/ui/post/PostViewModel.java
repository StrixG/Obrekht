package me.obrecht.developerslife.ui.post;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

import me.obrecht.developerslife.PostRepository;
import me.obrecht.developerslife.model.Post;

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
        repository.getRandomPost(post);
        return post;
    }

    void loadPreviousGif() {
        if (currentPost < 1) {
            return;
        }

        loadingState = PostViewModel.LoadingState.LOADING;

        Post _post = postList.get(currentPost - 1);
        post.setValue(_post);

        currentPost -= 1;

        loadingState = PostViewModel.LoadingState.SUCCESS;
    }

    void loadNextGif() {
        loadingState = PostViewModel.LoadingState.LOADING;

        if (currentPost < (postList.size() - 1)) {
            Post _post = postList.get(currentPost + 1);
            post.setValue(_post);

            currentPost += 1;

            loadingState = PostViewModel.LoadingState.SUCCESS;
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