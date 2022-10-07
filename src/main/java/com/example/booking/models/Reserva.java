package com.example.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reserva")

    private Set<Habitacion> habitaciones;

    @Column(name = "metodo_pago")
    private EMetodoDePago metodoDePago;

    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaInicio;


    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaFin;



}
