package com.infobip.secops.model;

import javax.persistence.*;

@Entity
@Table(name = "fileitem")
public class FileItem {

    @Id
    @GeneratedValue
    @Column(name = "fileitem_id")
    private Long id;

    @Column(name = "filepath", unique = true)
    private String filepath;

    @Column(name = "filename", unique = true)
    private String filename;

    public FileItem() {
    }

    public FileItem(String filepath, String filename) {
        this.filepath = filepath;
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
