package me.obrecht.developerslife.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.developerslife.R;
import com.example.developerslife.databinding.FragmentPostBinding;

import me.obrecht.developerslife.GlideRestartGifListener;
import me.obrecht.developerslife.model.Post;

public class PostFragment extends Fragment {

    private PostViewModel viewModel;
    private FragmentPostBinding binding;

    private CircularProgressDrawable circularProgress;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);
        binding = FragmentPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.backButton.setOnClickListener(v -> viewModel.loadPreviousGif());
        binding.nextButton.setOnClickListener(v -> viewModel.loadNextGif());
        binding.tryAgainButton.setOnClickListener(v -> {
            setErrorVisible(false);
            viewModel.loadNextGif();
        });

        // Prepare WebView for Coub
        binding.coub.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    view.setAlpha(0f);
                    view.setVisibility(View.VISIBLE);
                    view.animate()
                            .alpha(1f)
                            .setDuration(500);
                }
            }
        });
        binding.coub.getSettings().setJavaScriptEnabled(true);
        binding.coub.getSettings().setLoadsImagesAutomatically(true);
        binding.coub.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        binding.coub.getSettings().setMediaPlaybackRequiresUserGesture(false);

        circularProgress = new CircularProgressDrawable(this.requireContext());
        circularProgress.setStrokeWidth(16f);
        circularProgress.setCenterRadius(64f);
        circularProgress.setColorSchemeColors(requireContext().getColor(R.color.blue));
        circularProgress.start();

        viewModel.getRandomPost().observe(getViewLifecycleOwner(), post -> {
            if (post == null) {
                setErrorVisible(true);
            } else {
                setErrorVisible(false);
                loadPost(post);
            }
            binding.backButton.setEnabled(viewModel.getCurrentPostIndex() > 0);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setErrorVisible(boolean visible) {
        if (visible) {
            binding.postCard.setVisibility(View.GONE);
            binding.networkErrorLayer.setVisibility(View.VISIBLE);
            binding.buttons.setVisibility(View.INVISIBLE);
        } else {
            binding.postCard.setAlpha(0f);
            binding.postCard.setVisibility(View.VISIBLE);
            binding.postCard.animate()
                    .setDuration(250)
                    .alpha(1f);
            binding.networkErrorLayer.setVisibility(View.GONE);
            binding.buttons.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param post The post object containing GIF URL or Coub ID and description.
     */
    private void loadPost(Post post) {
        if (post.getType() == Post.Type.GIF) {
            binding.gif.setVisibility(View.VISIBLE);
            binding.coub.setVisibility(View.GONE);
            binding.coub.loadUrl("about:blank");
            binding.coub.clearHistory();

            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(circularProgress);
            Glide.with(this)
                    .asGif()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .load(post.getGifURL())
                    .error(R.drawable.kesha)
                    .listener(new GlideRestartGifListener())
                    .into(binding.gif);

        } else if (post.getType() == Post.Type.COUB) {
            binding.gif.setVisibility(View.GONE);
            binding.coub.setVisibility(View.INVISIBLE);

            binding.coub.loadUrl("https://coub.com/embed/" + post.getEmbedId() + "?muted=true&autostart=false&originalSize=true&startWithHD=true");
        } else {
            return;
        }

        binding.description.setText(post.getDescription());
    }
}