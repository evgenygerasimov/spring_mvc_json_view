package com.spring_mvc_json_view.service;

import com.spring_mvc_json_view.entity.Order;
import com.spring_mvc_json_view.entity.User;
import com.spring_mvc_json_view.repository.OrderRepository;
import com.spring_mvc_json_view.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order = new Order();
        order.setId(1L);
        order.setProduct("Laptop");
        order.setAmount(2000D);

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
    }

    @Test
    void shouldReturnAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("Laptop", orders.get(0).getProduct());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOrderByIdTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getProduct());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateOrderTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(1L, order);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getProduct());
        assertEquals("John Doe", result.getUser().getName());
        verify(userRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowExceptionWhenCreateOrderAndUserNotFoundTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(1L, order));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldUpdateOrderTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        order.setProduct("Smartphone");
        order.setAmount(5000D);
        order.setStatus("Shipped");
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.updateOrder(1L, order);

        assertNotNull(result);
        assertEquals("Smartphone", result.getProduct());
        assertEquals(5000, result.getAmount());
        assertEquals("Shipped", result.getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowExceptionWhenUpdateOrderNotFoundTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.updateOrder(1L, order));

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void shouldDeleteOrderTest() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeleteOrderNotFoundTest() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.deleteOrder(1L));

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, never()).deleteById(anyLong());
    }
}
