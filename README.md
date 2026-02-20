# Smart Parking System

## Overview

The Smart Parking System is a desktop application developed using Java Swing and MySQL to efficiently manage vehicle parking operations. It allows users to park vehicles, remove vehicles, view slot availability, and calculate parking fees through a user-friendly graphical interface. The system stores vehicle data in a MySQL database for reliable and persistent management.

---

## Features

* Park vehicles with vehicle number, owner name, and vehicle type
* Automatically assign available parking slots
* Remove vehicles and update slot availability
* View all parked vehicles
* Display available parking slots
* Calculate parking fees based on vehicle type and duration
* Store and retrieve vehicle records using MySQL database
* Interactive GUI built using Java Swing

---

## Concepts Utilised

* Object-Oriented Programming (OOP) – Classes, Objects, Encapsulation
* GUI Development using Java Swing
* Database Connectivity using JDBC
* SQL for data storage and retrieval
* Event Handling in Java
* ArrayList for dynamic slot management
* Exception Handling

---

## Technologies Used

* Java
* Java Swing
* MySQL
* JDBC (Java Database Connectivity)
* SQL

---

## Project Structure

```text
SmartParkingSystem
│
├── src/com/parking/system/
│   ├── Vehicle.java
│   ├── ParkingSlot.java
│   ├── ParkingLot.java
│   ├── ParkingSystemApp.java
│   └── DatabaseManager.java
│
└── lib/
    └── mysql-connector-j-9.6.0.jar
```

---

## Database Schema

```sql
CREATE DATABASE smart_parking;

USE smart_parking;

CREATE TABLE vehicles (
    vehicleNumber VARCHAR(20) PRIMARY KEY,
    vehicleType VARCHAR(10),
    ownerName VARCHAR(50),
    entryTime VARCHAR(20)
);
```

## Skills Demonstrated

Java • Object-Oriented Programming • GUI Development • JDBC • SQL • Database Integration • Software Development

---

## Impact

* Developed a complete real-world parking management system
* Improved skills in Java GUI development and database integration
* Gained hands-on experience with OOP and JDBC


