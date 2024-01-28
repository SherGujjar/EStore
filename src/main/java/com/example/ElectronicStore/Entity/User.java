package com.example.ElectronicStore.Entity;

import com.example.ElectronicStore.Validations.EmailValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

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

    @Version
    private int version;

    private String name;
    @NotNull
    @Column(unique=true)
    @EmailValidator
    private String email;
    private String password;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Size(max = 10,message = "Number length should be equla to 10")
    @Size(min = 10, message = "Number length should be equla to 10")
    @Column(unique=true)
    private String number;


}
