package com.example.ototocarsrentingapp.model;
import java.util.Calendar;

public class Car {
    private int licensePlate;        // מספר רישוי
    private CarColor carColor;       // enum צבע הרכב
    private int kilometers;          // קילומטרים
    private int vehicleValueForOneDay;        // שווי הרכב
    private int seatsNumber;         // מספר מקומות ישיבה
    private CarType carModel;
    private int year;                // שנת רישוי

    //----------------
    //constructors
    //----------------

    private Car(Builder builder) {
        this.licensePlate = builder.licensePlate;
        this.carColor = builder.carColor;
        this.kilometers = builder.kilometers;
        this.vehicleValueForOneDay = builder.vehicleValue;
        this.seatsNumber = builder.seatsNumber;
        this.year = builder.year;
        this.carModel=builder.carModel;
    }

    //מחלקה builder
    public static class Builder{
        private int licensePlate;
        private CarColor carColor;
        private int kilometers;
        private int vehicleValue;
        private int seatsNumber;
        private int year;
        private CarType carModel;

        //מתודות שמאתחלות את השדות הבסיסיים של האובייקטCAR בעזרת שרשור
        public Builder setLicensePlate(int licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setCarColor(CarColor carColor) {
            this.carColor = carColor;
            return this;
        }

        public Builder setKilometers(int kilometers) {
            this.kilometers = kilometers;
            return this;
        }

        public Builder setVehicleValue(int vehicleValue) {
            this.vehicleValue = vehicleValue;
            return this;
        }

        public Builder setSeatsNumber(int seatsNumber) {
            this.seatsNumber = seatsNumber;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Builder SetCarModel(CarType carModel){
            this.carModel=carModel;
            return this;
        }
        public Car build(){
            return new Car(this);
        }
    }
    //מתודה לחישוב שווי הרכב
    public void calculateValueForOneDay(){
        this.vehicleValueForOneDay = this.carModel.getCarPrice();
        int current_year = Calendar.getInstance().get(Calendar.YEAR);
        int age = current_year - this.year;//גיל הרכב
        this.vehicleValueForOneDay *= Math.pow(0.9,age);//שווי הרכב לפי הגיל(מורידים 10% משווי הרכב כל שנה)
        this.vehicleValueForOneDay= this.vehicleValueForOneDay/365;//שווי הרכב לפי יום
    }
}
