package com.igorofa.paperbank.paperbank.models;

/**
 * Created by x4b1d on 22/02/17.
 */

public class Paper {
    long id;

    private String paperTitle;
    private String paperTag;
    private boolean hasBeenSaved;
    private String onlinePaperLink;
    private String localFileUrl;

    public Paper(long id, String paperTitle, String paperTag, String onlinePaperLink) {
        this.id = id;
        this.paperTitle = paperTitle;
        this.paperTag = paperTag;
        this.onlinePaperLink = onlinePaperLink;
    }

    public Paper(long id, String paperTitle, String paperTag, boolean hasBeenSaved, String onlinePaperLink) {
        this.id = id;
        this.paperTitle = paperTitle;
        this.paperTag = paperTag;
        this.hasBeenSaved = hasBeenSaved;
        this.onlinePaperLink = onlinePaperLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setOnlinePaperLink(String onlinePaperLink) {
        this.onlinePaperLink = onlinePaperLink;
    }

    public String getOnlinePaperLink() {
        return onlinePaperLink;
    }

    public String getLocalFileUrl() {
        return localFileUrl;
    }

    public void setLocalFileUrl(String localFileUrl) {
        this.localFileUrl = localFileUrl;
    }
}
