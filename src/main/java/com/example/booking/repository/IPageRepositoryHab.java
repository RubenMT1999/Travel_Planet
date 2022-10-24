package com.example.booking.repository;

import com.example.booking.models.Habitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IPageRepositoryHab extends PagingAndSortingRepository<Habitacion,Integer> {


    @Query("SELECT h FROM Habitacion h where h.hotel.id = ?1")
    Page<Habitacion> listarHabitacionesPages(Pageable pageable, Integer id);


}
