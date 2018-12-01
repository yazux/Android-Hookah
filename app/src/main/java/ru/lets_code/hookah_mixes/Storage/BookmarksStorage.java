package ru.lets_code.hookah_mixes.Storage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import ru.lets_code.hookah_mixes.APIClient;
import ru.lets_code.hookah_mixes.Data.Bookmark;
import ru.lets_code.hookah_mixes.Helper;
import ru.lets_code.hookah_mixes.R;

public class BookmarksStorage extends Fragment implements DataStorageInterface <Bookmark[]> {
    private Bookmark[] Bookmarks;

    private CallbackStorageInterface Callback;

    private android.app.Activity Activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void set(Bookmark[] Data)
    {
        this.Bookmarks = Data;
    }

    public Bookmark[] g() {
        return Bookmarks;
    }

    public void get(android.app.Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params)
    {
        this.Callback = Callback;
        this.Activity = Activity;
        if (this.Bookmarks != null) {
            Callback.finish(this.Bookmarks);
            return;
        }

        if (!Helper.checkConnection(Activity, Context, true)) return;

        APIClient.get("/bookmark?order_by=created_at&order_type=desc", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String jsonArray = response.getJSONArray("response").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    BookmarksStorage.this.Bookmarks = mapper.readValue(jsonArray, Bookmark[].class);
                    BookmarksStorage.this.Callback.finish(BookmarksStorage.this.Bookmarks);
                } catch (Exception error) {
                    Toast.makeText(BookmarksStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(BookmarksStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
