package com.example.booking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class GrapqlModels {

    @Data
    @AllArgsConstructor
    public class UsuarioInput{
        private String nombre;
        private String apellidos;
        private String password;
        private Date fechaNacimiento;
        private String dni;
        private String nacionalidad;
        private String telefono;
        private String email;
        private Boolean esHotelero;
    }

}
