package ru.lets_code.example1.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import ru.lets_code.example1.Classes.CircleTransform;
import ru.lets_code.example1.Data.Vendor;
import ru.lets_code.example1.R;

public class VendorsAdapter extends ArrayAdapter<Vendor> {

    private Context mContext;

    private List<Vendor> VendorsList = new ArrayList<>();

    public VendorsAdapter(Context context, ArrayList<Vendor> list) {
        super(context, 0, list);
        mContext = context;
        VendorsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.vendor_list_item, parent, false);

        Vendor currentVendor = VendorsList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.vendorListItemName);
        name.setText(currentVendor.name);

        TextView country = (TextView) listItem.findViewById(R.id.vendorListItemCountry);
        country.setText(currentVendor.country);

        TextView rating = (TextView) listItem.findViewById(R.id.vendorListItemRatingText);
        rating.setText(Float.toString(currentVendor.rating));

        TextView id = (TextView) listItem.findViewById(R.id.vendorListItemId);
        id.setText(Integer.toString(currentVendor.id));


        try {
            Picasso.get()
                    .load(currentVendor.hero_image.path)
                    .transform(new CircleTransform())
                    .into((ImageView) listItem.findViewWithTag("image"));
        } catch (Exception e) {
            Log.d("AppDebug", "Ошибка загрузки изображения: " + e.getMessage());
        }

        return listItem;
    }
}