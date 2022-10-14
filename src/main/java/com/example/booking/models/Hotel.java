package com.example.booking.models;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


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

    @Column(name = "num_habitaciones")
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

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "precio")
    private String precio;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "cif")
    private String cif;

    private String imagen;

    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Habitacion> habitaciones;

//    public void addHabitacion(Habitacion habitacion){
//        this.habitaciones.add(habitacion);
//    }

    public String getImagen() {
        return imagen;
    }
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;



    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public String getComentario() {
        return comentario;
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

    public String getPrecio() {
        return precio;
    }
}
