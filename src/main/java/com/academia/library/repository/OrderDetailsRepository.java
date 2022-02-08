package com.academia.library.repository;

import com.academia.library.model.Order;
import com.academia.library.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

    @Modifying
    @Query("update OrderDetail set deleted = true WHERE order=:order")
    void deleteByOrder(@Param("order") Order order);
}
