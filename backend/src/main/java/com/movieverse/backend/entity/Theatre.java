package com.movieverse.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theatres")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private Integer totalScreens;
}
