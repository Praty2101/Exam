-- ==========================================
-- SQL Script for Student Management System
-- Run this in MySQL Workbench or MySQL CLI
-- ==========================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS studentdb;

-- Step 2: Use the database
USE studentdb;

-- Step 3: Create the students table
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    mobile VARCHAR(15) NOT NULL
);

-- Step 4: (Optional) Insert sample data for testing
INSERT INTO students (name, email, age, mobile) VALUES
('Rahul Sharma', 'rahul@email.com', 21, '9876543210'),
('Priya Patel', 'priya@email.com', 22, '8765432109'),
('Amit Kumar', 'amit@email.com', 20, '7654321098');

-- Verify
SELECT * FROM students;
