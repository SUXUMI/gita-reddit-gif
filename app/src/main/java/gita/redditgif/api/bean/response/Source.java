package gita.redditgif.api.bean.response;

/**
 * Created by alex on 10/25/16.
 */

public class Source {

    private String url;
    private int width;
    private int height;

    public String getUrl() {
        return url.replaceAll("&amp;", "&");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
