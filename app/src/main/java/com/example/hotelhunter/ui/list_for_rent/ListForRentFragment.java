package com.example.hotelhunter.ui.list_for_rent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.hotelhunter.dbForRent.ForRent;
import com.example.hotelhunter.dbForRent.ForRentListAdapter;
import com.example.hotelhunter.R;
import com.example.hotelhunter.dbForRent.DBHelper;

import java.util.ArrayList;

public class ListForRentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    DBHelper db;
    ArrayList<ForRent> mForRentList;
    ListView lvForRent, lvSearch;
    private SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_for_rent, container, false);

        db = new DBHelper(getActivity());
        lvForRent = (ListView) view.findViewById(R.id.lv_for_rent);

        mForRentList = db.viewDBData();

        mForRentList.add(new ForRent("371 Nguyễn Kiệm, phường 3, quận Gò Vấp, Thành phố Hồ Chí Minh", "Chung cư",
                5000000, "Quận Gò Vấp", "0123456789", "ĐH Mở CS Nguyễn Kiệm"));

        mForRentList.add(new ForRent("31 Nguyễn Văn Công, phường 3, quận Gò Vấp, Thành phố Hồ Chí Minh", "Căn hộ",
                5000000, "Quận Gò Vấp", "0789654132", "Nhà Cho Thuê"));

        mForRentList.add(new ForRent("174 Lí Thường Kiệt, phường 6, quận 11, Thành phố Hồ Chí Minh", "Nhà trọ",
                5000000, "Quận 11", "0123456789", "Phòng cho thuê, không chung chủ, giờ giấc tự do"));

        mForRentList.add(new ForRent("97 Võ Văn Tần, phường 6, quận 3, Thành phố Hồ Chí Minh", "Chung cư",
                5000000, "Quận 3", "0123456789", "ĐH Mở CS Võ Văn Tần"));

        mForRentList.add(new ForRent("79 Cao Thắng, phường 3, quận 3, Thành phố Hồ Chí Minh", "Nhà trọ",
                5000000, "Quận 3", "0123456789", "Nhà cho thuê nguyên căn"));

        ForRentListAdapter myAdapter = new ForRentListAdapter(getActivity().getApplicationContext(),mForRentList);
        lvForRent.setAdapter(myAdapter);


        searchView = (SearchView) view.findViewById(R.id.item_search);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ForRent> forRentListSearch = new ArrayList<>();
                DBHelper searchDB = new DBHelper(getActivity());
                forRentListSearch  = searchDB.searchDBData(newText);

                lvSearch = (ListView) getView().findViewById(R.id.lv_for_rent);

                ForRentListAdapter myAdapterSearch = new ForRentListAdapter(getActivity().getApplicationContext(),forRentListSearch);
                lvSearch.setAdapter(myAdapterSearch);
                return true;
            }
        });
        return view;
    }

    private void AddSpinner(Spinner spinner, String[] str) {
        ArrayAdapter<String> areaArrAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item, str) {
            @Override
            public int getCount() {
                return (str.length);
            }
        };
        areaArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaArrAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}