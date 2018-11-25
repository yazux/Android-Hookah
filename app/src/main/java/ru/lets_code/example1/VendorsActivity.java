package ru.lets_code.example1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.lets_code.example1.Data.Vendor;
import ru.lets_code.example1.Storage.CallbackStorageInterface;
import ru.lets_code.example1.Storage.VendorsStorage;

public class VendorsActivity extends AppCompatActivity {
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

    public SwipeRefreshLayout viewSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        Vendors = new ArrayList<String>();
        VendorsList = findViewById(R.id.vendorsList);
        viewSwipeRefreshLayout = findViewById(R.id.vendorsSwiperefresh);

        this.toolbar = getSupportActionBar();
        this.toolbar.setTitle("Vendors");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getVendors();

        viewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVendors();
            }
        });
    }

    public void getVendors()
    {
        VendorsStorage VendorsStorage = new VendorsStorage();
        VendorsStorage.get(VendorsActivity.this, this, new CallbackStorageInterface<Vendor[]>() {
            @Override
            public void finish(Vendor[] Data) {
                for (Vendor Vendor : Data)
                    if (Vendor != null && Vendor.name != null)
                        Vendors.add(Vendor.name);

                //если категории пустые, то ничего не делаем
                if (Vendors.isEmpty()) return;

                // создаем адаптер
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(VendorsActivity.this, R.layout.vendor_list_item, Vendors);
                // присваиваем адаптер списку - выводим категории в представление
                VendorsList.setAdapter(adapter);

                viewSwipeRefreshLayout.setRefreshing(false);
            }
        }, new HashMap<String, String>());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    toolbar.setTitle(menuItem.getTitle());
                    Class nextActivity;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_mixes:
                            nextActivity = MainActivity.class;
                            break;
                        case R.id.navigation_vendors:
                            nextActivity = VendorsActivity.class;
                            break;
                        case R.id.navigation_bookmarks:
                            nextActivity = MainActivity.class;
                            break;
                        default:
                            nextActivity = MainActivity.class;
                    }

                    Intent intentMain = new Intent(getApplicationContext(), nextActivity);
                    startActivity(intentMain);

                    return false;
                }
            };
}
