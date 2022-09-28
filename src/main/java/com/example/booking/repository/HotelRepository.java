package com.example.booking.repository;

import com.example.booking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    @Query("SELECT hh.ciudad, r.fecha_inicio, r.fecha_fin" +
            "FROM hotel hh" +
            "JOIN habitacion h  ON hh.id = h.id_hotel" +
            "join reserva r on r.id_habitacion = h.id" +
            "where hh.ciudad like %?1% and r.fecha_inicio like %?1% and r.fecha_fin like %?1%")
    public List<Hotel> search(String keyword);

}
