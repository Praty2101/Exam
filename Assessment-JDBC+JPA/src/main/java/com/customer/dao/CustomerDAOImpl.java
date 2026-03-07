package com.customer.dao;

import com.customer.entity.Customer;
import com.customer.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Q3: Implementation of CustomerDAO interface.
 * Performs CRUD operations using JPA EntityManager.
 * All operations use proper transaction management.
 *
 * Q4: Contains JPQL query to fetch Customer by email.
 */
public class CustomerDAOImpl implements CustomerDAO {

    // ================================================================
    //              Q3: CRUD OPERATIONS
    // ================================================================

    /**
     * Saves a new customer (with cascaded order if set).
     * Returns a success/failure message.
     */
    @Override
    public String saveCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(customer);
            transaction.commit();
            return "✅ Customer saved successfully! Customer ID: " + customer.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return "❌ Error saving customer: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing customer's details.
     * The customer object should have the ID set.
     */
    @Override
    public String updateCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Customer existing = em.find(Customer.class, customer.getId());
            if (existing == null) {
                transaction.rollback();
                return "⚠️ No customer found with ID: " + customer.getId();
            }

            existing.setCustomerName(customer.getCustomerName());
            existing.setEmail(customer.getEmail());
            existing.setGender(customer.getGender());
            existing.setPhone(customer.getPhone());
            existing.setRegistrationDate(customer.getRegistrationDate());

            em.merge(existing);
            transaction.commit();
            return "✅ Customer updated successfully!";
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return "❌ Error updating customer: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes a customer by ID.
     * Order is deleted automatically due to CascadeType.ALL + orphanRemoval.
     */
    @Override
    public String deleteCustomerById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                transaction.rollback();
                return "⚠️ No customer found with ID: " + id;
            }

            em.remove(customer);
            transaction.commit();
            return "✅ Customer deleted! (Order also deleted automatically via cascade)";
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return "❌ Error deleting customer: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    /**
     * Fetches a customer by ID (with order via EAGER fetch).
     */
    @Override
    public Customer getCustomerById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                System.out.println("⚠️ No customer found with ID: " + id);
            }
            return customer;
        } finally {
            em.close();
        }
    }

    /**
     * Fetches all customers.
     */
    @Override
    public List<Customer> getAllCustomers() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // ================================================================
    //      Q4: JPQL QUERY - Fetch Customer by Email
    // ================================================================

    /**
     * JPQL query to fetch Customer by email address.
     * Uses a parameterized query for safety.
     */
    @Override
    public Customer getCustomerByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email", Customer.class);
            query.setParameter("email", email);

            List<Customer> results = query.getResultList();
            if (results.isEmpty()) {
                System.out.println("⚠️ No customer found with email: " + email);
                return null;
            }
            return results.get(0);
        } finally {
            em.close();
        }
    }
}
