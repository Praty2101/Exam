package com.customer.app;

import com.customer.dao.*;
import com.customer.entity.Customer;
import com.customer.entity.Order;
import com.customer.util.JPAUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainApplication {

    private static Scanner sc = new Scanner(System.in);
    private static CustomerDAO customerDAO = new CustomerDAOImpl();
    private static OrderDAO orderDAO = new OrderDAOImpl();

    public static void main(String[] args) {
        System.out.println("=== Customer-Order Management System ===");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Customer Operations");
            System.out.println("2. Order Operations");
            System.out.println("3. Search Customer by Email (JPQL)");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());

            switch (ch) {
                case 1 -> customerMenu();
                case 2 -> orderMenu();
                case 3 -> searchByEmail();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }

        JPAUtil.shutdown();
        sc.close();
        System.out.println("Goodbye.");
    }

    private static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Customer Menu --");
            System.out.println("1. Insert Customer with Order");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer by ID");
            System.out.println("4. Get Customer by ID");
            System.out.println("5. Get All Customers");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());

            switch (ch) {
                case 1 -> insertCustomerWithOrder();
                case 2 -> updateCustomer();
                case 3 -> deleteCustomer();
                case 4 -> getCustomerById();
                case 5 -> getAllCustomers();
                case 0 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void insertCustomerWithOrder() {
        System.out.print("Customer Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Gender: ");
        String gender = sc.nextLine().trim();
        System.out.print("Phone: ");
        long phone = Long.parseLong(sc.nextLine().trim());
        System.out.print("Registration Date (YYYY-MM-DD): ");
        LocalDate regDate = LocalDate.parse(sc.nextLine().trim());

        Customer customer = new Customer(name, email, gender, phone, regDate);

        System.out.print("Order Number: ");
        String orderNum = sc.nextLine().trim();
        System.out.print("Product Name: ");
        String product = sc.nextLine().trim();
        System.out.print("Quantity: ");
        int qty = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Order Date (YYYY-MM-DD): ");
        LocalDate orderDate = LocalDate.parse(sc.nextLine().trim());

        Order order = new Order(orderNum, product, qty, price, orderDate);
        customer.setOrder(order);

        System.out.println(customerDAO.saveCustomer(customer));
    }

    private static void updateCustomer() {
        System.out.print("Enter Customer ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Customer existing = customerDAO.getCustomerById(id);
        if (existing == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("Current: " + existing);

        System.out.print("New Name: ");
        String name = sc.nextLine().trim();
        System.out.print("New Email: ");
        String email = sc.nextLine().trim();
        System.out.print("New Gender: ");
        String gender = sc.nextLine().trim();
        System.out.print("New Phone: ");
        long phone = Long.parseLong(sc.nextLine().trim());
        System.out.print("New Registration Date (YYYY-MM-DD): ");
        LocalDate regDate = LocalDate.parse(sc.nextLine().trim());

        Customer updated = new Customer();
        updated.setId(id);
        updated.setCustomerName(name.isEmpty() ? existing.getCustomerName() : name);
        updated.setEmail(email.isEmpty() ? existing.getEmail() : email);
        updated.setGender(gender.isEmpty() ? existing.getGender() : gender);
        updated.setPhone(phone > 0 ? phone : existing.getPhone());
        updated.setRegistrationDate(regDate != null ? regDate : existing.getRegistrationDate());

        System.out.println(customerDAO.updateCustomer(updated));
    }

    private static void deleteCustomer() {
        System.out.print("Enter Customer ID to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        System.out.println(customerDAO.deleteCustomerById(id));
    }

    private static void getCustomerById() {
        System.out.print("Enter Customer ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Customer c = customerDAO.getCustomerById(id);
        if (c != null) {
            System.out.println(c);
            if (c.getOrder() != null) {
                System.out.println("Order: " + c.getOrder());
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void getAllCustomers() {
        List<Customer> list = customerDAO.getAllCustomers();
        if (list.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        for (Customer c : list) {
            System.out.println(c);
        }
        System.out.println("Total: " + list.size());
    }

    private static void orderMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n-- Order Menu --");
            System.out.println("1. Update Order");
            System.out.println("2. Get Order by ID");
            System.out.println("3. Delete Order by ID");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine().trim());

            switch (ch) {
                case 1 -> updateOrder();
                case 2 -> getOrderById();
                case 3 -> deleteOrder();
                case 0 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void updateOrder() {
        System.out.print("Enter Order ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        Order existing = orderDAO.getOrderById(id);
        if (existing == null) {
            System.out.println("Order not found.");
            return;
        }
        System.out.println("Current: " + existing);

        System.out.print("New Order Number: ");
        String orderNum = sc.nextLine().trim();
        System.out.print("New Product Name: ");
        String product = sc.nextLine().trim();
        System.out.print("New Quantity: ");
        int qty = Integer.parseInt(sc.nextLine().trim());
        System.out.print("New Price: ");
        double price = Double.parseDouble(sc.nextLine().trim());
        System.out.print("New Order Date (YYYY-MM-DD): ");
        LocalDate orderDate = LocalDate.parse(sc.nextLine().trim());

        Order updated = new Order();
        updated.setId(id);
        updated.setOrderNumber(orderNum.isEmpty() ? existing.getOrderNumber() : orderNum);
        updated.setProductName(product.isEmpty() ? existing.getProductName() : product);
        updated.setQuantity(qty > 0 ? qty : existing.getQuantity());
        updated.setPrice(price > 0 ? price : existing.getPrice());
        updated.setOrderDate(orderDate != null ? orderDate : existing.getOrderDate());

        System.out.println(orderDAO.updateOrder(updated));
    }

    private static void getOrderById() {
        System.out.print("Enter Order ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Order o = orderDAO.getOrderById(id);
        if (o != null) {
            System.out.println(o);
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void deleteOrder() {
        System.out.print("Enter Order ID to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        System.out.println(orderDAO.deleteOrderById(id));
    }

    private static void searchByEmail() {
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        Customer c = customerDAO.getCustomerByEmail(email);
        if (c != null) {
            System.out.println(c);
            if (c.getOrder() != null) {
                System.out.println("Order: " + c.getOrder());
            }
        } else {
            System.out.println("No customer found with that email.");
        }
    }
}
