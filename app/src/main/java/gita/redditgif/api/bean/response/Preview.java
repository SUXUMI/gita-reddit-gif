package gita.redditgif.api.bean.response;

import java.util.ArrayList;

/**
 * Created by alex on 10/25/16.
 */

public class Preview {
    private ArrayList<Image> images;

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
