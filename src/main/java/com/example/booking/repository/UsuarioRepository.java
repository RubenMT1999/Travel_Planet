package com.example.booking.repository;

import com.example.booking.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {


    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre and u.contrasenia = :contrasenia")
    Usuario findUserByNameAndPassword(@Param("nombre") String nombre, @Param("contrasenia") String contrasenia);


}
