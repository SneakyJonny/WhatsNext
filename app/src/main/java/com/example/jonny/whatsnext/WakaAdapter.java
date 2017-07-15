package com.example.jonny.whatsnext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonny on 7/8/2017.
 */

public class WakaAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Waka> mDataSource;

    public WakaAdapter(Context context, ArrayList<Waka> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_waka, parent, false);

        // Get the title element
        TextView titleTextView =
                (TextView) rowView.findViewById(com.example.jonny.whatsnext.R.id.waka_list_title);

        // Get the priority element
        TextView priorityTextView =
                (TextView) rowView.findViewById(com.example.jonny.whatsnext.R.id.waka_list_priority);

        Waka waka = (Waka) getItem(position);

        titleTextView.setText(waka.title);
        priorityTextView.setText(String.format("%.2f",waka.getPriority()));

        return rowView;
    }
}
