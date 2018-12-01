package ru.lets_code.hookah_mixes.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.HashMap;

import ru.lets_code.hookah_mixes.Classes.CircleTransform;
import ru.lets_code.hookah_mixes.Data.Vendor;
import ru.lets_code.hookah_mixes.R;
import ru.lets_code.hookah_mixes.Storage.CallbackStorageInterface;
import ru.lets_code.hookah_mixes.Storage.VendorStorage;

public class VendorFragment extends Fragment {

    private VendorStorage Storage;

    private Activity activity;

    private View FragmentView;

    private int id;

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

        Storage = (VendorStorage) getFragmentManager().findFragmentByTag(VendorStorage.class.getSimpleName());
        if (Storage == null) {
            Storage = new VendorStorage();
            getFragmentManager().beginTransaction().add(Storage, Storage.getClass().getSimpleName()).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentView = inflater.inflate(R.layout.fragment_vendor, container, false);

        activity = ((AppCompatActivity) getActivity());

        Vendor vendor = Storage.g();

        if (vendor == null) {
            getVendors();
        } else {
            init(vendor);
        }

        return FragmentView;
    }

    public void init(Vendor vendor) {

        try {
            ImageView image = (ImageView) FragmentView.findViewWithTag("vendorItemImage");
            Picasso.get().load(vendor.hero_image.path).transform(new CircleTransform()).into(image);
        } catch (Exception e) {
            Log.d("AppDebug", "Error: " + e.getMessage());
        }

        TextView description = (TextView) FragmentView.findViewWithTag("vendorItemDescription");
        description.setText(vendor.description);

        TextView name = (TextView) FragmentView.findViewWithTag("vendorItemName");
        name.setText(vendor.name);

        TextView country = (TextView) FragmentView.findViewWithTag("vendorItemCountry");
        country.setText(vendor.country);

        TextView rating = (TextView) FragmentView.findViewWithTag("vendorItemRatingText");
        rating.setText(Float.toString(vendor.rating));
    }

    public void getVendors()
    {
        if (this.id != 0) return;

        HashMap Params = new HashMap<String, String>();
        Params.put("id", "1");

        Storage.get(activity, activity, new CallbackStorageInterface<Vendor>() {
            @Override
            public void finish(Vendor Data) {
                init(Data);
            }
        }, Params);
    }
}
