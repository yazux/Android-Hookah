package ru.lets_code.hookah_mixes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import ru.lets_code.hookah_mixes.Data.Bookmark;
import ru.lets_code.hookah_mixes.Data.Tobacco;
import ru.lets_code.hookah_mixes.R;

public class BookmarksAdapter extends ArrayAdapter<Bookmark> {

    private Context mContext;

    private List<Bookmark> MixesList = new ArrayList<>();

    public BookmarksAdapter(Context context, ArrayList<Bookmark> list) {
        super(context, 0, list);
        mContext = context;
        MixesList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.bookmark_list_item, parent, false);

        Bookmark currentBookmark = MixesList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.bookmarkListItemName);
        name.setText(currentBookmark.mix.name);

        //выводим список вкусов и табаков
        FlowLayout flavors = (FlowLayout) listItem.findViewById(R.id.bookmarkFlavorList);

        for (Tobacco tobacco : currentBookmark.mix.tobacco) {
            TextView flavor = (TextView) LayoutInflater.from(mContext).inflate(R.layout.mix_list_flavor_list_item, parent, false);
            flavor.setText((tobacco.vendor.name + ", " + tobacco.flavor));
            flavors.addView(flavor);
        }


        return listItem;
    }
}
