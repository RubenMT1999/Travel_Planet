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

    private Pension pension;

    @Column(name = "precio_persona")
    private boolean precioPersona;

    @Column(name = "precioWifi")
    private Double precioWifi;

    @Column(name = "precioCajaFuerte")
    private Double precioCajaFuerte;

    @Column(name = "precioCocina")
    private Double precioCocina;

    @Column(name = "precioBaño")
    private Double precioAire;

    @Column(name = "precioTV")
    private Double precioTV;

    @Column(name = "precioTerraza")
    private Double precioTerraza;

    public Integer getId() {
        return id;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public Pension getPension() {
        return pension;
    }

    public boolean isPrecioPersona() {
        return precioPersona;
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
}

