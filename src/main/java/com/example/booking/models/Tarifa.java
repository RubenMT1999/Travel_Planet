package com.example.booking.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "id_pension")
    private Pension pension;

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

    @Column(name = "precio_baño")
    private Double precioBanio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", referencedColumnName = "id")
    private Hotel hotel;

    public Integer getId() {
        return id;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public Pension getPension() {
        return pension;
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

