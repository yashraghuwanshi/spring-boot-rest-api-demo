package com.example.repository;

import com.example.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false")
    List<Employee> findAllNonDeleted();

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.isDeleted = true WHERE e.id = ?1")
    void softDelete(Integer id);
}
