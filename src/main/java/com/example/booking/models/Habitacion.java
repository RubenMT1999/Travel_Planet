package com.example.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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

    private String imagen;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @JsonIgnore
    @OneToMany
    private List<Tarifa> id_tarifa;


    @OneToMany(mappedBy = "habitacion",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reserva> reserva;


    public Integer getId() {
        return id;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public String getExtensionTelefonica() {
        return extensionTelefonica;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }


}
