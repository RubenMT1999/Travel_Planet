package com.example.booking.repository;

import com.example.booking.models.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TarifaRepository  extends JpaRepository<Tarifa,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into tarifa (precio_banio, precio_caja_fuerte, precio_cocina, preciotv, precio_terraza, precio_wifi, precio_aire, id_hotel) values (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true )
    void guardarTarifa(@Param("precio_banio") Double precioBanio, @Param("precio_caja_fuerte") Double precioCajaFuerte,
                              @Param("precio_cocina") Double precioCocina, @Param("preciotv") Double precioTV
            , @Param("precio_terraza") Double precioTerraza, @Param("precio_wifi") Double precioWifi, @Param("precio_aire") Double precioAire, @Param("id_hotel") Integer id_hotel);

}
