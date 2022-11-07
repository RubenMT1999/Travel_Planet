package com.example.booking.repository;

import com.example.booking.models.PensionHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPensionRepository extends JpaRepository<PensionHotel,Integer> {

    @Query(value = "SELECT p.precio from PensionHotel p where p.pension=?1")
    Double precioPension(Integer pension);

}
