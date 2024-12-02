package com.spring_mvc_json_view.repository;

import com.spring_mvc_json_view.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
