package com.igorofa.paperbank.paperbank;

import com.igorofa.paperbank.paperbank.models.PapersDataBaseWrapper;
import com.igorofa.paperbank.paperbank.network.PaperBankFileDownloadService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    MockWebServer mWebServer;

    @Before
    public void setUp() throws IOException {

        mWebServer = new MockWebServer();

        mWebServer.start();
    }

    @Test
    public void testRestService(){
//        System.out.println(PaperBankFileDownloadService.getInstance() == null);
//        assertNotNull(PaperBankFileDownloadService.getInstance());
    }

    @Test
    public void testRest() throws InterruptedException {
        HttpUrl httpUrl = mWebServer.url("users");

        TestObserver<Response<ResponseBody>> testSubscriber = new TestObserver<>();
        File file = new File("laz.txt");

        mWebServer.enqueue(
                new MockResponse().setResponseCode(206)
                        .setBody("Page Unavailable Page Unavailable Page Unavailable")
                        .throttleBody(20, 10, TimeUnit.SECONDS));

//        RecordedRequest recordedRequest = mWebServer.takeRequest();
//        for (int i = 0; i < recordedRequest.getHeaders().size(); i++) {
//            System.out.println(recordedRequest.getHeaders().name(i) + ": " + recordedRequest.getHeaders().value(i));
//        }

        new PaperBankFileDownloadService(file).getFile(httpUrl, false)
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertSubscribed();
//        testSubscriber.assertError(FileNotDownloadedException.class);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testFileCreate(){
        PapersDataBaseWrapper papersDataBaseWrapper = new PapersDataBaseWrapper();
        String title = "S6 Physics 2016";

        String result = papersDataBaseWrapper.createFileDate(title);

        System.out.println(result);
    }

    @After
    public void tearDown() throws IOException {
        mWebServer.shutdown();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}