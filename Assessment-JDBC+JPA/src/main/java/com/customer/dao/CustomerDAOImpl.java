package com.customer.dao;

import com.customer.entity.Customer;
import com.customer.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public String saveCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(customer);
            tx.commit();
            return "Customer saved. ID: " + customer.getId();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return "Error saving customer: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    @Override
    public String updateCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Customer existing = em.find(Customer.class, customer.getId());
            if (existing == null) {
                tx.rollback();
                return "No customer found with ID: " + customer.getId();
            }
            existing.setCustomerName(customer.getCustomerName());
            existing.setEmail(customer.getEmail());
            existing.setGender(customer.getGender());
            existing.setPhone(customer.getPhone());
            existing.setRegistrationDate(customer.getRegistrationDate());
            em.merge(existing);
            tx.commit();
            return "Customer updated successfully.";
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return "Error updating customer: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    @Override
    public String deleteCustomerById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Customer customer = em.find(Customer.class, id);
            if (customer == null) {
                tx.rollback();
                return "No customer found with ID: " + id;
            }
            em.remove(customer);
            tx.commit();
            return "Customer deleted. (Order also deleted via cascade)";
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return "Error deleting customer: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email", Customer.class);
            query.setParameter("email", email);
            List<Customer> results = query.getResultList();
            if (results.isEmpty()) return null;
            return results.get(0);
        } finally {
            em.close();
        }
    }
}
