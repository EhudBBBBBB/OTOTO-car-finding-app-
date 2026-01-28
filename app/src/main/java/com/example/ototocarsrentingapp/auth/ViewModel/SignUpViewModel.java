package com.example.ototocarsrentingapp.auth.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;//שימוש במחלקה viewmodel של אנדרויד סטודיו
import androidx.lifecycle.LiveData;//שימוש במחלקה liveData של אנדרויד סטודיו
import androidx.lifecycle.MutableLiveData;//שימוש במחלקה mutableliveData של אנדרויד סטודיו

import com.example.ototocarsrentingapp.auth.Validator.Validator;
import com.example.ototocarsrentingapp.auth.Validator.ValidationResult;
import com.example.ototocarsrentingapp.model.CarColor;
import com.example.ototocarsrentingapp.model.CarType;
import com.example.ototocarsrentingapp.model.UserType;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = "SignUpViewModel";

    private final MutableLiveData<SignUpStep> currentState = new MutableLiveData<>(SignUpStep.PERSONAL_DETAILS);//השלב הנוכחי

    //נתונים שצריכים להיות לכל user
    private final MutableLiveData<String> first_name = new MutableLiveData<>();//שם פרטי
    private final MutableLiveData<String> last_name = new MutableLiveData<>();//שם משפחה
    private final MutableLiveData<String> birth_date = new MutableLiveData<>();//תאריך לידה
    private final MutableLiveData<String> email = new MutableLiveData<>();//אמייל
    private final MutableLiveData<String> phone_number = new MutableLiveData<>();//מספר טלפון
    private final MutableLiveData<String> address = new MutableLiveData<>();//כתובת
    private final MutableLiveData<String> city = new MutableLiveData<>();//עיר
    private final MutableLiveData<String> password = new MutableLiveData<>();//סיסמה
    private final MutableLiveData<String> confirm_password = new MutableLiveData<>();//אישור סיסימה
    private final MutableLiveData<ValidationResult> is_password_valid = new MutableLiveData<>();//אישור סיסמה
    private final MutableLiveData<UserType> user_type = new MutableLiveData<>();// סוג הUSER

    //נתונים שצריכים להיות עבור כל seller
    //אובייקט מסוג CAR
    private final MutableLiveData<Integer> licensePlate = new MutableLiveData<>();
    private final MutableLiveData<CarColor> carColor = new MutableLiveData<>();
    private final MutableLiveData<Integer> Kilometers = new MutableLiveData<>();
    private final MutableLiveData<Integer> year = new MutableLiveData<>();
    private final MutableLiveData<CarType> carModel = new MutableLiveData<>();
    private final MutableLiveData<Integer> seatsNumber = new MutableLiveData<>();


    //נתונים שצריכים להיות עבור כל renter
    private final MutableLiveData<Integer> licenseNumber = new MutableLiveData<>();//מספר רישיון
    private final MutableLiveData<Integer>  licenseExpirationDate = new MutableLiveData<>();//תוקף רישיון
    //צילום רישיון
    private final MutableLiveData<Boolean> is_seller_verified = new MutableLiveData<>();//אם המשתמש העלה תמונה אז זה מספיק לוורפיקציה


    //-----------------------------------------------------------------------
    //מתודה שמאפשרת לactivity לעבור לפי מסכים לפי הstate הנוכחי
    public void onNext(){
        // ==========================================================
        // שלב 1: פרטים אישיים
        // המטרה: לא לאפשר מעבר קדימה אם חסרים/לא תקינים:
        // שם פרטי, שם משפחה, תאריך לידה, אימייל, טלפון
        // ==========================================================
        SignUpStep step = currentState.getValue();
        if(step==null){
            step = SignUpStep.PERSONAL_DETAILS;
        }
        switch (step){
            case PERSONAL_DETAILS:
                currentState.postValue(SignUpStep.PASSWORD_DETAILS);
                break;
            case PASSWORD_DETAILS:
                currentState.postValue(SignUpStep.ADDRESS_DETAILS);
                break;
            case ADDRESS_DETAILS:
                currentState.postValue(SignUpStep.REVIEW_DETAILS);
                break;
            case REVIEW_DETAILS:
                break;
        }
    }

    public void onback(){
        SignUpStep step = currentState.getValue();
        if(step==null){
            step = SignUpStep.PERSONAL_DETAILS;
        }
        switch (step){
            case PERSONAL_DETAILS:
                break;
            case PASSWORD_DETAILS:
                currentState.setValue(SignUpStep.PERSONAL_DETAILS);
                break;
            case ADDRESS_DETAILS:
                currentState.setValue(SignUpStep.PASSWORD_DETAILS);
                break;
            case REVIEW_DETAILS:
                break;
        }
    }

    //setters and getters
    //נתונים שצריכים להיות לכל USER

    //First name
    public ValidationResult setFirstName(String first_name) {
        ValidationResult result = Validator.validateFirstName(first_name);
        if(result.getIsValid()){
            this.first_name.postValue(first_name);
            Log.d(TAG,"first name was changed in the view model");
        }
       return result;
    }
    public LiveData<String> get_first_name() {
        return first_name;
    }

    //Last name
    public ValidationResult set_last_name(String last_name) {
        ValidationResult result = Validator.validateLastName(last_name);
        if(result.getIsValid()){
            this.last_name.postValue(last_name);
            Log.d(TAG,"Last name was changed in the view model");
        }
        return result;
    }
    public LiveData<String> get_last_name() {
        return last_name;
    }

    //Email
    public ValidationResult set_email(String email) {
        ValidationResult result = Validator.validateEmail(email);
        if(result.getIsValid()){
            this.email.postValue(email);
        }
        return result;
    }
    public LiveData<String> get_email() {
        return email;
    }

    //password
    public ValidationResult  setPassword(String value){
        ValidationResult result = Validator.validatePassword(value);
        if(result.getIsValid()){
            this.password.postValue(value);
            Log.d(TAG,"password was changed in the view model");
        }
            return result;
    }

    public LiveData<String>getPassword(){
        return this.password;
    }

    //confirmpassword
    public ValidationResult setConfirmPassword(String password, String confirmPassword){
        ValidationResult result = Validator.validateConfirmPassword(password,confirmPassword);
        if(result.getIsValid()){
            this.confirm_password.postValue(confirmPassword);
        }
        return result;
    }
    public LiveData<String>getConfirmPassword(){
        return this.confirm_password;
    }

    //phone number
    public LiveData<String>getPhoneNumber(){
        return this.phone_number;
    }

    //כתובת
    public LiveData<String> getAddress(){
        return this.address;
    }
    public ValidationResult setAddress(String value){
        ValidationResult result = Validator.validateAddress(value);
        if(result.getIsValid()){
            this.address.postValue(value);
        }
        return result;
    }

    //עיר
    public LiveData<String>getCity(){
        return this.city;
    }
    public ValidationResult setCity(String value){
        ValidationResult result = Validator.validateCity(value);
        if(result.getIsValid()){
            this.city.postValue(value);
        }
      return result;
    }

    //תאריך לידה
    public LiveData<String>getBirthDate(){
        return this.birth_date;
    }
    public void setBirthDate(String value){
        this.birth_date.setValue(value);
    }

    //סוג המשתמש
    public LiveData<UserType> getUserType(){
        return this.user_type;
    }
    public void setUserType(UserType value){
        this.user_type.setValue(value);
    }

    //מצב הנוכחי של המשתמש
    public LiveData<SignUpStep> getCurrentState(){
        return this.currentState;
    }
    public void setCurrentState(SignUpStep value){
        this.currentState.postValue(value);
        Log.d(TAG,"The state was changed to"+value+"  in the view model");
    }
}
