package com.igorofa.paperbank.paperbank.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;

/**
 * Created by x4b1d on 22/02/17.
 */

@Entity
public final class Paper {

    @Id
    private long id;

    @SerializedName("id")
    @Expose
    private Integer paperId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("paper_file")
    @Expose
    private String paperFile;

    @Generated(hash = 147105847)
    public Paper(long id, Integer paperId, String name, String paperFile) {
        this.id = id;
        this.paperId = paperId;
        this.name = name;
        this.paperFile = paperFile;
    }

    @Generated(hash = 891627928)
    public Paper() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaperFile() {
        return paperFile;
    }

    public void setPaperFile(String paperFile) {
        this.paperFile = paperFile;
    }

}
