package com.student.model;

/**
 * Student model class representing a student entity.
 * Contains all student attributes with proper encapsulation.
 */
public class Student {

    private int id;
    private String name;
    private String email;
    private int age;
    private String mobile;

    // Default constructor
    public Student() {
    }

    // Parameterized constructor (without id - for inserting new student)
    public Student(String name, String email, int age, String mobile) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.mobile = mobile;
    }

    // Parameterized constructor (with id - for fetching/updating)
    public Student(int id, String name, String email, int age, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.mobile = mobile;
    }

    // ---- Getters and Setters ----

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    // toString for displaying student details
    @Override
    public String toString() {
        return "Student [ID=" + id + ", Name=" + name + ", Email=" + email
                + ", Age=" + age + ", Mobile=" + mobile + "]";
    }
}
