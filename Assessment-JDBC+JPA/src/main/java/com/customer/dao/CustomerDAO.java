package com.customer.dao;

import com.customer.entity.Customer;
import java.util.List;

/**
 * Q2: DAO Interface for Customer entity.
 * Defines CRUD operations and JPQL query methods.
 */
public interface CustomerDAO {

    String saveCustomer(Customer customer);

    String updateCustomer(Customer customer);

    String deleteCustomerById(int id);

    Customer getCustomerById(int id);

    List<Customer> getAllCustomers();

    /**
     * Q4: JPQL query to fetch Customer by email.
     */
    Customer getCustomerByEmail(String email);
}
