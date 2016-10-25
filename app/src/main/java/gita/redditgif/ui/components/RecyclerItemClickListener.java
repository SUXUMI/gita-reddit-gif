package gita.redditgif.ui.components;

import gita.redditgif.api.bean.response.Source;

/**
 * Created by alex on 10/25/16.
 */

public interface RecyclerItemClickListener {
    void onItemClick(Source gifUrl);
}
