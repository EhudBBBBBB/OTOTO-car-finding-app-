package com.example.ototocarsrentingapp.model;

public enum CarType {
    FIAT(75000),
    SUZUKI(80000),
    PEUGEOT(85000),
    NISSAN(90000),
    MITSUBISHI(95000),
    HYUNDAI(100000),
    KIA(105000),
    SKODA(115000),
    VOLKSWAGEN(120000),
    MAZDA(125000),
    TOYOTA(130000),
    FORD(135000),
    CHEVROLET(140000),
    HONDA(145000),
    BYD(150000),
    TESLA(180000),
    AUDI(220000),
    BMW(250000),
    MERCEDES(270000);

    private final int car_price;
    private CarType(int car_price){
        this.car_price = car_price;
    }
    public int getCarPrice(){
        return this.car_price;
    }
}
