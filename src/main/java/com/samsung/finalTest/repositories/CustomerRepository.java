package com.samsung.finalTest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByCustomerNumber(String number);
    Boolean existsByEmail(String email);
}
