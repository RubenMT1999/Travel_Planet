package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion,Integer> {


    @Query("SELECT h FROM Habitacion h where h.hotel.id = ?1")
    List<Habitacion> listarHabitaciones(Integer id);


    @Transactional
    @Modifying
    @Query(value = "insert into habitacion (id_hotel, num_habitacion, ext_telefonica, capacidad,imagen, descripcion, precio_base,caja_fuerte,cocina,banio_privado,aire_acondicionado,tv,terraza,wifi)" +
            " values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14)",nativeQuery = true )
    void guardarPersonalizado(@Param("id_hotel") Integer idHotel, @Param("num_habitacion") Integer numHab,
                              @Param("ext_telefonica") String extTelefonica, @Param("capacidad") Integer capacidad
                    , @Param("imagen") String imagen, @Param("descripcion") String descripcion, @Param("precioBase") Double precioBase,
                              @Param("caja_fuerte") Boolean cajaFuerte, @Param("cocina") Boolean cocina, @Param("banio_privado") Boolean banioPrivado,
                              @Param("aire_acondicionado") Boolean aireAcondicionado, @Param("tv") Boolean tv, @Param("terraza") Boolean terraza,
                              @Param("wifi") Boolean wifi, @Param("disponibilidad") Boolean disponibilidad);


    @Query(value = "select * from vistahabitacion where id_hotel = :id_hotel and capacidad = :capacidad" +
            " and (tv in :tv and aire_acondicionado in :aire and banio_privado in :banio_privado and cocina in :cocina and" +
            " caja_fuerte in :caja_fuerte and wifi in :wifi and terraza in :terraza and precio_base between :preciobase and " +
            ":precioFinal and estrellas between :puntuacion and :estrella)" +
            " and (fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin or fecha_inicio is null and fecha_fin is null) and disponibilidad = 1" +
            " group by id", nativeQuery = true)
    Page<Habitacion> buscarfiltrosid(Integer id_hotel, Date fecha_inicio, Date fecha_fin, Integer capacidad, List<Integer> wifi,
                                     List<Integer> terraza, List<Integer> tv, List<Integer> aire, List<Integer> banio_privado, List<Integer> cocina,
                                     List<Integer> caja_fuerte, Double preciobase, Integer precioFinal, Integer puntuacion, Double estrella, Pageable pageable);


    @Query(value = "select * from vistahabitacion where id_hotel = :id_hotel and capacidad = :capacidad" +
            " and (tv in :tv and aire_acondicionado in :aire and banio_privado in :banio_privado and cocina in :cocina and" +
            " caja_fuerte in :caja_fuerte and wifi in :wifi and terraza in :terraza and precio_base between :preciobase and " +
            ":precioFinal and estrellas between :puntuacion and :estrella)" +
            " and (fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin or fecha_inicio is null and fecha_fin is null) and disponibilidad = 1" +
            " group by id", nativeQuery = true)

    List<Habitacion> buscarfiltrosidprecio(Integer id_hotel, Date fecha_inicio, Date fecha_fin, Integer capacidad, List<Integer> wifi,
                                     List<Integer> terraza, List<Integer> tv, List<Integer> aire, List<Integer> banio_privado, List<Integer> cocina,
                                     List<Integer> caja_fuerte, Double preciobase, Integer precioFinal, Integer puntuacion, Double estrella);
    @Query(value = "select * from vistahabitacion where id_hotel = :id and capacidad = :capacidad and ((fecha_inicio not between :fecha_inicio and :fecha_fin " +
                       "and fecha_fin not between :fecha_inicio and :fecha_fin) or (fecha_inicio is null and fecha_fin is null)) group by id", nativeQuery = true)
    List<Habitacion> buscarHabitacionesrepo(Integer id, Integer capacidad, Date fecha_inicio, Date fecha_fin);
    @Query(value = "SELECT id_hotel from Habitacion ", nativeQuery = true)
    List<Integer> totalIdHotelesHabitacion ();


    @Query(value = "SELECT h.id from Habitacion h where h.numeroHabitacion = ?1 and h.hotel.id = ?2")
    Integer comprobarNumHab(Integer numHab,Integer numHotel);

    @Transactional
    @Modifying
    @Query("UPDATE Habitacion SET disponibilidad = :disponibilidad where id = :id")
    void editarDisponibilidad(Boolean disponibilidad, Integer id);

    @Query("SELECT h FROM Habitacion h WHERE h.id = ?1")
    Habitacion obtenerHabitacionReserva(Integer id);

}
