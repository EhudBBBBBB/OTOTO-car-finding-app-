package com.example.ototocarsrentingapp.auth.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ototocarsrentingapp.R;
import com.example.ototocarsrentingapp.auth.Validator.ValidationResult;
import com.example.ototocarsrentingapp.auth.ViewModel.SignUpViewModel;


public class SignUp1Fragment extends Fragment {

    private static final String TAG = "SignUp1Fragment";


    public SignUp1Fragment() {
        super(R.layout.fragment_sign_up1);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SignUpViewModel vm = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);//קישור בין fragment 1 לviewmodel
        EditText etFirstName = view.findViewById(R.id.etFirstName);
        Button btnNext = view.findViewById(R.id.btnNext);
        TextView tvValidationMessage = view.findViewById(R.id.tvValidationMessage);


        vm.get_first_name().observe(getViewLifecycleOwner(),firstName ->{
            if(firstName==null){
                return;
            };
            String current = etFirstName.getText().toString();
            if(!current.equals(firstName)){//בדיקה האם הערך שהמשתמש הקליד זהה לערך בview model
                etFirstName.setText(firstName);
                etFirstName.setSelection(firstName.length());//שמים את הסמן בסוף
                Log.d(TAG,"firstName was updated by the view model");
            }
        });

        btnNext.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString();
            ValidationResult result=vm.setFirsName(firstName);
            if(!result.getIsValid()){
                Log.d(TAG,"firstName is not valid");
                tvValidationMessage.setText(result.getErrorMessage());
                tvValidationMessage.setVisibility(View.VISIBLE);
            }
            else{
                Log.d(TAG,"first name is valid");
                tvValidationMessage.setVisibility(View.GONE);
                vm.onNext();//שינוי הstate של הview model
            }

        });



    }
}