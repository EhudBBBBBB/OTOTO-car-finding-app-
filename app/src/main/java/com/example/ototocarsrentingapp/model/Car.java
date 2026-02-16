package com.example.ototocarsrentingapp.model;
import java.util.Calendar;

public class Car {
    private CarColor carColor;       // enum צבע הרכב
    private String licensePlate;        // מספר רישוי
    private String kilometers;          // קילומטרים
    private int vehicleValueForOneDay;        // שווי הרכב
    private String seatsNumber;         // מספר מקומות ישיבה
    private CarType carModel;
    private String year;                // שנת רישוי

    //----------------
    //constructors
    //----------------

    private Car(String licensePlate, CarColor carColor, String kilometers, int vehicleValue, String seatsNumber, String year, CarType carModel) {
        {
            this.licensePlate = licensePlate;
            this.carColor = carColor;
            this.kilometers = kilometers;
            this.vehicleValueForOneDay = vehicleValue;
            this.seatsNumber = seatsNumber;
            this.year = year;
            this.carModel = carModel;
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
}
