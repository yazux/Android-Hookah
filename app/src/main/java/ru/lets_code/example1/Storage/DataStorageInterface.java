package ru.lets_code.example1.Storage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public interface DataStorageInterface<T> {
    public void onCreate(Bundle savedInstanceState);
    public void set(T Data);
    public void get(Activity Activity, Context Context, CallbackStorageInterface Callback, HashMap<String, String> Params);
}