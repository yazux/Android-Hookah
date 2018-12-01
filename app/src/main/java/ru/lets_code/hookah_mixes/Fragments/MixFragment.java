package ru.lets_code.hookah_mixes.Fragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ru.lets_code.hookah_mixes.Adapters.MixFlavorAdapter;
import ru.lets_code.hookah_mixes.Data.Mix;
import ru.lets_code.hookah_mixes.Data.Tobacco;
import ru.lets_code.hookah_mixes.R;
import ru.lets_code.hookah_mixes.Storage.CallbackStorageInterface;
import ru.lets_code.hookah_mixes.Storage.MixStorage;
import ru.lets_code.hookah_mixes.View.NestedListView;

public class MixFragment extends Fragment {
    private MixStorage Storage;

    private Activity activity;

    private View FragmentView;

    private int id;

    private ViewGroup FragmentViewGroup;

    public void setId(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Storage = (MixStorage) getFragmentManager().findFragmentByTag(MixStorage.class.getSimpleName());
        if (Storage == null) {
            Storage = new MixStorage();
            getFragmentManager().beginTransaction().add(Storage, Storage.getClass().getSimpleName()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment_mix, container, false);

        activity = ((AppCompatActivity) getActivity());
        FragmentViewGroup = container;

        Mix mix = Storage.g();

        if (mix == null) {
            getMixes();
        } else {
            init(mix);
        }

        return FragmentView;
    }

    public void init(Mix mix) {
        NestedListView ProgressContainer = (NestedListView) FragmentView.findViewWithTag("mixItemProgressContainer");
        ArrayList<Tobacco> ProgressList = new ArrayList<Tobacco>(Arrays.asList(mix.tobacco));
        MixFlavorAdapter ProgressAdapter = new MixFlavorAdapter(activity, ProgressList);
        ProgressContainer.setAdapter(ProgressAdapter);

        TextView description = (TextView) FragmentView.findViewWithTag("mixItemDescription");
        description.setText(mix.description);

        TextView additionally = (TextView) FragmentView.findViewWithTag("mixItemDescription");
        additionally.setText(mix.additionally);

        //выводим список порядка укладки табака
        FlowLayout Stowage = (FlowLayout) FragmentView.findViewWithTag("mixItemStowageValue");
        int num = 1;
        for (String item : mix.stowage) {
            TextView stowageItem = (TextView) LayoutInflater.from(activity).inflate(R.layout.mix_stowage_list_item, FragmentViewGroup, false);
            stowageItem.setText((num + ". " + item));
            Stowage.addView(stowageItem);
            num++;
        }

        TextView liquid = (TextView) FragmentView.findViewWithTag("mixItemLiquidValue");
        liquid.setText(mix.liquid);

        TextView coal = (TextView) FragmentView.findViewWithTag("mixItemCoalValue");
        coal.setText(mix.coal);
    }

    public void getMixes()
    {
        if (this.id != 0) return;

        HashMap Params = new HashMap<String, String>();
        Params.put("id", "1");

        Storage.get(activity, activity, new CallbackStorageInterface<Mix>() {
            @Override
            public void finish(Mix Data) {
                init(Data);
            }
        }, Params);
    }
}
