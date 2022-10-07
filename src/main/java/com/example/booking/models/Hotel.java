package com.example.booking.models;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @NotEmpty
    private String ciudad;

    @Column(name = "poblacion")
    private String poblacion;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "cif")
    private String cif;

    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Habitacion> habitaciones;

//    public void addHabitacion(Habitacion habitacion){
//        this.habitaciones.add(habitacion);
//    }


}
