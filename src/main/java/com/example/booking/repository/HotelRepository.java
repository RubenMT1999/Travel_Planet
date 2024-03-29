package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.Hotel;
import com.example.booking.models.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    @Query(value = " select h from Hotel h order by h.id", nativeQuery = true)
    List<Hotel> obtenerHoteles(String nombre);

    @Query(value = "select * from vistabuscador where ciudad = :ciudad and capacidad = :capacidad" +
            " and (fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin or fecha_inicio is null and fecha_fin is null) and disponibilidad = 1 group by nombre ", nativeQuery = true)

    List<Hotel> buscador(String ciudad, Date fecha_inicio, Date fecha_fin, Integer capacidad);

    @Query(value = "select h.* from Hotel h " +
            "join Habitacion h2 on h2.id_hotel = h.id " +
            "join Reserva r on r.id_habitacion = h2.id " +
            "where h.ciudad = :ciudad and h2.capacidad = :capacidad" +
            " and (r.fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and r.fecha_fin not between :fecha_inicio and :fecha_fin or r.fecha_inicio is null and r.fecha_fin is null) and h2.disponibilidad = 1 group by h.nombre ", nativeQuery = true)

    List<Hotel> buscadortest(String ciudad, Date fecha_inicio, Date fecha_fin, Integer capacidad);

    @Query(value = "select * from Hotel h", nativeQuery = true)
    List<Hotel> obtenertodoshoteles();






    @Query(value = "SELECT precio_base FROM vistafiltro h where id = :id_hotel order by precio_base asc limit 1", nativeQuery = true)
    Double preciofiltro(Integer id_hotel);

    @Query(value = "UPDATE hotel set precio = :precio where id = :id", nativeQuery = true)
    Double preciobase(Double precio, Integer id);

    @Query(value = "UPDATE hotel set precio = :precio where id = :id", nativeQuery = true)
    Double preciobasefiltro(Double precio, Integer id);


    @Query(value = "select * from vistafiltro where ciudad = :ciudad and capacidad = :capacidad" +
            " and (tv in :tv and aire_acondicionado in :aire and banio_privado in :banio_privado and cocina in :cocina and" +
            " caja_fuerte in :caja_fuerte and wifi in :wifi and terraza in :terraza and precio_base between :preciobase and :precioFinal " +
            "and estrellas between :puntuacion and :estrella)" +
            " and (fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin or fecha_inicio is null and fecha_fin is null) and disponibilidad = 1" +
            " group by nombre", nativeQuery = true)

    List<Hotel>buscarfiltros(String ciudad, Date fecha_inicio, Date fecha_fin, Integer capacidad, List<Integer> wifi,
                             List<Integer> terraza,List<Integer> tv, List<Integer> aire, List<Integer> banio_privado, List<Integer> cocina,
                             List<Integer> caja_fuerte, Double preciobase, Integer precioFinal, Integer puntuacion, Double estrella);

     @Query("SELECT h FROM Habitacion h where h.hotel.usuario.nombre = ?1")
    List<Habitacion> listarHabitaciones();


    @Query(value = "select * from hotel where id = :id_hotel", nativeQuery = true)
    Hotel obtenerHotelId(Integer id_hotel);



    @Query("select h from Hotel h where h.ciudad = ?1")
        List<Hotel> hotelesPorCiudad(String ciudad);

    @Query("SELECT r FROM Hotel r WHERE r.usuario.nombre= ?1")
    List<Hotel> hotelPorNombre(String nombre);

    @Query("SELECT r FROM Hotel r WHERE r.usuario.email= ?1")
    List<Hotel> hotelPorMail(String nombre);

    @Transactional
    @Modifying
    @Query(value = "insert into hotel (nombre, puntuacion, precio, comentario,imagen, lugar, telefono, cif, num_hab, ciudad, id_usuario) values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)",nativeQuery = true )
    void crearHotel(@Param("nombre") String nombre, @Param("estrellas") Integer estrellas,
                    @Param("comentario") String comentario, @Param("imagen") String imagen
            , @Param("lugar") String lugar, @Param("telefono") String telefono, @Param("cif") String cif, String s, @Param("num_hab") Integer num_hab, @Param("ciudad") String ciudad, @Param("id_usuario") Integer id_usuario);

    @Query(value = "select r.imagen from Hotel r where r.id= ?1")
    String imagenHotel(Integer id);



    @Query(value = "select r from Hotel r where r.usuario.id = ?1")
    List<Hotel> obtenerHoteldeUsuario(Integer id);

//    @Query(value = "select * from hotel where id_usuario = :id_usuario", nativeQuery = true)
//    List<Hotel> obtenerHoteldeUsuario(Integer id_usuario);





}
