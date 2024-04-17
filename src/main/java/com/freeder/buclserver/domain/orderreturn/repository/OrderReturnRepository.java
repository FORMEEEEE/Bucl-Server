package com.freeder.buclserver.domain.orderreturn.repository;

import com.freeder.buclserver.domain.orderreturn.entity.OrderReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {
}
