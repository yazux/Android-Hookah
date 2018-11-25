package ru.lets_code.example1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ru.lets_code.example1.Data.Tobacco;
import ru.lets_code.example1.R;

public class MixFlavorAdapter extends ArrayAdapter<Tobacco> {

    private Context mContext;

    private List<Tobacco> FlavorsList = new ArrayList<>();

    public MixFlavorAdapter(Context context, ArrayList<Tobacco> list) {
        super(context, 0, list);
        mContext = context;
        FlavorsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.mix_item_flavor_list_item, parent, false);


        Tobacco flavor = FlavorsList.get(position);

        ProgressBar Progress = (ProgressBar) listItem.findViewById(R.id.mixItemFlavorProgress);
        Progress.setProgress(flavor.percent);


        TextView ProgressText = (TextView) listItem.findViewById(R.id.mixItemFlavorPercentValue);
        ProgressText.setText((flavor.percent + "%"));

        TextView FlavorText = (TextView) listItem.findViewById(R.id.mixItemFlavorValue);
        FlavorText.setText(flavor.flavor);

        TextView VendorText = (TextView) listItem.findViewById(R.id.mixItemFlavorVendorValue);
        VendorText.setText(flavor.vendor.name);

        return listItem;
    }
}