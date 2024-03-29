package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface IPageRepositoryHab extends PagingAndSortingRepository<Habitacion,Integer> {


    @Query("SELECT h FROM Habitacion h where h.hotel.id = ?1")
    Page<Habitacion> listarHabitacionesPages(Pageable pageable, Integer id);


    @Query(value = "select * from vistahabitacion where id_hotel = :id and capacidad = :capacidad and ((fecha_inicio not between :fecha_inicio and :fecha_fin " +
            "and fecha_fin not between :fecha_inicio and :fecha_fin) or (fecha_inicio is null and fecha_fin is null)) and disponibilidad = 1 group by id", nativeQuery = true)

    Page<Habitacion> buscarHabitacionesPages(Integer id, Integer capacidad, Date fecha_inicio, Date fecha_fin, Pageable pageable);


}
