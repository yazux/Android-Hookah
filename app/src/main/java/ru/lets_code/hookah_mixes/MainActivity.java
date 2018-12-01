package ru.lets_code.hookah_mixes;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ru.lets_code.hookah_mixes.Fragments.BookmarksFragment;
import ru.lets_code.hookah_mixes.Fragments.CategoriesFragment;
import ru.lets_code.hookah_mixes.Fragments.VendorsFragment;

public class MainActivity extends AppCompatActivity
{
    /**
     * Толбар в верхней части представления
     */
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toolbar = getSupportActionBar();
        this.toolbar.setTitle("Mixes");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchFragment(new CategoriesFragment(), MainActivity.this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    toolbar.setTitle(menuItem.getTitle());
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_mixes:
                            switchFragment(new CategoriesFragment(), MainActivity.this);
                            break;
                        case R.id.navigation_vendors:
                            switchFragment(new VendorsFragment(), MainActivity.this);
                            break;
                        case R.id.navigation_bookmarks:
                            switchFragment(new BookmarksFragment(), MainActivity.this);
                            break;
                        default:
                            switchFragment(new CategoriesFragment(), MainActivity.this);
                    }
                    return false;
                }
            };

    public static boolean switchFragment(Fragment fragment, MainActivity activity) {
        if (fragment != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main_section, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        int backStackCount = MainActivity.this.getSupportFragmentManager().getBackStackEntryCount();
        //Log.d("AppDebug", "Back button: " + backStackCount);
        if (backStackCount > 1) super.onBackPressed();
    }
}