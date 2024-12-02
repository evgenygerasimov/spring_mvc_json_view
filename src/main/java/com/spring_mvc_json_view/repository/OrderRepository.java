package com.spring_mvc_json_view.repository;

import com.spring_mvc_json_view.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
