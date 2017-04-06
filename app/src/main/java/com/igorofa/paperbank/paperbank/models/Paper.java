package com.igorofa.paperbank.paperbank.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
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
    @Index
    private Integer paperId;

    @SerializedName("name")
    @Expose
    @Index
    private String name;

    @SerializedName("paper_file")
    @Expose
    private String paperFile;

    private Date datePaper;

    @Generated(hash = 891627928)
    public Paper() {
    }

    @Generated(hash = 1848883383)
    public Paper(long id, Integer paperId, String name, String paperFile,
            Date datePaper) {
        this.id = id;
        this.paperId = paperId;
        this.name = name;
        this.paperFile = paperFile;
        this.datePaper = datePaper;
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

    public void setPaperId(@NonNull Integer paperId) {
        this.paperId = paperId;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPaperFile() {
        return paperFile;
    }

    public void setPaperFile(@NonNull String paperFile) {
        this.paperFile = paperFile;
    }

    public Date getDatePaper() {
        return datePaper;
    }

    public void setDatePaper(@NonNull Date datePaper) {
        this.datePaper = datePaper;
    }

}
