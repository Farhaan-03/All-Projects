package com.dependencyinjection;


public class Address {
    private String houseNo;
    private String location;
    private int pinCode;

    public Address(String houseNo, String location, int pinCode) {
        this.houseNo = houseNo;
        this.location = location;
        this.pinCode = pinCode;
    }

    public Address() {
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "houseNo='" + houseNo + '\'' +
                ", location='" + location + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }
}
