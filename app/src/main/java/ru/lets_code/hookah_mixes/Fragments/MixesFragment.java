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

import ru.lets_code.hookah_mixes.Adapters.MixesAdapter;
import ru.lets_code.hookah_mixes.Data.Mix;
import ru.lets_code.hookah_mixes.Data.Tobacco;
import ru.lets_code.hookah_mixes.MainActivity;
import ru.lets_code.hookah_mixes.R;
import ru.lets_code.hookah_mixes.Storage.CallbackStorageInterface;
import ru.lets_code.hookah_mixes.Storage.MixStorage;
import ru.lets_code.hookah_mixes.Storage.MixesStorage;

public class MixesFragment extends Fragment {

    private Activity activity;

    /**
     * Толбар в верхней части представления
     */
    private ActionBar toolbar;

    /**
     * Представлени - список для вывода миксов
     */
    private ListView mixesList;

    /**
     * Список миксов, которые приходят с backend
     */
    public ArrayList<String> Mixes;

    public SwipeRefreshLayout viewSwipeRefreshLayout;

    private MixesStorage Storage;

    private MixStorage mixStorage;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);

        Storage = (MixesStorage) getFragmentManager().findFragmentByTag(MixesStorage.class.getSimpleName());
        if (Storage == null) {
            Storage = new MixesStorage();
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
        View FragmentView = inflater.inflate(R.layout.fragment_mixes, container, false);

        Mixes = new ArrayList<String>();
        mixesList = FragmentView.findViewById(R.id.mixesList);
        viewSwipeRefreshLayout = FragmentView.findViewById(R.id.mixesSwiperefresh);

        init();
        return FragmentView;
    }


    public void init() {
        viewSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMixes();
            }
        });

        mixesList.setClickable(true);
        mixesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Object o = mixesList.getItemAtPosition(position);
            Mix mix = Mix.class.cast(o);
            if (mix != null) mixStorage.set(mix);
            MainActivity.switchFragment(new MixFragment(), (MainActivity) activity);
            }
        });

        getMixes();
    }

    public void getMixes()
    {
        Storage.get(activity, activity, new CallbackStorageInterface<Mix[]>() {
            @Override
            public void finish(Mix[] Data) {
                ArrayList<Mix> listMixes = new ArrayList<Mix>(Arrays.asList(Data));
                ArrayList<Mix> MixesResult = new ArrayList<Mix>();

                for (Mix mix : listMixes) {
                    Mix currentMix = mix;
                    ArrayList<String> tobaccoText = new ArrayList<String>();
                    for (Tobacco tobacco : currentMix.tobacco) {
                        tobaccoText.add(tobacco.vendor.name + ", " + tobacco.flavor);
                    }

                    currentMix.setTobacco_text(tobaccoText);
                    MixesResult.add(currentMix);
                }

                // создаем адаптер
                MixesAdapter adapter = new MixesAdapter(activity, MixesResult);
                // присваиваем адаптер списку - выводим категории в представление
                mixesList.setAdapter(adapter);
                viewSwipeRefreshLayout.setRefreshing(false);
            }
        }, new HashMap<String, String>());
    }
}
