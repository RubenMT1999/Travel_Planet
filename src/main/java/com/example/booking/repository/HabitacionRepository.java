package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,Integer> {


    @Query("SELECT h FROM Habitacion h where h.hotel.id = ?1")
    List<Habitacion> listarHabitaciones(Integer id);

    @Query(value = "select * from vistahabitacion where id_hotel = :id and capacidad = :capacidad and ((fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin) or (fecha_inicio is null and fecha_fin is null)) group by id", nativeQuery = true)

    List<Habitacion> buscarporidhab(Integer id, Integer capacidad, Date fecha_inicio, Date fecha_fin);


}
