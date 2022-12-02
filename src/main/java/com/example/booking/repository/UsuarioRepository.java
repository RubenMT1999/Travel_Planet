package com.example.booking.repository;

import com.example.booking.models.Authorities;
import com.example.booking.models.UserAuth;
import com.example.booking.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    @Query("SELECT u FROM Usuario u where u.id = ?1")
    Usuario datosUsuarioID(Integer id);


    @Transactional
    @Modifying
    @Query("UPDATE Usuario SET nombre = :nombre, apellidos = :apellidos, contrasenia = :contrasenia," +
            "fechaNacimiento = :fechaNacimiento, dni = :dni, nacionalidad = :nacionalidad," +
            "telefono = :telefono WHERE id = :id")
     void editarUsuario(String nombre, String apellidos, String contrasenia,
                        Date fechaNacimiento, String dni, String nacionalidad,
                        Integer id, String telefono);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario SET esHotelero = :getAdmin where id = :id")
    void getAdmin(Boolean getAdmin, Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Authorities SET authority = :roleAdmin where user.id = :id")
    void getIdAuthorities(String roleAdmin, Integer id);

    @Query("SELECT aut FROM Authorities aut  where aut.user.id = ?1")
    Authorities getAuthorities(Integer id);

    @Query("SELECT ua from UserAuth ua where ua.username = ?1")
    UserAuth getIdUserAuth(String username);

}
