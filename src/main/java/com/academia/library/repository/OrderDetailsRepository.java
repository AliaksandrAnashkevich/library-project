package com.academia.library.repository;

import com.academia.library.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

    @Modifying
    @Query(value = "DELETE FROM order_details o where o.deleted = true"
            , nativeQuery = true)
    void deleteAllByDeleted();
}
