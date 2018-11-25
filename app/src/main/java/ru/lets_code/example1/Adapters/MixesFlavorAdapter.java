package ru.lets_code.example1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import ru.lets_code.example1.R;

public class MixesFlavorAdapter extends RecyclerView.Adapter<MixesFlavorAdapter.ViewHolder> {

    private List<String> falvors;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MixesFlavorAdapter(Context context, List<String> newFlavors) {
        this.mInflater = LayoutInflater.from(context);
        this.falvors = newFlavors;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mix_list_flavor_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = falvors.get(position);
        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return falvors.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View myView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.mixListFlavorListItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) { }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return falvors.get(id);
    }
}
