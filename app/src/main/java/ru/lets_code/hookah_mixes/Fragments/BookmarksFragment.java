package ru.lets_code.hookah_mixes.Fragments;

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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ru.lets_code.hookah_mixes.Adapters.BookmarksAdapter;
import ru.lets_code.hookah_mixes.Data.Bookmark;
import ru.lets_code.hookah_mixes.MainActivity;
import ru.lets_code.hookah_mixes.R;
import ru.lets_code.hookah_mixes.Storage.BookmarksStorage;
import ru.lets_code.hookah_mixes.Storage.CallbackStorageInterface;
import ru.lets_code.hookah_mixes.Storage.MixStorage;

public class BookmarksFragment extends Fragment {
    private Activity activity;

    /**
     * Толбар в верхней части представления
     */
    private ActionBar toolbar;

    /**
     * Представлени - список для вывода категорий
     */
    private ListView BookmarkList;

    /**
     * Список категорий миксов, которые приходят с backend
     */
    public ArrayList<String> Bookmarks;

    public SwipeRefreshLayout ViewSwipeRefreshLayout;

    private BookmarksStorage Storage;

    private MixStorage mixStorage;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Storage = (BookmarksStorage) getFragmentManager().findFragmentByTag(BookmarksStorage.class.getSimpleName());
        if (Storage == null) {
            Storage = new BookmarksStorage();
            getFragmentManager().beginTransaction().add(Storage, Storage.getClass().getSimpleName()).commit();
        }

        mixStorage = (MixStorage) getFragmentManager().findFragmentByTag(MixStorage.class.getSimpleName());
        if (mixStorage == null) {
            mixStorage = new MixStorage();
            getFragmentManager().beginTransaction().add(mixStorage, mixStorage.getClass().getSimpleName()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = ((AppCompatActivity) getActivity());
        View FragmentView = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        BookmarkList = FragmentView.findViewById(R.id.bookmarkList);
        ViewSwipeRefreshLayout = FragmentView.findViewById(R.id.bookmarkSwipeRefresh);

        init();
        return FragmentView;
    }

    public void init() {
        ViewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBookmarks();
            }
        });


        BookmarkList.setClickable(true);
        BookmarkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = BookmarkList.getItemAtPosition(position);
                Bookmark bookmark = Bookmark.class.cast(o);
                if (bookmark != null) mixStorage.set(bookmark.mix);
                MainActivity.switchFragment(new MixFragment(), (MainActivity) activity);
            }
        });
        getBookmarks();
    }

    public void getBookmarks()
    {
        Storage.get(activity, activity, new CallbackStorageInterface<Bookmark[]>() {
            @Override
            public void finish(Bookmark[] Data) {
                ViewSwipeRefreshLayout.setRefreshing(false);
                ArrayList<Bookmark> listBookmarks = new ArrayList<Bookmark>(Arrays.asList(Data));

                // создаем адаптер
                BookmarksAdapter adapter = new BookmarksAdapter(activity, listBookmarks);
                // присваиваем адаптер списку - выводим категории в представление
                BookmarkList.setAdapter(adapter);

                ViewSwipeRefreshLayout.setRefreshing(false);
            }
        }, new HashMap<String, String>());
    }
}
