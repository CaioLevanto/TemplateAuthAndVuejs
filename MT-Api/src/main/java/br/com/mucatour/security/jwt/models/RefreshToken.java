package br.com.mucatour.security.jwt.models;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.*;

import br.com.mucatour.model.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "refreshtoken")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RefreshToken implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;
    
    @Column(nullable = false)
    private Instant expiryDate;
}
