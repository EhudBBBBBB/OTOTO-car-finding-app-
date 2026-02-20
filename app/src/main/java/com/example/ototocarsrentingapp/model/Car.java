package com.example.ototocarsrentingapp.model;
import java.util.Calendar;

public class Car {
    private String carColor;       // enum צבע הרכב
    private String licensePlate;        // מספר רישוי
    private String kilometers;          // קילומטרים
    private int vehicleValueForOneDay;        // שווי הרכב
    private String seatsNumber;         // מספר מקומות ישיבה
    private String carModel;
    private String year;                // שנת רישוי

    //----------------
    //constructors
    //----------------

    public Car(String licensePlate, String carColor, String kilometers, String seatsNumber, String year, String carModel) {
        {
            this.licensePlate = licensePlate;
            this.carColor = carColor;
            this.kilometers = kilometers;
            this.seatsNumber = seatsNumber;
            this.year = year;
            this.carModel = carModel;
        }
    }
    public Car() {

    }
    //פעולות get וset לכל הנתונים
    // carColor
    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    // licensePlate
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    // kilometers
    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    // vehicleValueForOneDay
    public int getVehicleValueForOneDay() {
        return vehicleValueForOneDay;
    }

    public void setVehicleValueForOneDay(int vehicleValueForOneDay) {
        this.vehicleValueForOneDay = vehicleValueForOneDay;
    }

    // seatsNumber
    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    // carModel
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    // year
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    //לבנות מתודה המחשבת שווי הרכב
    /*
    public void calculateValueForOneDay(){
        this.vehicleValueForOneDay = this.carModel.getCarPrice();
        int current_year = Calendar.getInstance().get(Calendar.YEAR);
        int age = current_year - this.year;//גיל הרכב
        this.vehicleValueForOneDay *= Math.pow(0.9,age);//שווי הרכב לפי הגיל(מורידים 10% משווי הרכב כל שנה)
        this.vehicleValueForOneDay= this.vehicleValueForOneDay/365;//שווי הרכב לפי יום
    }
    */
}
