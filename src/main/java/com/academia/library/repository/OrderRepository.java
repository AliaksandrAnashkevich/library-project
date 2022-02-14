package com.academia.library.repository;

import com.academia.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query(value = "DELETE FROM orders o where o.deleted = true"
            , nativeQuery = true)
    void deleteAllByDeleted();
}
