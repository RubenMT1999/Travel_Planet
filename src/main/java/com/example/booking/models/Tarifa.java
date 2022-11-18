package com.example.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tarifa")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Temporada temporada;



    @Column(name = "precio_wifi")
    private Double precioWifi;

    @Column(name = "precio_caja_fuerte")
    private Double precioCajaFuerte;

    @Column(name = "precio_cocina")
    private Double precioCocina;

    @Column(name = "precio_aire")
    private Double precioAire;

    @Column(name = "preciotv")
    private Double precioTV;

    @Column(name = "precio_terraza")
    private Double precioTerraza;

    @Column(name = "precio_banio")
    private Double precioBanio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", referencedColumnName = "id")
    @JsonIgnoreProperties(value="tarifa")
    @JsonIgnore
    private Hotel hotel;

    @OneToMany(mappedBy = "tarifa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PensionHotel> pensionHoteles;

    @OneToMany(mappedBy = "tarifa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TemporadaHotel> temporadaHoteles;

    public Integer getId() {
        return id;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public List<TemporadaHotel> getTemporadaHoteles() {
        return temporadaHoteles;
    }


    public Double getPrecioWifi() {
        return precioWifi;
    }

    public Double getPrecioCajaFuerte() {
        return precioCajaFuerte;
    }

    public Double getPrecioCocina() {
        return precioCocina;
    }

    public Double getPrecioAire() {
        return precioAire;
    }

    public Double getPrecioTV() {
        return precioTV;
    }

    public Double getPrecioTerraza() {
        return precioTerraza;
    }

    public Hotel getHotel() {return hotel;}

    public Double getPrecioBanio() {
        return precioBanio;
    }
}

