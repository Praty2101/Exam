package com.customer.dao;

import com.customer.entity.Order;
import com.customer.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Q3: Implementation of OrderDAO interface.
 * Performs CRUD operations for Order entity.
 * All operations use proper transaction management.
 */
public class OrderDAOImpl implements OrderDAO {

    /**
     * Saves a new order.
     * Note: Typically orders are saved via cascade when saving a Customer.
     * This method allows standalone order creation if customer is already set.
     */
    @Override
    public String saveOrder(Order order) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(order);
            transaction.commit();
            return "✅ Order saved successfully! Order ID: " + order.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return "❌ Error saving order: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing order's details.
     */
    @Override
    public String updateOrder(Order order) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Order existing = em.find(Order.class, order.getId());
            if (existing == null) {
                transaction.rollback();
                return "⚠️ No order found with ID: " + order.getId();
            }

            existing.setOrderNumber(order.getOrderNumber());
            existing.setProductName(order.getProductName());
            existing.setQuantity(order.getQuantity());
            existing.setPrice(order.getPrice());
            existing.setOrderDate(order.getOrderDate());

            em.merge(existing);
            transaction.commit();
            return "✅ Order updated successfully!";
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return "❌ Error updating order: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes an order by ID.
     */
    @Override
    public String deleteOrderById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Order order = em.find(Order.class, id);
            if (order == null) {
                transaction.rollback();
                return "⚠️ No order found with ID: " + id;
            }

            em.remove(order);
            transaction.commit();
            return "✅ Order deleted successfully!";
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return "❌ Error deleting order: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    /**
     * Fetches an order by ID.
     */
    @Override
    public Order getOrderById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            Order order = em.find(Order.class, id);
            if (order == null) {
                System.out.println("⚠️ No order found with ID: " + id);
            }
            return order;
        } finally {
            em.close();
        }
    }
}
