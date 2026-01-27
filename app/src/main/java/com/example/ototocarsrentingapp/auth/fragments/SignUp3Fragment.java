package com.example.ototocarsrentingapp.auth.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ototocarsrentingapp.R;
import com.example.ototocarsrentingapp.auth.Validator.ValidationResult;
import com.example.ototocarsrentingapp.auth.ViewModel.SignUpViewModel;


public class SignUp3Fragment extends Fragment {
    private static final String TAG = "SignUp3Fragment";

    public SignUp3Fragment() {
        super(R.layout.fragment_sign_up3);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated successful");
        //חיבור בין הviews של הXML לjava
        EditText city = view.findViewById(R.id.city);
        Button btnNext = view.findViewById(R.id.btnNext);
        TextView tvValidationMessage = view.findViewById(R.id.tvValidationMessage);

        //חיבור לSign Up view model
        SignUpViewModel vm = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        vm.getCity().observe(getViewLifecycleOwner(), city1 ->{
            if(city1==null){
                return;
            }
            String current = city.getText().toString();
            if(!current.equals(city1)){//בדיקה האם הערך שהמשתמש הקליד זהה לערך בview model
                city.setText(city1);
                city.setSelection(city1.length());
                Log.d(TAG,"city was updated by the view model");
            }
        });

        btnNext.setOnClickListener(v -> {
            ValidationResult result= vm.setCity(city.getText().toString());
            if(!result.getIsValid()){
                Log.d(TAG,"city is not valid");
                tvValidationMessage.setText(result.getErrorMessage());
                tvValidationMessage.setVisibility(View.VISIBLE);
            }
            else{
                Log.d(TAG,"city is valid");
                vm.onNext();
            }
        });

    }
}