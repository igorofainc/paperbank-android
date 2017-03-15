package com.igorofa.paperbank.paperbank.models;

/**
 * Created by x4b1d on 22/02/17.
 */

public class Paper {
    long id;

    private String paperTitle;
    private String paperTag;
    private boolean hasBeenSaved;

    public Paper(final String paperTitle, final String paperTag){
        this.paperTitle = paperTitle;
        this.paperTag = paperTag;
    }

    public Paper(final String paperTitle, final String paperTag, final boolean yesSaved){
        this.paperTitle = paperTitle;
        this.paperTag = paperTag;
        this.hasBeenSaved = yesSaved;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(final String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getPaperTag() {
        return paperTag;
    }

    public void setPaperTag(final String paperTag) {
        this.paperTag = paperTag;
    }

    public boolean isHasBeenSaved() {
        return hasBeenSaved;
    }

    public void setHasBeenSaved(final boolean hasBeenSaved) {
        this.hasBeenSaved = hasBeenSaved;
    }
}
