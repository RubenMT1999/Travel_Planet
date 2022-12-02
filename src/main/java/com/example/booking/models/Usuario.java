package com.example.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Length(max = 15,message = "El nombre no puede tener más de 15 caracteres")
    private String nombre;

    @NotEmpty
    @Length(max = 25, message = "Los apellidos no puede tener más de 25 caracteres")
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
    @Length(max = 9)
    @Pattern(regexp = "[0-9]{7,8}[A-Za-z]",message = "Formato DNI incorrecto")
    private String dni;

    @NotEmpty
    private String nacionalidad;

    @NotEmpty
    private String telefono;

    @NotEmpty
    @Email(message = "correo con formato incorrecto")
    private String email;

    private boolean registrado;


    private Integer descuento;


    @Column(name = "es_hotelero")
    private Boolean esHotelero;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reserva> reservas;


    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private Set<Hotel> hoteles;


    public void addReserva(Reserva reserva){
        this.reservas.add(reserva);
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }


    public boolean isRegistrado() {
        return registrado;
    }

    public Integer getDescuento() {
        return descuento;
    }


    public Boolean getEsHotelero() {
        return esHotelero;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    @JsonIgnore public Set<Hotel> getHoteles() {
        return hoteles;
    }
}
