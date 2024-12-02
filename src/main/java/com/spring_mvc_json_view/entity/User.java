package com.spring_mvc_json_view.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonView(Views.UserSummary.class)
    @Column(name = "name")
    private String name;

    @JsonView(Views.UserSummary.class)
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @JsonView(Views.UserDetails.class)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}


