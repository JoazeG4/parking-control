package com.api.parkingcontrol.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "db_user")
public class User implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 130)
    private String responsableName;
    @Column(nullable = false, length = 10)
    private String dateOfBirth;
    @Column(unique = true ,nullable = false, length = 20)
    private String phone;
    @CPF
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false)
    private LocalDateTime updateDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ParkingSpot parkingSpot;
}
