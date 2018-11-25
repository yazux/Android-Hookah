package ru.lets_code.example1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;

public class FragmentBottomNavigationBar extends Fragment  {

    /**
     * Толбар в верхней части представления
     */
    private ActionBar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        this.toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) ((AppCompatActivity) getActivity()).findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return inflater.inflate(R.layout.bottom_navigation_bar, container, false);
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

                    Intent intentMain = new Intent(((AppCompatActivity) getActivity()).getApplicationContext(), nextActivity);
                    startActivity(intentMain);

                    return false;
                }
            };
}
