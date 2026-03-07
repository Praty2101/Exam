package com.customer.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Q1: Entity class mapped to the 'orders' table.
 * Has a One-to-One relationship with Customer.
 * Order is the owning side of the relationship (has the FK column).
 *
 * Note: Table name is 'orders' because 'order' is a reserved keyword in SQL.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "order_date")
    private LocalDate orderDate;

    /**
     * One-to-One mapping with Customer.
     * This is the owning side of the relationship.
     * @JoinColumn creates a foreign key column 'customer_id' in the orders table.
     */
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    // ========================
    // Constructors
    // ========================

    public Order() {
    }

    public Order(String orderNumber, String productName, int quantity,
                 double price, LocalDate orderDate) {
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // ========================
    // toString
    // ========================

    @Override
    public String toString() {
        return String.format(
            "| %-4d | %-15s | %-20s | %-8d | %10.2f | %-12s |",
            id, orderNumber, productName, quantity, price, orderDate
        );
    }

    public String toDetailString() {
        return "--------------------------------------------\n" +
               "  Order ID      : " + id + "\n" +
               "  Order Number  : " + orderNumber + "\n" +
               "  Product Name  : " + productName + "\n" +
               "  Quantity      : " + quantity + "\n" +
               "  Price         : " + String.format("%.2f", price) + "\n" +
               "  Order Date    : " + orderDate + "\n" +
               "--------------------------------------------";
    }
}
