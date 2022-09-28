package com.example.booking.repository;

import com.example.booking.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository  extends JpaRepository<Reserva,Integer> {
}
