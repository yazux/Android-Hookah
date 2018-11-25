package ru.lets_code.example1.Storage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import ru.lets_code.example1.APIClient;
import ru.lets_code.example1.Data.Vendor;
import ru.lets_code.example1.Helper;
import ru.lets_code.example1.R;

public class VendorStorage extends Fragment implements DataStorageInterface <Vendor> {

    private Vendor vendor;

    private CallbackStorageInterface Callback;

    private android.app.Activity Activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void set(Vendor Data)
    {
        this.vendor = Data;
    }

    public Vendor g() {
        return vendor;
    }

    public void get(android.app.Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params)
    {
        this.Callback = Callback;
        this.Activity = Activity;
        if (this.vendor != null) {
            Callback.finish(this.vendor);
            return;
        }

        if (!Helper.checkConnection(Activity, Context, true)) return;

        APIClient.get("/vendor/" + Params.get("id"), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String jsonArray = response.getJSONArray("response").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    VendorStorage.this.vendor = mapper.readValue(jsonArray, Vendor.class);
                    VendorStorage.this.Callback.finish(VendorStorage.this.vendor);
                } catch (Exception error) {
                    Toast.makeText(VendorStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(VendorStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
