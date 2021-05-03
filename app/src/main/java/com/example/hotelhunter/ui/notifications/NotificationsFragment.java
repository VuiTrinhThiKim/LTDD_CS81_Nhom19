package com.example.hotelhunter.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hotelhunter.R;

public class NotificationsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText edtAdress, edtPrice, edtContact, edtDescription;
    RadioGroup rgType;
    RadioButton rbNhaTro, rbChungCu, rbCanHo;
    Spinner spinnerArea;
    String[] areas = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6", "Quận 7", "Quận 8", "Quận 9", "Quận 10", "Quận 11", "Quận 12",
                        "Gò Vấp", "Tân Bình", "Tân Phú", "Phú Nhuận", "Bình Thạnh", "Bình Tân", "Bình Chánh", "Hóc Môn", "Củ Chi", "Cần Giờ"};
    Button btnAdd, btnReset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        edtAdress = view.findViewById(R.id.edt_address);
        edtPrice = view.findViewById(R.id.edt_price);
        edtContact = view.findViewById(R.id.edt_contact);
        edtDescription = view.findViewById(R.id.edt_description);

        rgType = view.findViewById(R.id.rg_type);
        rbNhaTro = view.findViewById(R.id.rb_nhatro);
        rbChungCu = view.findViewById(R.id.rb_chungcu);
        rbCanHo = view.findViewById(R.id.rb_canho);


        btnAdd = view.findViewById(R.id.btn_add);
        btnReset = view.findViewById(R.id.btn_reset);

        spinnerArea = (Spinner) view.findViewById(R.id.sp_area);
        ArrayAdapter<String> areaArrAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item, areas);
        areaArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(areaArrAdapter);
        spinnerArea.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String textSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}