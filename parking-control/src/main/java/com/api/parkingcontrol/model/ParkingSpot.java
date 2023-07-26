package com.api.parkingcontrol.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "db_parking_spot")
public class ParkingSpot implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 10)
    private int parkingSpotNumber;
    @Column(nullable = false, length = 30)
    private int apartment;
    @Column(nullable = false, length = 30)
    private String block;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false)
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parkingSpot", cascade = CascadeType.ALL)
    private Set<Car> cars = new HashSet<>();
}
