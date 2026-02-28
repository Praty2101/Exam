CREATE DATABASE IF NOT EXISTS studentdb;
USE studentdb;
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    mobile VARCHAR(15) NOT NULL
);
INSERT INTO students (name, email, age, mobile) VALUES
('Rahul Sharma', 'rahul@email.com', 21, '9876543210'),
('Priya Patel', 'priya@email.com', 22, '8765432109'),
('Amit Kumar', 'amit@email.com', 20, '7654321098');
SELECT * FROM students;
