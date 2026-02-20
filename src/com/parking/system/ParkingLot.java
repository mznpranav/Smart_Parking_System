package com.parking.system;
import java.util.ArrayList;
public class ParkingLot {
    private ArrayList<ParkingSlot> slots;
    private int totalSlots;
    
    // Constructor
    public ParkingLot(int totalSlots) {
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>();
        
        // Initialize all parking slots
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }
    
    // Park a vehicle in first available slot
    public String parkVehicle(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.parkVehicle(vehicle);
                return "Vehicle parked successfully in Slot " + slot.getSlotNumber();
            }
        }
        return "Sorry! Parking is full.";
    }
    
    // Remove vehicle by vehicle number
    public String removeVehicle(String vehicleNumber) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() && 
                slot.getParkedVehicle().getVehicleNumber().equals(vehicleNumber)) {
                slot.removeVehicle();
                return "Vehicle " + vehicleNumber + " removed from Slot " + slot.getSlotNumber();
            }
        }
        return "Vehicle not found!";
    }
    
    // Display all slots status
    public void displayAllSlots() {
        System.out.println("\n===== PARKING LOT STATUS =====");
        for (ParkingSlot slot : slots) {
            System.out.println(slot.getStatus());
            System.out.println("---------------------------");
        }
    }
    
    // Display available slots
    public void displayAvailableSlots() {
        int count = 0;
        System.out.println("\n===== AVAILABLE SLOTS =====");
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) {
                System.out.println("Slot " + slot.getSlotNumber());
                count++;
            }
        }
        System.out.println("Total Available: " + count + "/" + totalSlots);
    }
    
    // Calculate parking fee
    public double calculateFee(String vehicleType, int hours) {
        double rate = 0;
        
        switch (vehicleType.toLowerCase()) {
            case "bike":
                rate = 10.0;
                break;
            case "car":
                rate = 20.0;
                break;
            case "bus":
                rate = 30.0;
                break;
            default:
                rate = 20.0;
        }
        
        return rate * hours;
    }
    
    // Getter for slots (used by GUI)
    public ArrayList<ParkingSlot> getSlots() {
        return slots;
    }
}
