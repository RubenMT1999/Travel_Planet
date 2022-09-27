package com.example.booking.models;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "temporada")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temporada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "alta")
    private boolean alta;

    @Column(name = "media")
    private boolean media;

    @Column(name = "baja")
    private boolean baja;
}
