package com.example.booking.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    private Pension pension;

   /* @Column(name = "precio_persona")
    private boolean precioPersona;
    */
    @Column(name = "precio_wifi")
    private Double precioWifi;

    @Column(name = "precio_caja_fuerte")
    private Double precioCajaFuerte;

    @Column(name = "precio_cocina")
    private Double precioCocina;

    @Column(name = "precio_aire")
    private Double precioAire;

    @Column(name = "precio_tv")
    private Double precioTV;

    @Column(name = "precio_terraza")
    private Double precioTerraza;

    @Column(name = "precio_baño")
    private Double precioBanio;


   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;



   */ @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_hotel", referencedColumnName = "id")
    private Hotel hotelTarifa;




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

    public Double getPrecioBanio() {
        return precioBanio;
    }

    public Hotel getHotelTarifa() {
        return hotelTarifa;
    }
}

