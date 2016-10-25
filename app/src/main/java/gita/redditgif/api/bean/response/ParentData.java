package gita.redditgif.api.bean.response;

import java.util.ArrayList;

/**
 * Created by alex on 10/25/16.
 */

public class ParentData {
    private String modhash;
    private ArrayList<Child> children;

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }
}
