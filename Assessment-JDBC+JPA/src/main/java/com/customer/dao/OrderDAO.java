package com.customer.dao;

import com.customer.entity.Order;

/**
 * Q2: DAO Interface for Order entity.
 * Defines CRUD operations for Order.
 */
public interface OrderDAO {

    String saveOrder(Order order);

    String updateOrder(Order order);

    String deleteOrderById(int id);

    Order getOrderById(int id);
}
