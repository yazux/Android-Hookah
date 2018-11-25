package ru.lets_code.example1.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import ru.lets_code.example1.Data.Mix;
import ru.lets_code.example1.Data.Tobacco;
import ru.lets_code.example1.R;

public class MixesAdapter extends ArrayAdapter<Mix> {

    private Context mContext;

    private List<Mix> MixesList = new ArrayList<>();

    public MixesAdapter(Context context, ArrayList<Mix> list) {
        super(context, 0, list);
        mContext = context;
        MixesList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.mix_list_item, parent, false);

        Mix currentMix = MixesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.mixListItemName);
        name.setText(currentMix.name);

        TextView rating = (TextView) listItem.findViewById(R.id.mixListItemRatingText);
        rating.setText(Float.toString(currentMix.rating));

        //выводим список вкусов и табаков
        FlowLayout flavors = (FlowLayout) listItem.findViewById(R.id.mixesFlavorList);
        for (Tobacco tobacco : currentMix.tobacco) {
            TextView flavor = (TextView) LayoutInflater.from(mContext).inflate(R.layout.mix_list_flavor_list_item, parent, false);
            flavor.setText((tobacco.vendor.name + ", " + tobacco.flavor));
            flavors.addView(flavor);
        }

        return listItem;
    }
}