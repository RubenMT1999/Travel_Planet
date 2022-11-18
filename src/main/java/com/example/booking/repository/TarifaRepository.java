package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import com.example.booking.models.PensionHotel;
import com.example.booking.models.Tarifa;
import com.example.booking.models.TemporadaHotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Repository
public interface TarifaRepository  extends JpaRepository<Tarifa,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into tarifa (precio_banio, precio_caja_fuerte, precio_cocina, preciotv, precio_terraza, precio_wifi, precio_aire, id_hotel) values (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true )
    void guardarTarifa(@Param("precio_banio") Double precioBanio, @Param("precio_caja_fuerte") Double precioCajaFuerte,
                              @Param("precio_cocina") Double precioCocina, @Param("preciotv") Double precioTV
            , @Param("precio_terraza") Double precioTerraza, @Param("precio_wifi") Double precioWifi, @Param("precio_aire") Double precioAire, @Param("id_hotel") Integer id_hotel);

    @Query("SELECT h FROM Tarifa h where h.hotel.id = ?1")
    Tarifa listarTarifa(Integer id);



    @Query(value = "insert into pensiones(tipo_pension,precio,id_tarifa) values (?1,?2,?3)",nativeQuery = true )
    void guardarPension(@Param("tipo_pension") Integer tipoPension, @Param("precio") Double precio, @Param("id_tarifa") Integer idTarifa);

    @Transactional
    @Modifying
    @Query(value = "update pensiones pen set pen.precio = ?1 where pen.id_tarifa = ?2 and pen.tipo_pension = ?3 ", nativeQuery = true)
    void modificarPension(@Param("precio") Double precio, @Param("id_tarifa") Integer idTarifa, @Param("tipo_pension") Integer tipoPension);

    @Query(value = "insert into temporada(tipo_temporada, fecha_inicio, fecha_fin, precio, id_tarifa) values (?1,?2,?3,?4,?5)",nativeQuery = true )
    void guardarTemporada(@Param("tipo_temporada") Integer tipoTemporada, @Param("fecha_inicio") Date fechaInicio, @Param("fecha_fin") Date fechaFin,@Param("precio") Double precio,@Param("id_tarifa") Integer idTarifa);

    @Transactional
    @Modifying
    @Query(value = "update temporada t set t.fecha_inicio = ?1, t.fecha_fin = ?2, t.precio =?3 where t.id_tarifa = ?4 and t.tipo_temporada = ?5", nativeQuery = true)
    void modificarTemporada(@Param("fecha_inicio") Date fechaInicio, @Param("fecha_fin") Date fechaFin,@Param("precio") Double precio,@Param("id_tarifa") Integer idTarifa, @Param("tipo_temporada") Integer tipoTemporada);

    @Query("Select p from PensionHotel p where p.tarifa.id = ?1 ")
    PensionHotel listarPension(Integer id_tarifa);

    @Query("Select p from TemporadaHotel p where p.tarifa.id = ?1 ")
    TemporadaHotel listarTemporada(Integer id_tarifa);


}
