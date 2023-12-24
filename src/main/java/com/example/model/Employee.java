package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "hire_date", nullable = false)
    private String hireDate;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private Long phone;

    @Column(name = "email_address", nullable = false, unique = true)
    private String email;

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "password", unique = true)
    private String password;

    @Column(name = "emp_role", nullable = false)
    private String role;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false; // Soft delete flag, default to false

    @PrePersist
    public void prePersist() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.hireDate = localDateTime.format(dateTimeFormatter);
    }

}
