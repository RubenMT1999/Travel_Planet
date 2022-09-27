package com.example.booking.models;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

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
    private String ciudad;

    @Column(name = "poblacion")
    private String pobalcion;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "cif")
    private int cif;

}
