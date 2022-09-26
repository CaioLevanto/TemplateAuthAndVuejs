package br.com.mucatour.model.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.com.mucatour.model.travel.entity.Travel;
import br.com.mucatour.payload.request.user.SignupRequest;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(columnDefinition = "serial", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_seq")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Personal personal;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Address address;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<Travel> travel = new HashSet<>();

    public static User buildToUser(SignupRequest sign) {
        return User.builder()
                .firstName(sign.getFirstName())
                .secondName(sign.getSecondName())
                .email(sign.getEmail())
                .password(sign.getPassword())
                .phone(sign.getPhone())
                .build();
    }
}
