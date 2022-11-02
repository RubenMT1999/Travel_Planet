package com.example.booking.repository;

import com.example.booking.models.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository  extends JpaRepository<Tarifa,Integer> {
    @Query(value = "insert into tarifa (precio_baño, precio_caja_fuerte, precio_cocina, preciotv,precio_terraza, precio_wifi, precio_aire, precio_banio, id_hotel) values (?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true )
    void guardarPersonalizado(@Param("id_hotel") Integer idHotel, @Param("num_habitacion") Integer numHab,
                              @Param("ext_telefonica") String extTelefonica, @Param("capacidad") Integer capacidad
            , @Param("imagen") String imagen, @Param("descripcion") String descripcion, @Param("precioBase") Double precioBase);

}
