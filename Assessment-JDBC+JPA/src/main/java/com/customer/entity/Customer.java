package com.customer.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Q1: Entity class mapped to the 'customer' table.
 * Has a One-to-One relationship with Order.
 * Customer is the parent/inverse side of the relationship.
 */
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private long phone;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    /**
     * One-to-One mapping with Order.
     * CascadeType.ALL: All operations (persist, merge, remove) cascade to Order.
     * orphanRemoval: When customer is deleted, order is also deleted.
     * mappedBy: Order entity owns the relationship (has the FK column).
     */
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true,
              fetch = FetchType.EAGER)
    private Order order;

    // ========================
    // Constructors
    // ========================

    public Customer() {
    }

    public Customer(String customerName, String email, String gender,
                    long phone, LocalDate registrationDate) {
        this.customerName = customerName;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
        this.registrationDate = registrationDate;
    }

    // ========================
    // Getters and Setters
    // ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Order getOrder() {
        return order;
    }

    /**
     * Sets the order and establishes the bidirectional relationship.
     */
    public void setOrder(Order order) {
        this.order = order;
        if (order != null) {
            order.setCustomer(this);
        }
    }

    // ========================
    // toString
    // ========================

    @Override
    public String toString() {
        return String.format(
            "| %-4d | %-20s | %-25s | %-8s | %-12d | %-12s |",
            id, customerName, email, gender, phone, registrationDate
        );
    }

    public String toDetailString() {
        StringBuilder sb = new StringBuilder();
        sb.append("============================================\n");
        sb.append("  CUSTOMER DETAILS\n");
        sb.append("============================================\n");
        sb.append("  ID                : ").append(id).append("\n");
        sb.append("  Name              : ").append(customerName).append("\n");
        sb.append("  Email             : ").append(email).append("\n");
        sb.append("  Gender            : ").append(gender).append("\n");
        sb.append("  Phone             : ").append(phone).append("\n");
        sb.append("  Registration Date : ").append(registrationDate).append("\n");

        if (order != null) {
            sb.append("  ---------- ORDER ----------\n");
            sb.append("  Order ID          : ").append(order.getId()).append("\n");
            sb.append("  Order Number      : ").append(order.getOrderNumber()).append("\n");
            sb.append("  Product Name      : ").append(order.getProductName()).append("\n");
            sb.append("  Quantity          : ").append(order.getQuantity()).append("\n");
            sb.append("  Price             : ").append(String.format("%.2f", order.getPrice())).append("\n");
            sb.append("  Order Date        : ").append(order.getOrderDate()).append("\n");
        } else {
            sb.append("  Order             : Not assigned\n");
        }

        sb.append("============================================");
        return sb.toString();
    }
}
