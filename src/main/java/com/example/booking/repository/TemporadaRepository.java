package com.example.booking.repository;

import com.example.booking.models.TemporadaHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporadaRepository extends JpaRepository<TemporadaHotel, Integer> {

}
