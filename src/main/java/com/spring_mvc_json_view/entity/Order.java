package com.spring_mvc_json_view.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonView(Views.UserDetails.class)
    @Column(name = "product")
    private String product;

    @JsonView(Views.UserDetails.class)
    @Column(name = "amount")
    private Double amount;

    @JsonView(Views.UserDetails.class)
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}