package ru.lets_code.example1.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ru.lets_code.example1.Adapters.VendorsAdapter;
import ru.lets_code.example1.Data.Vendor;
import ru.lets_code.example1.MainActivity;
import ru.lets_code.example1.R;
import ru.lets_code.example1.Storage.CallbackStorageInterface;
import ru.lets_code.example1.Storage.VendorStorage;
import ru.lets_code.example1.Storage.VendorsStorage;

public class VendorsFragment extends Fragment {

    private Activity activity;

    /**
     * Толбар в верхней части представления
     */
    private ActionBar toolbar;

    /**
     * Представлени - список для вывода категорий
     */
    private ListView VendorsList;

    /**
     * Список категорий миксов, которые приходят с backend
     */
    public ArrayList<String> Vendors;

    public SwipeRefreshLayout ViewSwipeRefreshLayout;

    private VendorsStorage Storage;

    private VendorStorage vendorStorage;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Storage = (VendorsStorage) getFragmentManager().findFragmentByTag(VendorsStorage.class.getSimpleName());
        if (Storage == null) {
            Storage = new VendorsStorage();
            getFragmentManager().beginTransaction().add(Storage, Storage.getClass().getSimpleName()).commit();
        }

        vendorStorage = (VendorStorage) getFragmentManager().findFragmentByTag(VendorStorage.class.getSimpleName());
        if (vendorStorage == null) {
            vendorStorage = new VendorStorage();
            getFragmentManager().beginTransaction().add(vendorStorage, vendorStorage.getClass().getSimpleName()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = ((AppCompatActivity) getActivity());
        View FragmentView = inflater.inflate(R.layout.fragment_vendors, container, false);

        VendorsList = FragmentView.findViewById(R.id.vendorsList);
        ViewSwipeRefreshLayout = FragmentView.findViewById(R.id.vendorsSwiperefresh);

        init();
        return FragmentView;
    }

    public void init() {
        ViewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVendors();
            }
        });

        VendorsList.setClickable(true);
        VendorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Object o = VendorsList.getItemAtPosition(position);
            Vendor vendor = Vendor.class.cast(o);

            if (vendor != null) vendorStorage.set(vendor);

            MainActivity.switchFragment(new VendorFragment(), (MainActivity) activity);
            }
        });

        getVendors();
    }

    public void getVendors()
    {
        Storage.get(activity, activity, new CallbackStorageInterface<Vendor[]>() {
            @Override
            public void finish(Vendor[] Data) {
                ViewSwipeRefreshLayout.setRefreshing(false);
                ArrayList<Vendor> listVendors = new ArrayList<Vendor>(Arrays.asList(Data));

                // создаем адаптер
                VendorsAdapter adapter = new VendorsAdapter(activity, listVendors);
                // присваиваем адаптер списку - выводим категории в представление
                VendorsList.setAdapter(adapter);
                ViewSwipeRefreshLayout.setRefreshing(false);

            }
        }, new HashMap<String, String>());
    }
}