package com.igorofa.paperbank.paperbank.exceptions;

import java.io.File;

/**
 * Created by laz on 16/03/17.
 */

public class FileNotDownloadedException extends Exception {
    private File theFileNotDownloaded;

    public FileNotDownloadedException(File file) {
        super();

        theFileNotDownloaded = file;
    }

    public File getTheFileNotDownloaded() {
        return theFileNotDownloaded;
    }

    @Override
    public String getMessage() {
//        return super.getMessage();
        return "File " + theFileNotDownloaded.getName() + " not downloaded";
    }
}
