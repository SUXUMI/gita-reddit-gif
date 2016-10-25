package gita.redditgif.ui.activities;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import gita.redditgif.R;
import gita.redditgif.api.RedditApi;
import gita.redditgif.api.bean.response.Child;
import gita.redditgif.api.bean.response.GifResponse;
import gita.redditgif.api.bean.response.Source;
import gita.redditgif.ui.adapters.GifRecyclerAdapter;
import gita.redditgif.ui.components.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView recyclerView;
    private GifRecyclerAdapter recyclerAdapter;
    private int screenHeight = 0;
    private int screenWidthFrame = 0;
    private View gifFrame;
    private ProgressBar frameProgress;
    private ImageView fullScreenGifIV;

    private Point getScreenDimens() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenHeight = getScreenDimens().y;
        screenWidthFrame = getScreenDimens().x;

        gifFrame = findViewById(R.id.gif_frame);
        frameProgress = (ProgressBar) findViewById(R.id.frame_progress);
        fullScreenGifIV = (ImageView) findViewById(R.id.full_screen_gif);

        recyclerView = (RecyclerView) findViewById(R.id.gif_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new GifRecyclerAdapter(this, this);
        recyclerView.setAdapter(recyclerAdapter);

        RedditApi redditApi = new RedditApi();
        redditApi.getGifJson(callback);

        recyclerView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                scrollImages();
            }
        });
    }

    public void scrollImages() {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();
        for (int i = first; i <= last; i++) {
            View v = manager.findViewByPosition(i);
            if (v == null)
                continue;
            ImageView image = (ImageView) v.findViewById(R.id.gif_image);
            adjustImage(v, image);
        }
    }

    public void adjustImage(View v, View image) {
        int[] coords = new int[2];
        v.getLocationOnScreen(coords);
        float distanceFromCenter = screenHeight * 0.5f - (coords[1]);
        float difference = image.getHeight() - (v.getHeight());
        float move = (distanceFromCenter / screenHeight) * difference;
        float y = -(difference * 0.5f) + move;
        if (y > 0) y = 0;
        image.setY(y);
    }

    Callback<GifResponse> callback = new Callback<GifResponse>() {
        @Override
        public void onResponse(Call<GifResponse> call, Response<GifResponse> response) {

            ArrayList<Child> children = new ArrayList<>();
            for (Child child : response.body().getData().getChildren()) {
                if (child.getData().getPreview() != null) {
                    if (child.getData().getPreview().getImages().get(0).getVariants().getGif() != null)
                        children.add(child);
                }
            }
            recyclerAdapter.setChildren(children);
            recyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<GifResponse> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Something went wrong :/", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onItemClick(final Source source) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_out);
        animation.setInterpolator(new OvershootInterpolator());
        gifFrame.setVisibility(View.VISIBLE);
        frameProgress.setVisibility(View.VISIBLE);
        gifFrame.startAnimation(animation);
        Ion.with(this).load(source.getUrl()).intoImageView(fullScreenGifIV).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {

                frameProgress.setVisibility(View.GONE);
                ViewGroup.LayoutParams imageParams = fullScreenGifIV.getLayoutParams();
                ViewGroup.LayoutParams frameParams = gifFrame.getLayoutParams();

                float ratio = (float)screenWidthFrame / (float)source.getWidth();

                imageParams.height = (int) (source.getHeight() * ratio);
                imageParams.width = screenWidthFrame;


                frameParams.height = imageParams.height;
                frameParams.width = imageParams.width;

                gifFrame.setLayoutParams(frameParams);
                fullScreenGifIV.setLayoutParams(imageParams);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gifFrame.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
            animation.setInterpolator(new OvershootInterpolator());
            gifFrame.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    gifFrame.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            return;
        }
        super.onBackPressed();
    }
}
