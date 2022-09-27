package com.example.booking.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name = "pension")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "completa")
    private boolean completa;

    @Column(name = "desayuno_cena")
    private boolean desayuno_Cena;

    @Column(name = "desayuno")
    private boolean desayuno;

    @Column(name = "ninguna")
    private boolean ninguna;
}
