package ru.lets_code.hookah_mixes.Storage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import ru.lets_code.hookah_mixes.APIClient;
import ru.lets_code.hookah_mixes.Data.Category;
import ru.lets_code.hookah_mixes.Helper;
import ru.lets_code.hookah_mixes.R;

public class CategoriesStorage extends Fragment implements DataStorageInterface <Category[]>
{
    private Category[] Categories;

    private CallbackStorageInterface Callback;

    private Activity Activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void set(Category[] Data)
    {
        this.Categories = Data;
    }

    public Category[] g() {
        return Categories;
    }

    public void get(android.app.Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params)
    {
        this.Callback = Callback;
        this.Activity = Activity;
        if (this.Categories != null) {
            Callback.finish(this.Categories);
            return;
        }

        if (!Helper.checkConnection(Activity, Context, true)) return;

        APIClient.get("/category", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String jsonArray = response.getJSONArray("response").toString();
                    ObjectMapper mapper = new ObjectMapper();
                    CategoriesStorage.this.Categories = mapper.readValue(jsonArray, Category[].class);
                    CategoriesStorage.this.Callback.finish(CategoriesStorage.this.Categories);
                } catch (Exception error) {
                    Log.d("AppDebug", "Ошибка загрузки категорий: " + error.getMessage());
                    Toast.makeText(CategoriesStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(CategoriesStorage.this.Activity, R.string.data_loading_fail, Toast.LENGTH_LONG).show();
            }
        });
    }
}