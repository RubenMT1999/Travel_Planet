package com.example.booking.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @Column(name = "num_habitacion")
    @NotEmpty
    private int numeroHabitacion;

    @Column(name = "ext_telefonica")
    @NotEmpty
    private String extensionTelefonica;

    @Column(name = "capacidad")
    @NotEmpty
    private int capacidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "disponibilidad")
    @NotNull
    private Boolean disponibilidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @OneToMany
    @JoinColumn(name = "id_tarifa")
    private List<Tarifa> id_tarifa;

}
