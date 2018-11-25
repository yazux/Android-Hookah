package ru.lets_code.example1.Storage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;
import ru.lets_code.example1.APIClient;
import ru.lets_code.example1.Data.Mix;
import ru.lets_code.example1.Helper;
import ru.lets_code.example1.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.util.HashMap;
import cz.msebera.android.httpclient.Header;

public class MixesStorage extends Fragment implements DataStorageInterface <Mix[]> {

    private Mix[] Mixes;

    private CallbackStorageInterface Callback;

    private android.app.Activity Activity;

    private int category_id;

    private int category_id_prev;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setCategoryId(int id)
    {
        this.category_id = id;
    }

    public void set(Mix[] Data)
    {
        this.Mixes = Data;
    }

    public Mix[] g() {
        return Mixes;
    }

    public void get(android.app.Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params)
    {
        this.Callback = Callback;
        this.Activity = Activity;
        if (this.Mixes != null && this.category_id_prev == this.category_id) {
            Callback.finish(this.Mixes);
            return;
        }

        if (!Helper.checkConnection(Activity, Context, true)) return;

        this.category_id_prev = this.category_id;

        String url = "/mix?order_by=rating&order_type=desc";

        if (Params.get("category_id") != null && !Params.get("category_id").isEmpty()) {
            url += "&filter={\"relation\":[[\"category\",\"id\",\"=\",\"" + Params.get("category_id") + "\"]]}";
        } else if(this.category_id != 0) {
            url += "&filter={\"relation\":[[\"category\",\"id\",\"=\",\"" + this.category_id  + "\"]]}";
        }

        APIClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String jsonArray = response.getJSONArray("response").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    MixesStorage.this.Mixes = mapper.readValue(jsonArray, Mix[].class);
                    MixesStorage.this.Callback.finish(MixesStorage.this.Mixes);
                } catch (Exception error) {
                    Toast.makeText(MixesStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
                    Log.d("AppDebug", error.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MixesStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
