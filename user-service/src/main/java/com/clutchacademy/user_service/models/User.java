package com.clutchacademy.user_service.models;

import com.clutchacademy.user_service.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private String email;

    @Transient
    private UserType type;
}
