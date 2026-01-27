package com.example.ototocarsrentingapp.model;

public class Seller extends User  {
    private Car car;

    public Seller(Builder builder) {
        super(builder);
        this.car = builder.car;
    }
    public static class Builder extends User.Builder{
        private Car car;

        public Builder setCar(Car car) {
            this.car=car;
            return this;
        }
        public Seller build(){
            return new Seller(this);
        }
    }
}
