-- Smart Parking System Database Setup Script
-- Run this script in MySQL to create the required database and tables

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS smart_parking;
USE smart_parking;

-- Create vehicles table
CREATE TABLE IF NOT EXISTS vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicleNumber VARCHAR(50) NOT NULL UNIQUE,
    vehicleType VARCHAR(20) NOT NULL,
    ownerName VARCHAR(100) NOT NULL,
    entryTime VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Verify table creation
SHOW TABLES;
SELECT * FROM vehicles;
