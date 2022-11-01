package com.rentcar.repository;

import com.rentcar.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Car, Long> {
}
