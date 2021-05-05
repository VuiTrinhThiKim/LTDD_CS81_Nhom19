package com.example.hotelhunter.dbForRent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.hotelhunter.R;

import java.util.ArrayList;
import java.util.List;

public class ForRentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ForRent> mForRentList;

    public ForRentListAdapter(Context mContext, List<ForRent> mForRentList) {
        this.mContext = mContext;
        this.mForRentList = mForRentList;
    }
    @Override
    public int getCount() {
        return mForRentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mForRentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_for_rent_list, null);



        }
        return convertView;
    }

    private static class ViewHolder{
        public TextView address;
        public TextView type;
        public TextView price;
        public TextView area;
        public TextView contact;
        public TextView description;
    }
}
