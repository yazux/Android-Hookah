package ru.lets_code.example1;

import com.loopj.android.http.*;

public class APIClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final String URL = "http://coub.lets-code.ru/api";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return URL + relativeUrl;
    }
}