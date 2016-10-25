package gita.redditgif.api.bean.response;

import java.util.ArrayList;

/**
 * Created by alex on 10/25/16.
 */

public class Gif {
    private Source source;
    private ArrayList<Source> resolutions;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public ArrayList<Source> getResolutions() {
        return resolutions;
    }

    public void setResolutions(ArrayList<Source> resolutions) {
        this.resolutions = resolutions;
    }
}
