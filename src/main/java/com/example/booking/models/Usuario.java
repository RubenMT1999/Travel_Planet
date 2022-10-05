package com.example.booking.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellidos;

    @NotEmpty
    private String contrasenia;

    @NotNull
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past
    private Date fechaNacimiento;

    @NotEmpty
    private String dni;

    @NotEmpty
    private String nacionalidad;

    @NotEmpty
    private String telefono;

    @NotEmpty
    @Email(message = "correo con formato incorrecto")
    private String email;

    @Column(name = "metodo_de_pago")
    private EMetodoDePago metodoDePago;

    private boolean registrado;


    private Integer descuento;


    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reserva> reservas;


    public void addReserva(Reserva reserva){
        this.reservas.add(reserva);
    }

}
