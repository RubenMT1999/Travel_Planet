package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleHabitacionRepository extends JpaRepository<Habitacion, Integer>{

        @Query(value = "select * from habitacion where id_hotel = :id_hotel ", nativeQuery = true)

        List<Habitacion> buscarporidhab(Integer id_hotel);

}
