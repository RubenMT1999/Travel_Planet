package com.example.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    private int numeroHabitacion;

    @Column(name = "ext_telefonica")
    @NotEmpty
    private String extensionTelefonica;

    @Column(name = "capacidad")
    @NotNull
    private int capacidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "descuento")
    private String descuento;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;
    @Column(name = "imagen")
    private String imagen;

    @Column(name = "caja_fuerte")
    private boolean cajaFuerte;

    @Column(name = "cocina")
    private boolean cocina;

    @Column(name = "banio_privado")
    private boolean banioPrivado;

    @Column(name = "aire_acondicionado")
    private boolean aireAcondicionado;
    @Column(name = "tv")
    private boolean tv;
    @Column(name = "terraza")
    private boolean terraza;
    @Column(name = "wifi")
    private boolean wifi;

    @Column(name = "precio_base")
    @NotNull
    private Double precioBase;

    //tuve que quitar el cascade para que no me diera fallo al crear habitacion

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel")
    @JsonIgnore
    @JsonIgnoreProperties(value="habitacion")
    private Hotel hotel;


    @JsonIgnore
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
    public String getDescuento() {
        return descuento;
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

    public Hotel getHotel() {
        return hotel;
    }


    public boolean isCajaFuerte() {
        return cajaFuerte;
    }

    public boolean isCocina() {
        return cocina;
    }

    public boolean isBanioPrivado() {
        return banioPrivado;
    }

    public boolean isAireAcondicionado() {
        return aireAcondicionado;
    }

    public boolean isTv() {
        return tv;
    }

    public boolean isTerraza() {
        return terraza;
    }

    public boolean isWifi() {
        return wifi;
    }

    public Double getPrecioBase() {
        return precioBase;
    }


    public Set<Reserva> getReserva() {
        return reserva;
    }
}
