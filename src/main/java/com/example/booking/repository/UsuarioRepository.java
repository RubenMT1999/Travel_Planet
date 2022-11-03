package com.example.booking.repository;

import com.example.booking.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre and u.contrasenia = :contrasenia")
    Usuario findUserByNameAndPassword(@Param("nombre") String nombre, @Param("contrasenia") String contrasenia);

    @Query("select u from Usuario u where u.email = ?1")
    Usuario buscarPormail(String mail);

    @Query("select u from Usuario u where u.nombre = ?1")
    Usuario usuarioPorNombre(String nombre);


    @Query("select u.email from Usuario u where u.email = ?1")
    String validarEmail(String email);

    @Query("SELECT u FROM Usuario u where u.email = ?1")
    Usuario datosUsuario(String nombre);



}
