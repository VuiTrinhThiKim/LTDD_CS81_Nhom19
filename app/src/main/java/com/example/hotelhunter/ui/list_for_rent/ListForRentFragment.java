package com.example.hotelhunter.ui.list_for_rent;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotelhunter.R;
import com.example.hotelhunter.dbForRent.DBHelper;

import java.util.ArrayList;

public class ListForRentFragment extends Fragment {

    Button btnAdd;
    DBHelper db;
    ArrayList<String> listForRentItem;
    ArrayAdapter arrayAdapter;
    ListView forRentList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_for_rent, container, false);

        db = new DBHelper(getActivity());
        listForRentItem = new ArrayList<>();

        btnAdd = (Button) view.findViewById(R.id.btn_add);
        forRentList = (ListView) view.findViewById(R.id.lv_for_rent);

        /*btnAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                ListForRentFragment listForRentFragment = new ListForRentFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, listForRentFragment);
                transaction.commit();
            }
        });*/
        viewData();

        forRentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        return view;
    }

    private void viewData() {
        Cursor cursor = db.viewDBData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                listForRentItem.add(cursor.getString(0));
            }
            arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listForRentItem);
            forRentList.setAdapter(arrayAdapter);
        }
    }
}