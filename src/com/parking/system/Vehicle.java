package com.parking.system;

public class Vehicle {
    private String vehicleNumber;
    private String vehicleType; // Car, Bike, Bus
    private String ownerName;
    private String entryTime;
    
    // Constructor
    public Vehicle(String vehicleNumber, String vehicleType, String ownerName, String entryTime) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.ownerName = ownerName;
        this.entryTime = entryTime;
    }
    
    // Getters
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    
    public String getVehicleType() {
        return vehicleType;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public String getEntryTime() {
        return entryTime;
    }
    
    @Override
    public String toString() {
        return "Vehicle Number: " + vehicleNumber + 
               "\nType: " + vehicleType + 
               "\nOwner: " + ownerName + 
               "\nEntry Time: " + entryTime;
    }
}
