package com.example.booking.models;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "num_hab")
    private int numero_habitaciones;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ciudad")
    @NotEmpty
    private String ciudad;

    @Column(name = "poblacion")
    private String poblacion;

    @Column(name = "lugar")
    private String lugar;

    @Column(name ="imagen")
    private String imagen;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "cif")
    private String cif;


    @Column(name = "comentario")
    private String comentario;

    @Column(name = "puntuacion")
    private String puntuacion;


    @Column(name = "precio")
    private String precio;
    public String getPrecio() {
        return precio;
    }

    public String getComentario() {
        return comentario;
    }

    public String getPuntuacion() {
        return puntuacion;
    }



    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY)
    private List<Habitacion> habitaciones;

//    public void addHabitacion(Habitacion habitacion){
//        this.habitaciones.add(habitacion);
//    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


    public Usuario getUsuario() {
        return usuario;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    public int getNumero_habitaciones() {
        return numero_habitaciones;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPais() {
        return pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPoblacion() {
        return poblacion;
    }
    public String getLugar() {
        return lugar;
    }


    public String getLocalidad() {
        return localidad;
    }

    public String getCif() {
        return cif;
    }

    public String getImagen() {
        return imagen;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
}
