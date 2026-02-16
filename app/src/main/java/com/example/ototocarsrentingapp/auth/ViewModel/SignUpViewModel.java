package com.example.ototocarsrentingapp.auth.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;//שימוש במחלקה viewmodel של אנדרויד סטודיו
import androidx.lifecycle.LiveData;//שימוש במחלקה liveData של אנדרויד סטודיו
import androidx.lifecycle.MutableLiveData;//שימוש במחלקה mutableliveData של אנדרויד סטודיו

import com.example.ototocarsrentingapp.auth.Validator.Validator;
import com.example.ototocarsrentingapp.auth.Validator.ValidationResult;
import com.example.ototocarsrentingapp.model.CarColor;
import com.example.ototocarsrentingapp.model.CarType;
import com.example.ototocarsrentingapp.model.Renter;
import com.example.ototocarsrentingapp.model.Seller;
import com.example.ototocarsrentingapp.model.User;
import com.example.ototocarsrentingapp.model.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpViewModel extends ViewModel {

    private static final String TAG = "SignUpViewModel";

    // אתחול של FirebaseAuth
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //המצב הנוכחי
    private final MutableLiveData<SignUpStep> currentState = new MutableLiveData<>(SignUpStep.PERSONAL_DETAILS);//השלב הנוכחי

    //נתונים שצריכים להיות לכל user
    private final MutableLiveData<String> first_name = new MutableLiveData<>();//שם פרטי
    private final MutableLiveData<String> last_name = new MutableLiveData<>();//שם משפחה
    private final MutableLiveData<String> email = new MutableLiveData<>();//אמייל
    private final MutableLiveData<String> address = new MutableLiveData<>();//כתובת
    private final MutableLiveData<String> city = new MutableLiveData<>();//עיר
    private final MutableLiveData<String> password = new MutableLiveData<>();//סיסמה
    private final MutableLiveData<String> confirm_password = new MutableLiveData<>();//אישור סיסימה
    private final MutableLiveData<UserType> user_type = new MutableLiveData<>();// סוג הUSER

    //נתונים שצריכים להיות עבור כל seller
    //אובייקט מסוג CAR
    private final MutableLiveData<String> licensePlate = new MutableLiveData<>();
    private final MutableLiveData<CarColor> carColor = new MutableLiveData<>();
    private final MutableLiveData<String> Kilometers = new MutableLiveData<>();
    private final MutableLiveData<String> year = new MutableLiveData<>();
    private final MutableLiveData<CarType> carModel = new MutableLiveData<>();
    private final MutableLiveData<String> seatsNumber = new MutableLiveData<>();


    //נתונים שצריכים להיות עבור כל renter
    private final MutableLiveData<String> licenseNumber = new MutableLiveData<>();//מספר רישיון
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
                createUser();
                break;
            case ADDRESS_DETAILS:
                currentState.setValue(SignUpStep.UserType);
                break;
            case UserType:
                UserType type = user_type.getValue();
                if(type==null){
                    Log.d(TAG,"user type was not entered");
                    return;
                }
                if(type==UserType.RENTER){
                    currentState.setValue(SignUpStep.RENTER_DETAILS);
                }
                else{
                    currentState.setValue(SignUpStep.SELLER_DETAILS);
                }
                break;
            case REVIEW_DETAILS:
                break;
        }
    }

    public void onBack(){
        SignUpStep step = currentState.getValue();
        if(step==null){
            step = SignUpStep.PERSONAL_DETAILS;
        }
        switch (step){
            case PERSONAL_DETAILS:
                break;
            case ADDRESS_DETAILS:
                currentState.setValue(SignUpStep.PERSONAL_DETAILS);
                break;
            case UserType:
                currentState.setValue(SignUpStep.ADDRESS_DETAILS);
                break;
            case RENTER_DETAILS:
                currentState.setValue(SignUpStep.UserType);
                break;
            case SELLER_DETAILS:
                currentState.setValue(SignUpStep.UserType);
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
            this.first_name.setValue(first_name);
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
            this.last_name.setValue(last_name);
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
            this.email.setValue(email);
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
            this.password.setValue(value);
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
            this.confirm_password.setValue(confirmPassword);
        }
        return result;
    }
    public LiveData<String>getConfirmPassword(){
        return this.confirm_password;
    }

    //כתובת
    public LiveData<String> getAddress(){
        return this.address;
    }
    public ValidationResult setAddress(String value){
        ValidationResult result = Validator.validateAddress(value);
        if(result.getIsValid()){
            this.address.setValue(value);
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
            this.city.setValue(value);
        }
      return result;
    }

    //סוג המשתמש
    public LiveData<UserType> getUserType(){
        return this.user_type;
    }
    public void setUserType(UserType value){
        this.user_type.setValue(value);
        Log.d(TAG,"user type was changed in the view model");
        switch(value){
            case RENTER:
                Renter r = new Renter(first_name.getValue(),
                        last_name.getValue(),
                        email.getValue(),
                        address.getValue(),
                        city.getValue(),
                        licenseNumber.getValue()
                        );
                db.collection("renters").document(mAuth.getCurrentUser().getUid()).set(r).
                 addOnSuccessListener(documentReference -> {
                Log.d("Firestore", "Document added with ID: " + documentReference);
            })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error adding document", e);
                    });
                break;
                //יש צורך לבנות את האובייקט מסוג car ולשלוח אותו לבנאי של seller
                /*
            case SELLER:
                Seller s = new Seller(first_name.getValue(),
                        last_name.getValue(),
                        email.getValue(),
                        address.getValue(),
                        city.getValue(),
                        );
                db.collection("sellers").document(mAuth.getCurrentUser().getUid()).set(s).
                        addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Document added with ID: " + documentReference);
                })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error adding document", e);
                        });
                break;
                */

        }
    }

    //מצב הנוכחי של המשתמש
    public LiveData<SignUpStep> getCurrentState(){
        return this.currentState;
    }
    public void setCurrentState(SignUpStep value){
        this.currentState.setValue(value);
        Log.d(TAG,"The state was changed to"+value+"  in the view model");
    }

    //פונקציה שיוצרת user
    public void createUser(){
        String email = get_email().getValue();
        String password = getPassword().getValue();
        if (email == null || password == null) {
            Log.d(TAG,"email or password is null");
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentState.setValue(SignUpStep.ADDRESS_DETAILS);
                            Log.d(TAG,"user was created successfully"+task.getResult());
                        }
                        else{
                            Log.e(TAG,"user was not created successfully"+task.getException().getMessage());
                            //לשלוח שגיאה למסך
                        }
                    }
                });
        //פעולות set וget RENTER
        //-------------------------------------------------------------------
        }
        //license number(driver)
        public LiveData<String> getLicenseNumber(){
            return this.licenseNumber;
        }
        public ValidationResult setLicenseNumber(String value){
            ValidationResult result = Validator.validateLicenseNumber(value);
            if(result.getIsValid()){
                this.licenseNumber.setValue(value);
            }
            return result;
        }
    //------------------------------------------------------------------
    //פעולות get וset seller
    //license plate(seller)
    public LiveData<String> getLicensePlate() {
        return this.licensePlate;
    }
    public ValidationResult setLicensePlate(String value) {
        ValidationResult result = Validator.validateLicensePlate(value);
        if (result.getIsValid()) {
            this.licensePlate.setValue(value);
        }
        return result;
    }
    //kilometer(seller)
    public LiveData<String> getKilometer() {
        return this.Kilometers;
    }
    public ValidationResult setKilometer(String value) {
        ValidationResult result = Validator.validateKilometers(value);
        if (result.getIsValid()) {
            this.Kilometers.setValue(value);
        }
        return result;
    }
    public LiveData<String> getYear() {
        return this.year;
    }
    public ValidationResult setYear(String value) {
        ValidationResult result = Validator.validateYears(value);
        if (result.getIsValid()) {
            this.year.setValue(value);
        }
        return result;
    }
    }



