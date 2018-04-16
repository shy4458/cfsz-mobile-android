package com.sx.cfsz.baseframework.http;

import java.io.File;

/***       Author  shy
 *         Time   2018/4/13 0013    13:57      */

public class FileInfo {

    private FileType fileType;
    private File file;
    private String formName;

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
