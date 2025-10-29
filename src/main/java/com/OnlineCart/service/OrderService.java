package com.OnlineCart.service;

import com.OnlineCart.model.OrderRequset;
import com.OnlineCart.model.ProductOrder;

public interface OrderService {

    public void saveOrder(Integer userId, OrderRequset orderRequset);
}
