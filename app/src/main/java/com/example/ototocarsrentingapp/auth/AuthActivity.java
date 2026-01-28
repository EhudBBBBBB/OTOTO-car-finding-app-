package com.example.ototocarsrentingapp.auth;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ototocarsrentingapp.R;
import com.example.ototocarsrentingapp.auth.ViewModel.SignUpViewModel;

import com.example.ototocarsrentingapp.auth.fragments.SignUp1PersonalInfoFragment;
import com.example.ototocarsrentingapp.auth.fragments.SignUp3AddressFragment;
import com.example.ototocarsrentingapp.auth.fragments.SignUp2PasswordFragment;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);//קישור בין הbackend לfrontend
        Log.d(TAG,"onCreate successful");

        SignUpViewModel vm = new ViewModelProvider(this).get(SignUpViewModel.class);
        Log.d(TAG,"connection to ViewModel was successful");




        vm.getCurrentState().observe(this, state -> {
            Fragment fragmnet;

            switch(state){//ייצור fragment לפי הstate
               case PERSONAL_DETAILS:
                   fragmnet = new SignUp1PersonalInfoFragment();
                   break;
               case PASSWORD_DETAILS:
                   fragmnet = new SignUp2PasswordFragment();
                   break;
                case ADDRESS_DETAILS:
                    fragmnet = new SignUp3AddressFragment();
                    break;
               case REVIEW_DETAILS:
                default:
                    fragmnet = new SignUp1PersonalInfoFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragmnet)
                    .commit();
            Log.d(TAG,"transaction to"+fragmnet+"  was successful");
        });

    }
}