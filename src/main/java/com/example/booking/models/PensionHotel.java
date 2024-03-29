package com.example.booking.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "pensiones")
public class PensionHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_pension")
    private EPension pension;

    @Column(name = "precio")
    private Double precio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarifa")
    private Tarifa tarifa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EPension getPension() {
        return pension;
    }

    public void setPension(EPension pension) {
        this.pension = pension;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }
}