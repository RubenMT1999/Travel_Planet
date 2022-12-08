package com.example.booking.repository;

import com.example.booking.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPensionRepository extends JpaRepository<PensionHotel,Integer> {

    @Query(value = "SELECT p.precio from PensionHotel p where p.pension=?1 and p.tarifa=?2")
    Double precioPension(EPension pension, Tarifa idTarifa);

    @Query(value = "select t.precio from TemporadaHotel t where t.temporada = ?1 and t.tarifa = ?2")
    Double precioTemporada(Temporada temporada, Tarifa idTarifa);

    @Query("Select p from PensionHotel p where p.tarifa.id = ?1 ")
    List<PensionHotel> listarPension(Integer id_tarifa);
}
