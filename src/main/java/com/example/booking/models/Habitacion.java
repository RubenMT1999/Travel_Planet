package com.example.booking.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "habitacion")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "")
    private int numero_habitacion;

    @Column(name = "")
    private String extension_telefonica;

    @Column(name = "capacidad")
    private int capacidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @OneToOne
    @JoinColumn(name = "id_hotel")
    private Hotel id_hotel;

    @OneToMany
    @JoinColumn(name = "id_tarifa")
    private List<Tarifa> id_tarifa;

}
