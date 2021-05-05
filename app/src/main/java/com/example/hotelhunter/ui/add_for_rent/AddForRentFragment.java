package com.example.hotelhunter.ui.add_for_rent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hotelhunter.R;
import com.example.hotelhunter.dbForRent.DBHelper;

public class AddForRentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText edtAddress, edtPrice, edtContact, edtDescription;
    RadioGroup rgType;
    RadioButton rbNhaTro, rbChungCu, rbCanHo;
    Spinner spinnerArea;
    String[] areas = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6", "Quận 7", "Quận 8", "Quận 9", "Quận 10", "Quận 11", "Quận 12",
                        "Quận Gò Vấp", "Quận Tân Bình", "Quận Tân Phú", "Quận Phú Nhuận", "Quận Bình Thạnh", "Quận Bình Tân",
                        "Huyện Bình Chánh", "Huyện Hóc Môn", "Huyện Củ Chi", "Huyện Cần Giờ", "-Chọn quận/huyện-"};
    Button btInsert, btnReset;
    DBHelper db;

    public AddForRentFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_for_rent, container, false);

        edtAddress = (EditText) view.findViewById(R.id.edt_address);
        edtPrice = (EditText) view.findViewById(R.id.edt_price);
        edtContact = (EditText) view.findViewById(R.id.edt_contact);
        edtDescription = (EditText) view.findViewById(R.id.edt_description);

        rgType = (RadioGroup) view.findViewById(R.id.rg_type);
        rbNhaTro = (RadioButton) view.findViewById(R.id.rb_nhatro);
        rbChungCu = (RadioButton) view.findViewById(R.id.rb_chungcu);
        rbCanHo = (RadioButton) view.findViewById(R.id.rb_canho);


        btInsert = (Button) view.findViewById(R.id.btn_insert);
        btnReset = (Button) view.findViewById(R.id.btn_reset);

        spinnerArea = (Spinner) view.findViewById(R.id.sp_area);
        AddSpinner();

        db = new DBHelper(getActivity());
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String addressTxt = edtAddress.getText().toString();
                if ((rgType.getCheckedRadioButtonId() == -1)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Vui lòng chọn loại phòng cho thuê", Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedID = rgType.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton) getView().findViewById(selectedID);
                String typeTxt = selectedRadioButton.getText().toString();
                int price = Integer.parseInt(edtPrice.getText().toString());
                String areaTxt = spinnerArea.getSelectedItem().toString();
                String contact = edtContact.getText().toString();
                String description = edtDescription.getText().toString();

                Boolean checkInsertData = db.insertDBData(addressTxt,typeTxt, price, areaTxt, contact, description);
                if (checkInsertData == true) {

                    AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
                    builder.setTitle("THÊM THÔNG TIN THÀNH CÔNG");
                    builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    String msg = "Địa chỉ: " + addressTxt + "\n\n";
                    msg += "Loại: " + typeTxt + "\n";
                    msg += "Giá: " + String.valueOf(price) + "\n";
                    msg += "Khu vực: " + areaTxt + "\n";
                    msg += "Số điện thoại: " + contact + "\n";
                    msg += "Mô tả" + description + "\n";
                    builder.setMessage(msg);
                    builder.create().show();
                }
                else {
                    AlertDialog.Builder builderFailded = new  AlertDialog.Builder(getActivity());
                    builderFailded.setTitle("THÊM THÔNG TIN THẤT BẠI2");
                    builderFailded.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    String msgFailed = "Địa chỉ: " + addressTxt + " đã có trong danh sách thông tin cho thuê\n";
                    builderFailded.setMessage(msgFailed);
                    builderFailded.create().show();
                }
            }
        });

        return view;
    }

    private void AddSpinner() {
        ArrayAdapter<String> areaArrAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_item, areas) {
            @Override
            public int getCount() {
                return (areas.length - 1);
            }
        };
        areaArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(areaArrAdapter);
        spinnerArea.setSelection(areas.length - 1);
        spinnerArea.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String textSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}