package com.example.booking.repository;

import com.example.booking.models.Pension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PensionRepository extends JpaRepository<Pension,Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into pension (completa, desayuno_cena, desayuno, ninguno) values (?1,?2,?3,?4)",nativeQuery = true )
    void guardarPension(@Param("completa") Integer Completa, @Param("desayuno_cena") Integer Desayuno_Cena,
                       @Param("desayuno") Integer Desayuno, @Param("ninguna") Integer Ninguna);

}
