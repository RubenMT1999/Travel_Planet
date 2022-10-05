package com.example.booking.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private Integer enabled;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Authorities> userRoles;



}
