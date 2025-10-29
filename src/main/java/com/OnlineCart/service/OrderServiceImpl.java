package com.OnlineCart.service;

import com.OnlineCart.Utils.OrderStatus;
import com.OnlineCart.model.Cart;
import com.OnlineCart.model.OrderAddress;
import com.OnlineCart.model.OrderRequset;
import com.OnlineCart.model.ProductOrder;
import com.OnlineCart.repository.CartRepository;
import com.OnlineCart.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void saveOrder(Integer userId, OrderRequset orderRequset) {

       List<Cart> carts = cartRepository.findByUserId(userId);

       for(Cart cart:carts)
       {
           ProductOrder order = new ProductOrder();
           order.setOrderId(UUID.randomUUID().toString());
           order.setOrderDate(new Date());
           order.setProduct(cart.getProduct());
           order.setPrice(cart.getProduct().getDiscountPrice());
           order.setQuantity(cart.getQuantity());
           order.setUser(cart.getUser());

           order.setStatus(OrderStatus.IN_PROGRESS.getName());
           order.setPaymentType(orderRequset.getPaymentType());

           OrderAddress address = new OrderAddress();
           address.setFirstName(orderRequset.getFirstName());
           address.setLastName(orderRequset.getLastName());
           address.setEmail(orderRequset.getEmail());
           address.setMobileNo(orderRequset.getMobileNo());
           address.setAddress(orderRequset.getAddress());
           address.setCity(orderRequset.getCity());
           address.setState(orderRequset.getState());
           address.setPincode(orderRequset.getPincode());

           order.setOrderAddress(address);

           productOrderRepository.save(order);
       }


    }
}
