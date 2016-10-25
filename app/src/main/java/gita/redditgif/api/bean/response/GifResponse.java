package gita.redditgif.api.bean.response;


/**
 * Created by alex on 10/25/16.
 */

public class GifResponse {
    private String kind;
    private ParentData data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ParentData getData() {
        return data;
    }

    public void setData(ParentData data) {
        this.data = data;
    }
}
