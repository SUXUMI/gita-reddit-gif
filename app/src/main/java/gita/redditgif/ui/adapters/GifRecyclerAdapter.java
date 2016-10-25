package gita.redditgif.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import gita.redditgif.R;
import gita.redditgif.api.bean.response.Child;
import gita.redditgif.api.bean.response.Gif;
import gita.redditgif.ui.components.RecyclerItemClickListener;

/**
 * Created by alex on 10/25/16.
 */

public class GifRecyclerAdapter extends RecyclerView.Adapter<GifRecyclerAdapter.VH> {

    private ArrayList<Child> children;
    private Context context;
    private RecyclerItemClickListener itemClickListener;

    public GifRecyclerAdapter(Context context, RecyclerItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }


    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.gif_recycler_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        String source = getItem(position).getResolutions().get(1).getUrl();
        Log.d("GIF", source);
        holder.progressBar.setVisibility(View.VISIBLE);
        Ion.with(context).load(source).intoImageView(holder.image).setCallback(new FutureCallback<ImageView>() {
            @Override
            public void onCompleted(Exception e, ImageView result) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.title.setText(getTitle(position));
    }

    @Override
    public int getItemCount() {
        if (children == null) return 0;
        return children.size();
    }

    public Gif getItem(int pos) {
        return children.get(pos).getData().getPreview().getImages().get(0).getVariants().getGif();
    }

    public String getTitle(int pos) {
        return children.get(pos).getData().getTitle();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;
        ProgressBar progressBar;

        public VH(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.gif_progress);
            image = (ImageView) itemView.findViewById(R.id.gif_image);
            title = (TextView) itemView.findViewById(R.id.gif_title);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(getItem(getAdapterPosition()).getResolutions().get(1));
        }
    }
}
