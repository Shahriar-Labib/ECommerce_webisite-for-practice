package com.OnlineCart.service;

import com.OnlineCart.model.OrderRequset;
import com.OnlineCart.model.ProductOrder;
import org.hibernate.query.Order;

import java.util.List;

public interface OrderService {

    public void saveOrder(Integer userId, OrderRequset orderRequset);

    public List<ProductOrder> getOrdersByUser(Integer userId);

    public Boolean updateOrderStatus(Integer id,String status);

    public List<ProductOrder> getAllOrders();
}
