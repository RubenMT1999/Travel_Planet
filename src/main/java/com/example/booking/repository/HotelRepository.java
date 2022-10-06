package com.example.booking.repository;

import com.example.booking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
   @Query(value = "SELECT *" +
            "FROM Hotel " +
            "where hotel.ciudad = :ciudad", nativeQuery = true)
    List<Hotel> findHotelByCiudadLike(String ciudad);

}


