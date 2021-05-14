package com.example.hotelhunter.ui.account;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelhunter.R;
import com.example.hotelhunter.ui.add_for_rent.AddForRentFragment;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;
    public boolean isLogined;
    TextView tvAcc, tvAccAddress, tvAccName, tvAccArea, tvAccContact;
    Button btnLogin, btnLogout, btnRegister;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        tvAcc = (TextView) view.findViewById(R.id.tv_acc);
        tvAccAddress = (TextView) view.findViewById(R.id.tv_acc_address);
        tvAccName = (TextView) view.findViewById(R.id.tv_acc_name);
        tvAccArea = (TextView) view.findViewById(R.id.tv_acc_area);
        tvAccContact = (TextView) view.findViewById(R.id.tv_acc_contact);

        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);
        btnRegister = (Button) view.findViewById(R.id.btn_register);

        if (tvAcc.getText().toString() == ""){
            isLogined = false;
            btnLogout.setVisibility(View.INVISIBLE);
        }
        else {
            isLogined = true;
            btnLogin.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
                /*AddForRentFragment forRentFragment = new AddForRentFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), forRentFragment, "findThisFragment")
                        .commit();

                 */
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}