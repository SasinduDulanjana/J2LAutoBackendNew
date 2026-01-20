package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByPhone(String mobile);

    List<Customer> findByPhoneContaining(String phoneNumber);

    List<Customer> findAllByStatus(Integer status);

    List<Customer> findByNameContaining(String name);

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:nameOrPhone% OR c.phone LIKE %:nameOrPhone%")
    List<Customer> findByNameOrPhone(@Param("nameOrPhone") String nameOrPhone);

}
