package com.example.ototocarsrentingapp.model;

public class Renter extends User {
    private String licenseNumber;//מספר  הרישיון

    //בנאי שכופה שימוש במחלקה builderכ
    public Renter(String firstName,
                  String lastName,
                  String email,
                  String address,
                  String city,
                  String licenseNumber) {
        super(firstName,lastName,email,address,city);

        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

}
