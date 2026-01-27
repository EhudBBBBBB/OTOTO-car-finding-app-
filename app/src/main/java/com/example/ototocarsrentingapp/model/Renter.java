package com.example.ototocarsrentingapp.model;

public class Renter extends User {
    private int license_number;//מספר  הרישיון
    private int license_expiration_date;//תוקף הרישיון

    //בנאי שכופה שימוש במחלקה builderכ
    private Renter(Builder builder){
        super(builder);
        this.license_number = builder.license_number;
        this.license_expiration_date = builder.license_expiration_date;
    }
    //סטטית builder שיורשת מהbuilder של הuserמחלקה
    public static class Builder extends User.Builder{
        private int license_number;
        private int license_expiration_date;
        //מתודה לאתחול מספר רישיון
        public Builder setLicenseNumber(int license_number){
            this.license_number=license_number;
            return this;
        }
        //מתודה לאתחול תוקף רישיון
        public Builder setLicenseExpirationDate(int license_expiration_date){
            this.license_expiration_date=license_expiration_date;
            return this;
        }
        //מתודה שבונה את האובייקט מסוג RENTER
        public Renter build(){
            return new Renter(this);
        }
    }
}
