package com.example.booking.repository;

import com.example.booking.models.Hotel;
import org.hibernate.dialect.Ingres9Dialect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleHotelRepository extends JpaRepository<Hotel, Integer> {
    @Query(value = "select * from hotel where id = :id ", nativeQuery = true)


    Hotel buscarporid(Integer id);
}
