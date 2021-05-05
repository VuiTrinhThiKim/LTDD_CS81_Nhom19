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
import androidx.fragment.app.Fragment;

import com.example.hotelhunter.dbForRent.ForRent;
import com.example.hotelhunter.dbForRent.ForRentListAdapter;
import com.example.hotelhunter.R;
import com.example.hotelhunter.dbForRent.DBHelper;

import java.util.ArrayList;

public class ListForRentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Button btnSort;
    DBHelper db, dbSort;
    ArrayList<ForRent> mForRentList, mForRentSort;
    ListView lvForRent, lvForRentSort;

    String[] sortArea = {"Tất cả", "Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6", "Quận 7", "Quận 8", "Quận 9", "Quận 10", "Quận 11", "Quận 12",
            "Quận Gò Vấp", "Quận Tân Bình", "Quận Tân Phú", "Quận Phú Nhuận", "Quận Bình Thạnh", "Quận Bình Tân",
            "Huyện Bình Chánh", "Huyện Hóc Môn", "Huyện Củ Chi", "Huyện Cần Giờ"};
    String[] sortPrice = {"Tất cả","Tăng dần", "Giảm dần"};
    String[] sortType = {"Tất cả", "Nhà trọ", "Chung cư", "Căn hộ"};

    Spinner spinnerAreas, spinnerPrice, spinnerType;

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

        spinnerAreas = (Spinner) view.findViewById(R.id.spinner_area);
        spinnerPrice = (Spinner) view.findViewById(R.id.spinner_price);
        spinnerType = (Spinner) view.findViewById(R.id.spinner_type);
        AddSpinner(spinnerAreas, sortArea);
        AddSpinner(spinnerPrice, sortPrice);
        AddSpinner(spinnerType, sortType);

        btnSort = (Button) view.findViewById(R.id.btn_sort);

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spArea = spinnerAreas.toString();
                String spPrice = spinnerPrice.toString();
                String spType = spinnerType.toString();
                if (spinnerAreas.getSelectedItem().toString() != "Tất cả" || spinnerPrice.getSelectedItem().toString() != "Tất cả" ||
                        spinnerType.getSelectedItem().toString() != "Tất cả"){
                    lvForRent.setVisibility(View.GONE);
                    /*dbSort = new DBHelper(getActivity());
                    lvForRentSort = (ListView) view.findViewById(R.id.lv_for_rent);
                    mForRentSort = db.viewDBDataSort(spArea, spPrice, spType);
                    ForRentListAdapter myAdapter = new ForRentListAdapter(getActivity().getApplicationContext(),mForRentSort);
                    lvForRentSort.setAdapter(myAdapter);
                    lvForRentSort.setVisibility(view.INVISIBLE);*/
                }

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