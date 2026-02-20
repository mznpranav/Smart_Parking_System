package com.parking.system;

public class ParkingSlot {
    private int slotNumber;
    private boolean isOccupied;
    private Vehicle parkedVehicle;

    // Constructor
    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isOccupied = false;
        this.parkedVehicle = null;
    }

    // Park a vehicle
    public boolean parkVehicle(Vehicle vehicle) {
        if (!isOccupied) {
            this.parkedVehicle = vehicle;
            this.isOccupied = true;
            return true;
        }
        return false;
    }

    // Remove vehicle
    public Vehicle removeVehicle() {
        if (isOccupied) {
            Vehicle vehicle = this.parkedVehicle;
            this.parkedVehicle = null;
            this.isOccupied = false;
            return vehicle;
        }
        return null;
    }

    // Getters
    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public String getStatus() {
        if (isOccupied) {
            return "Slot " + slotNumber + " - OCCUPIED\n" + parkedVehicle.toString();
        } else {
            return "Slot " + slotNumber + " - AVAILABLE";
        }
    }
}
