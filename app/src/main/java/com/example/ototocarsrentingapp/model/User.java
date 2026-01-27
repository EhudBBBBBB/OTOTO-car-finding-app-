package com.example.ototocarsrentingapp.model;

public class User {
    // שדות בסיסיים
    protected String firstName;          // שם פרטי
    protected String lastName;           // שם משפחה
    protected int birthDate;             // תאריך לידה
    protected String email;              // אימייל
    protected int phoneNumber;           // מספר טלפון
    protected String address;            // כתובת
    protected String city;               // עיר
    protected int postalCode;            // מיקוד
    protected String password;           // סיסמה
    protected String confirmPassword;    // אימות סיסמה
    protected boolean isPasswordValid;   // אישור סיסמה
    protected UserType userType;         // ENUM: RENTER או SELLER


    //בנאי של user
    //כפייה על שימוש במחלקה builder בשביל ליצור אובייקט מסוג user
    public User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
        this.password  = builder.password;
        this.confirmPassword = builder.confirmPassword;
    }


    public static class Builder{
        private String firstName;
        private String lastName;
        private int birthDate;
        private String email;
        private int phoneNumber;
        private String address;
        private String city;
        private int postalCode;
        private String password;
        private String confirmPassword;

        // כל מתודת set מחזירה את Builder עצמו כדי לאפשר שרשור (Method Chaining)
        private Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setBirthDate(int birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(int phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setPostalCode(int postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
