package com.example.hotelhunter.dbForRent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.hotelhunter.R;

import java.util.ArrayList;

public class ForRentListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ForRent> mForRentList;

    public ForRentListAdapter(Context mContext, ArrayList<ForRent> mForRentList) {
        this.mContext = mContext;
        this.mForRentList = mForRentList;
    }
    @Override
    public int getCount() {
        return mForRentList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mForRentList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_for_rent_list, null);

            holder = new ViewHolder();
            holder.address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.area = (TextView) convertView.findViewById(R.id.tv_area);
            holder.contact = (TextView) convertView.findViewById(R.id.tv_contact);
            holder.description = (TextView) convertView.findViewById(R.id.tv_description);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        ForRent num = mForRentList.get(position);
        holder.address.setText(num.getAddress());
        holder.type.setText(num.getType());
        holder.price.setText(Integer.toString(num.getPrice()));
        holder.area.setText(num.getArea());
        holder.contact.setText(num.getContact());
        holder.description.setText(num.getDescription());

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
