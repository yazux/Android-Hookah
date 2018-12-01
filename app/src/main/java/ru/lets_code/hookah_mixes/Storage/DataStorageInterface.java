package ru.lets_code.hookah_mixes.Storage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;

public interface DataStorageInterface<T> {
    public void onCreate(Bundle savedInstanceState);
    public void set(T Data);
    public void get(Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params);
}