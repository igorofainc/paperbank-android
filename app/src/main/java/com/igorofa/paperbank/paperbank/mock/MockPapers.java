package com.igorofa.paperbank.paperbank.mock;

import android.os.Environment;

import com.igorofa.paperbank.paperbank.models.Paper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laz on 18/03/17.
 */

public class MockPapers {
    private final static String TEST_PDF_URL = "https://paperbankdo.s3.amazonaws.com/uploads/Physics_2013-paperbank.pdf";

    public MockPapers() throws ClassNotFoundException {
        throw new ClassNotFoundException();
    }

    public static List<Paper> setUpMockPapers(){
        List<Paper> paperList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            long paperId = 0l;
            Paper newPaper = new Paper(/*paperId, "S6 Physics 2017", "#Physics", TEST_PDF_URL*/);
            File file = createFile(paperId);
            if (file.exists()){
//                newPaper.setLocalFileUrl(file.getAbsolutePath());
            }
            paperList.add(newPaper);
        }

        return paperList;
    }

    private static File createFile(long paperId) {
        String fileName = Long.toHexString(paperId).concat(".pdf");

        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
    }
}
