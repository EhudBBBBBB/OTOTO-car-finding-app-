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


public class SignUpFragment2 extends Fragment {

private static final String TAG = "SignUpFragment2";



    public SignUpFragment2() {
        super(R.layout.fragment_sign_up2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated successful");
        //קישור בין הviews של הXML לjava
        EditText etpassword = view.findViewById(R.id.etpassword);
        Button btnNext = view.findViewById(R.id.btnNext);
        TextView tvValidationMessage = view.findViewById(R.id.tvValidationMessage);

        SignUpViewModel vm = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);//

        vm.getPassword().observe(getViewLifecycleOwner(), password ->{
            if(password==null){
                return;
            }
            String current = etpassword.getText().toString();
            if(!current.equals(password)){//בדיקה האם הערך שהמשתמש הקליד זהה לערך בview model
                etpassword.setText(password);
                etpassword.setSelection(password.length());
                Log.d(TAG,"password was updated by the view model");
            }
        });

        btnNext.setOnClickListener(v -> {
            String password = etpassword.getText().toString();
            ValidationResult result=vm.setPassword(password);
            if(!result.getIsValid()){
                Log.d(TAG,"password is not valid");
                tvValidationMessage.setText(result.getErrorMessage());
                tvValidationMessage.setVisibility(View.VISIBLE);
            }
            else{
                Log.d(TAG,"password is valid");
                tvValidationMessage.setVisibility(View.GONE);
                vm.onNext();
            }
        });



    }

}