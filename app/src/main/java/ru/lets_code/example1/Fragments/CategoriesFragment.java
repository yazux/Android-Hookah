package ru.lets_code.example1.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import ru.lets_code.example1.Data.Category;
import ru.lets_code.example1.MainActivity;
import ru.lets_code.example1.R;
import ru.lets_code.example1.Storage.CallbackStorageInterface;
import ru.lets_code.example1.Storage.CategoriesStorage;
import ru.lets_code.example1.Storage.MixesStorage;

public class CategoriesFragment extends Fragment {

    private Activity Activity;

    /**
     * Толбар в верхней части представления
     */
    private ActionBar toolbar;

    /**
     * Представлени - список для вывода категорий
     */
    private ListView categoriesList;

    /**
     * Список категорий миксов, которые приходят с backend
     */
    public ArrayList<String> Categories;

    public SwipeRefreshLayout viewSwipeRefreshLayout;

    private CategoriesStorage Storage;

    private MixesStorage mixesStorage;

    private ArrayList<Category> categoriesObjects;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);

        Storage = (CategoriesStorage) getFragmentManager().findFragmentByTag(CategoriesStorage.class.getSimpleName());
        if (Storage == null) {
            Storage = new CategoriesStorage();
            getFragmentManager().beginTransaction().add(Storage, Storage.getClass().getSimpleName()).commit();
        }

        mixesStorage = (MixesStorage) getFragmentManager().findFragmentByTag(MixesStorage.class.getSimpleName());
        if (mixesStorage == null) {
            mixesStorage = new MixesStorage();
            getFragmentManager().beginTransaction().add(mixesStorage, mixesStorage.getClass().getSimpleName()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View FragmentView = inflater.inflate(R.layout.fragment_categories, container, false);

        Activity = ((AppCompatActivity) getActivity());

        Categories = new ArrayList<String>();
        categoriesObjects = new ArrayList<Category>();
        categoriesList = FragmentView.findViewById(R.id.categoriesList);
        viewSwipeRefreshLayout = FragmentView.findViewById(R.id.categoriesSwiperefresh);

        init();

        return FragmentView;
    }

    public void init() {
        viewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories();
            }
        });

        categoriesList.setClickable(true);
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Category selectedCategory = categoriesObjects.get(position);
                if (selectedCategory != null) mixesStorage.setCategoryId(selectedCategory.id);
                MainActivity.switchFragment(new MixesFragment(), (MainActivity) Activity);
            }
        });

        getCategories();
    }

    public void getCategories()
    {
        Storage.get(Activity, Activity, new CallbackStorageInterface<Category[]>() {
            @Override
            public void finish(Category[] Data) {
                Categories.clear();

                for (Category category : Data) {
                    if (category != null && category.getName() != null) {
                        Categories.add(category.getName());
                        categoriesObjects.add(category);
                    }
                }

                //если категории пустые, то ничего не делаем
                if (Categories.isEmpty()) return;

                // создаем адаптер
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity, R.layout.category_list_item, Categories);
                // присваиваем адаптер списку - выводим категории в представление
                categoriesList.setAdapter(adapter);

                viewSwipeRefreshLayout.setRefreshing(false);
            }
        }, new HashMap<String, String>());
    }
}
