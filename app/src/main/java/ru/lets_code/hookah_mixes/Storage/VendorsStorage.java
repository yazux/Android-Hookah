package ru.lets_code.hookah_mixes.Storage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import ru.lets_code.hookah_mixes.APIClient;
import ru.lets_code.hookah_mixes.Data.Vendor;
import ru.lets_code.hookah_mixes.Helper;
import ru.lets_code.hookah_mixes.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class VendorsStorage extends Fragment implements DataStorageInterface <Vendor[]> {

    private Vendor[] Vendors;

    private CallbackStorageInterface Callback;

    private android.app.Activity Activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void set(Vendor[] Data)
    {
        this.Vendors = Data;
    }

    public Vendor[] g() {
        return Vendors;
    }

    public void get(android.app.Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params)
    {
        this.Callback = Callback;
        this.Activity = Activity;
        if (this.Vendors != null) {
            Callback.finish(this.Vendors);
            return;
        }

        if (!Helper.checkConnection(Activity, Context, true)) return;

        APIClient.get("/vendor?order_by=rating&order_type=desc", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String jsonArray = response.getJSONArray("response").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    VendorsStorage.this.Vendors = mapper.readValue(jsonArray, Vendor[].class);
                    VendorsStorage.this.Callback.finish(VendorsStorage.this.Vendors);
                } catch (Exception error) {
                    Toast.makeText(VendorsStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(VendorsStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}
