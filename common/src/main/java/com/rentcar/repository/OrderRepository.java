package com.rentcar.repository;

import com.rentcar.domain.Car;
import com.rentcar.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {
}
