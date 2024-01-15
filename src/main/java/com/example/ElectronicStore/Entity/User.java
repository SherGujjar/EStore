package com.example.ElectronicStore.Entity;

import com.example.ElectronicStore.Validations.EmailValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    @NotNull
    @Column(unique=true)
    @EmailValidator
    private String email;
    private String password;
    private String role;

    @Size(max = 10,message = "Number length should be equla to 10")
    @Size(min = 10, message = "Number length should be equla to 10")
    @Column(unique=true)
    private String number;
}
