package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
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

   /*
    @Query(value = " select h from hotel h order by h.id")
    List<Hotel> obtenerHoteles(String nombre);


    @Query(value = "select * from vistabuscador where ciudad = :ciudad " +
            "and fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin", nativeQuery = true)
    List<Hotel> findHotelsByCiudadLike(String ciudad, Date fecha_inicio, Date fecha_fin);
   // Reserva buscar(Date fecha_inicio, Date fecha_fin);

     @Query("SELECT h FROM Habitacion h where h.hotel.usuario.nombre = ?1")
    List<Habitacion> listarHabitaciones();
*/


    @Query("select h from Hotel h where h.ciudad = ?1")
        List<Hotel> hotelesPorCiudad(String ciudad);


}
