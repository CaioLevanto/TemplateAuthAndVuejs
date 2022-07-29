package br.com.mucatour.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "user_id", columnDefinition = "serial", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_seq")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotBlank(message = "Obrigatório possuir um meio de comunicação")
    private Long phone;

    @OneToOne(cascade = CascadeType.ALL)
    @NotBlank(message = "Obrigatório preencher os Dados Pessoais")
    @JoinColumn(name = "personal_id")
    private Personal personal;

    @OneToOne(cascade = CascadeType.ALL)
    @NotBlank(message = "Obrigatório preencher o Endereço")
    @JoinColumn(name = "address_id")
    private Address address;

    public User(String name,
                String email,
                String password,
                Long phone) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

public String ifPresent(Object object) {
        return null;
}
}
