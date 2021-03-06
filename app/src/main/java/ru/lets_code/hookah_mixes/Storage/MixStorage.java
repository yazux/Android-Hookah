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
import ru.lets_code.hookah_mixes.Data.Mix;
import ru.lets_code.hookah_mixes.Helper;
import ru.lets_code.hookah_mixes.R;

public class MixStorage extends Fragment implements DataStorageInterface <Mix> {

    private Mix mix;

    private CallbackStorageInterface Callback;

    private android.app.Activity Activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void set(Mix Data)
    {
        this.mix = Data;
    }

    public Mix g() {
        return mix;
    }

    public void get(android.app.Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params)
    {
        this.Callback = Callback;
        this.Activity = Activity;
        if (this.mix != null) {
            Callback.finish(this.mix);
            return;
        }

        if (!Helper.checkConnection(Activity, Context, true)) return;

        APIClient.get("/mix/" + Params.get("id"), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String jsonArray = response.getJSONArray("response").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    MixStorage.this.mix = mapper.readValue(jsonArray, Mix.class);
                    MixStorage.this.Callback.finish(MixStorage.this.mix);
                } catch (Exception error) {
                    Toast.makeText(MixStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MixStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
