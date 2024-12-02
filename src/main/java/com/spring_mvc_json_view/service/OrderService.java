package com.spring_mvc_json_view.service;

import com.spring_mvc_json_view.entity.Order;
import com.spring_mvc_json_view.entity.User;
import com.spring_mvc_json_view.exception.OrderNotFoundException;
import com.spring_mvc_json_view.exception.UserNotFoundException;
import com.spring_mvc_json_view.repository.OrderRepository;
import com.spring_mvc_json_view.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        return orderRepository.findById(id);
    }

    public Order createOrder(Long userId, Order order) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        order.setUser(user);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setProduct(updatedOrder.getProduct());
            existingOrder.setAmount(updatedOrder.getAmount());
            existingOrder.setStatus(updatedOrder.getStatus());
            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundException("Order not found");
        }
    }
}
