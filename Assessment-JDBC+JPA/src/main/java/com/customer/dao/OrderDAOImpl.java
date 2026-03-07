package com.customer.dao;

import com.customer.entity.Order;
import com.customer.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String saveOrder(Order order) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            tx.commit();
            return "Order saved. ID: " + order.getId();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return "Error saving order: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    @Override
    public String updateOrder(Order order) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order existing = em.find(Order.class, order.getId());
            if (existing == null) {
                tx.rollback();
                return "No order found with ID: " + order.getId();
            }
            existing.setOrderNumber(order.getOrderNumber());
            existing.setProductName(order.getProductName());
            existing.setQuantity(order.getQuantity());
            existing.setPrice(order.getPrice());
            existing.setOrderDate(order.getOrderDate());
            em.merge(existing);
            tx.commit();
            return "Order updated successfully.";
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return "Error updating order: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    @Override
    public String deleteOrderById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order order = em.find(Order.class, id);
            if (order == null) {
                tx.rollback();
                return "No order found with ID: " + id;
            }
            em.remove(order);
            tx.commit();
            return "Order deleted successfully.";
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return "Error deleting order: " + e.getMessage();
        } finally {
            em.close();
        }
    }

    @Override
    public Order getOrderById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Order.class, id);
        } finally {
            em.close();
        }
    }
}
