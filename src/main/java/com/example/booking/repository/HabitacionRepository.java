package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,Integer> {


    @Query("SELECT h FROM Habitacion h where h.hotel.id = ?1")
    List<Habitacion> listarHabitaciones(Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into habitacion (id_hotel, num_habitacion, ext_telefonica, capacidad,imagen, descripcion, precioBase) values (?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true )
    void guardarPersonalizado(@Param("id_hotel") Integer idHotel, @Param("num_habitacion") Integer numHab,
                    @Param("ext_telefonica") String extTelefonica, @Param("capacidad") Integer capacidad
                    ,@Param("imagen") String imagen,@Param("descripcion") String descripcion, @Param("precioBase") Integer precioBase);

    @Query(value = "select * from vistahabitacion where id_hotel = :id and capacidad = :capacidad and ((fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin) or (fecha_inicio is null and fecha_fin is null)) group by id", nativeQuery = true)

    List<Habitacion> buscarporidhab(Integer id, Integer capacidad, Date fecha_inicio, Date fecha_fin);


    @Query(value = "SELECT id_hotel from Habitacion ", nativeQuery = true)
    List<Integer> totalIdHotelesHabitacion ();

    @Query(value = "select * from habitacion", nativeQuery = true)
    List<Habitacion> obtenertodaslashabitaciones();
}
