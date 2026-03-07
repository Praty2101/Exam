package com.customer.app;

import com.customer.dao.CustomerDAO;
import com.customer.dao.CustomerDAOImpl;
import com.customer.dao.OrderDAO;
import com.customer.dao.OrderDAOImpl;
import com.customer.entity.Customer;
import com.customer.entity.Order;
import com.customer.util.JPAUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main Application - Console-based Customer-Order Management System.
 * Covers all 4 questions:
 *   Q1: Entity Classes (Customer + Order with One-to-One mapping)
 *   Q2: DAO Interfaces (CustomerDAO + OrderDAO)
 *   Q3: CRUD Operations (CustomerDAOImpl + OrderDAOImpl)
 *   Q4: JPQL Query (Fetch Customer by Email)
 */
public class MainApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerDAO customerDAO = new CustomerDAOImpl();
    private static final OrderDAO orderDAO = new OrderDAOImpl();

    // ========================
    // MAIN METHOD
    // ========================

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  CUSTOMER-ORDER MANAGEMENT SYSTEM (Hibernate JPA)         ║");
        System.out.println("║  Assignment 3: One-to-One Mapping, CRUD, JPQL             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> customerCrudMenu();
                case 2 -> orderCrudMenu();
                case 3 -> jpqlQueryMenu();
                case 0 -> {
                    running = false;
                    System.out.println("\n🔒 Shutting down... Goodbye!");
                }
                default -> System.out.println("⚠️ Invalid choice! Please try again.");
            }
        }

        JPAUtil.shutdown();
        scanner.close();
    }

    // ========================
    // MAIN MENU
    // ========================

    private static void printMainMenu() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│              MAIN MENU                   │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Customer CRUD Operations     (Q3)   │");
        System.out.println("│  2. Order CRUD Operations        (Q3)   │");
        System.out.println("│  3. JPQL Query                   (Q4)   │");
        System.out.println("│  0. Exit                                │");
        System.out.println("└──────────────────────────────────────────┘");
        System.out.println("  [Q1: Entities defined | Q2: DAO Interfaces]");
    }

    // ================================================================
    //         Q3: CUSTOMER CRUD OPERATIONS
    // ================================================================

    private static void customerCrudMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────┐");
            System.out.println("│      CUSTOMER CRUD OPERATIONS            │");
            System.out.println("├──────────────────────────────────────────┤");
            System.out.println("│  1. Insert Customer with Order           │");
            System.out.println("│  2. Update Customer Details              │");
            System.out.println("│  3. Delete Customer by ID                │");
            System.out.println("│  4. Fetch Customer by ID                 │");
            System.out.println("│  5. Fetch All Customers                  │");
            System.out.println("│  0. Back to Main Menu                    │");
            System.out.println("└──────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> insertCustomerWithOrder();
                case 2 -> updateCustomer();
                case 3 -> deleteCustomer();
                case 4 -> fetchCustomerById();
                case 5 -> fetchAllCustomers();
                case 0 -> back = true;
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    /**
     * Q3: Insert a new customer with order.
     */
    private static void insertCustomerWithOrder() {
        System.out.println("\n--- Insert Customer with Order ---");

        // Customer details
        String name = readString("Customer Name: ");
        String email = readString("Email: ");
        String gender = readString("Gender (M/F/Other): ");
        long phone = readLong("Phone: ");
        LocalDate regDate = readDate("Registration Date (YYYY-MM-DD): ");

        Customer customer = new Customer(name, email, gender, phone, regDate);

        // Order details
        System.out.println("\n  --- Order Details ---");
        String orderNumber = readString("Order Number: ");
        String productName = readString("Product Name: ");
        int quantity = readInt("Quantity: ");
        double price = readDouble("Price: ");
        LocalDate orderDate = readDate("Order Date (YYYY-MM-DD): ");

        Order order = new Order(orderNumber, productName, quantity, price, orderDate);

        // Set bidirectional relationship
        customer.setOrder(order);

        String result = customerDAO.saveCustomer(customer);
        System.out.println(result);
    }

    /**
     * Q3: Update customer details.
     */
    private static void updateCustomer() {
        System.out.println("\n--- Update Customer ---");
        int id = readInt("Enter Customer ID: ");

        Customer existing = customerDAO.getCustomerById(id);
        if (existing == null) return;

        System.out.println("Current details:\n" + existing.toDetailString());

        String name = readString("New Name (current: " + existing.getCustomerName() + "): ");
        String email = readString("New Email (current: " + existing.getEmail() + "): ");
        String gender = readString("New Gender (current: " + existing.getGender() + "): ");
        long phone = readLong("New Phone (current: " + existing.getPhone() + "): ");
        LocalDate regDate = readDate("New Registration Date (YYYY-MM-DD, current: "
            + existing.getRegistrationDate() + "): ");

        Customer updated = new Customer();
        updated.setId(id);
        updated.setCustomerName(name.isEmpty() ? existing.getCustomerName() : name);
        updated.setEmail(email.isEmpty() ? existing.getEmail() : email);
        updated.setGender(gender.isEmpty() ? existing.getGender() : gender);
        updated.setPhone(phone > 0 ? phone : existing.getPhone());
        updated.setRegistrationDate(regDate != null ? regDate : existing.getRegistrationDate());

        String result = customerDAO.updateCustomer(updated);
        System.out.println(result);
    }

    /**
     * Q3: Delete customer by id.
     */
    private static void deleteCustomer() {
        System.out.println("\n--- Delete Customer ---");
        int id = readInt("Enter Customer ID to delete: ");
        String result = customerDAO.deleteCustomerById(id);
        System.out.println(result);
    }

    /**
     * Q3: Fetch customer by id.
     */
    private static void fetchCustomerById() {
        System.out.println("\n--- Fetch Customer by ID ---");
        int id = readInt("Enter Customer ID: ");
        Customer customer = customerDAO.getCustomerById(id);
        if (customer != null) {
            System.out.println(customer.toDetailString());
        }
    }

    /**
     * Q3: Fetch all customers.
     */
    private static void fetchAllCustomers() {
        System.out.println("\n--- All Customers ---");
        List<Customer> customers = customerDAO.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("⚠️ No customers found.");
            return;
        }

        System.out.printf("| %-4s | %-20s | %-25s | %-8s | %-12s | %-12s |%n",
            "ID", "Name", "Email", "Gender", "Phone", "Reg. Date");
        System.out.println("-".repeat(100));
        for (Customer c : customers) {
            System.out.println(c);
        }
        System.out.println("\nTotal customers: " + customers.size());
    }

    // ================================================================
    //          Q3: ORDER CRUD OPERATIONS
    // ================================================================

    private static void orderCrudMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────┐");
            System.out.println("│       ORDER CRUD OPERATIONS              │");
            System.out.println("├──────────────────────────────────────────┤");
            System.out.println("│  1. Update Order Details                 │");
            System.out.println("│  2. Fetch Order by ID                    │");
            System.out.println("│  3. Delete Order by ID                   │");
            System.out.println("│  0. Back to Main Menu                    │");
            System.out.println("└──────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> updateOrder();
                case 2 -> fetchOrderById();
                case 3 -> deleteOrder();
                case 0 -> back = true;
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    /**
     * Q3: Update order details and fetch order by id.
     */
    private static void updateOrder() {
        System.out.println("\n--- Update Order ---");
        int id = readInt("Enter Order ID: ");

        Order existing = orderDAO.getOrderById(id);
        if (existing == null) return;

        System.out.println("Current details:\n" + existing.toDetailString());

        String orderNumber = readString("New Order Number (current: " + existing.getOrderNumber() + "): ");
        String productName = readString("New Product Name (current: " + existing.getProductName() + "): ");
        int quantity = readInt("New Quantity (current: " + existing.getQuantity() + "): ");
        double price = readDouble("New Price (current: " + existing.getPrice() + "): ");
        LocalDate orderDate = readDate("New Order Date (YYYY-MM-DD, current: "
            + existing.getOrderDate() + "): ");

        Order updated = new Order();
        updated.setId(id);
        updated.setOrderNumber(orderNumber.isEmpty() ? existing.getOrderNumber() : orderNumber);
        updated.setProductName(productName.isEmpty() ? existing.getProductName() : productName);
        updated.setQuantity(quantity > 0 ? quantity : existing.getQuantity());
        updated.setPrice(price > 0 ? price : existing.getPrice());
        updated.setOrderDate(orderDate != null ? orderDate : existing.getOrderDate());

        String result = orderDAO.updateOrder(updated);
        System.out.println(result);
    }

    /**
     * Q3: Fetch order by id.
     */
    private static void fetchOrderById() {
        System.out.println("\n--- Fetch Order by ID ---");
        int id = readInt("Enter Order ID: ");
        Order order = orderDAO.getOrderById(id);
        if (order != null) {
            System.out.println(order.toDetailString());
        }
    }

    /**
     * Q3: Delete order by id.
     */
    private static void deleteOrder() {
        System.out.println("\n--- Delete Order ---");
        int id = readInt("Enter Order ID to delete: ");
        String result = orderDAO.deleteOrderById(id);
        System.out.println(result);
    }

    // ================================================================
    //              Q4: JPQL QUERY
    // ================================================================

    private static void jpqlQueryMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n┌──────────────────────────────────────────────────┐");
            System.out.println("│              JPQL QUERIES (Q4)                   │");
            System.out.println("├──────────────────────────────────────────────────┤");
            System.out.println("│  1. Fetch Customer by Email                      │");
            System.out.println("│  0. Back to Main Menu                            │");
            System.out.println("└──────────────────────────────────────────────────┘");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> fetchCustomerByEmail();
                case 0 -> back = true;
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    /**
     * Q4: JPQL Query - Fetch Customer by Email.
     */
    private static void fetchCustomerByEmail() {
        System.out.println("\n--- Fetch Customer by Email (JPQL) ---");
        String email = readString("Enter Email: ");

        Customer customer = customerDAO.getCustomerByEmail(email);
        if (customer != null) {
            System.out.println(customer.toDetailString());
        }
    }

    // ========================
    // INPUT UTILITY METHODS
    // ========================

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid integer.");
            }
        }
    }

    private static long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) return 0;
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) return 0;
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid decimal number.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) return null;
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
}
