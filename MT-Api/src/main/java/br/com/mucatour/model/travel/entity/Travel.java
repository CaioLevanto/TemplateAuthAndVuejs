package br.com.mucatour.model.travel.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import br.com.mucatour.model.travel.enums.GoBankEnum;
import br.com.mucatour.model.travel.enums.StatusTravel;
import br.com.mucatour.model.user.User;
import br.com.mucatour.payload.request.viagem.ViagemRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Travel implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(columnDefinition = "serial", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_seq_gen")
    @SequenceGenerator(name = "travel_seq_gen", sequenceName = "travel_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitante_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @Column(name = "expired_at", nullable = false)
    private long expiredAt;

    @Column(name = "validated_at", nullable = true)
    private long validateAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusTravel status;

    @Column(name = "travel_origin", nullable = false)
    private String origin;

    @Column(name = "travel_destiny", nullable = false)
    private String destiny;

    @Column(name = "start_date", nullable = false)
    private long startDate;

    @Column(name = "back_date")
    private long backDate;

    @Column(name = "go_back", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GoBankEnum radioGoBack;

    @Column(name = "count_adult", nullable = false)
    private Integer adult;

    @Column(name = "count_kids", nullable = false)
    private Integer kids;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "follows", joinColumns = @JoinColumn(name = "follow_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> follows = new HashSet<>();

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Itinerary> itinerary;

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payment = new ArrayList<>();

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Chat> chat;

    public static Travel buildToTravel(ViagemRequest dto) {
        return Travel.builder()
                .user(dto.getUser())
                .createdAt(Instant.now().getEpochSecond())
                .expiredAt(Instant.now().plus(6, ChronoUnit.DAYS).getEpochSecond())
                .status(StatusTravel.PENDENTE)
                .origin(dto.getOrigin())
                .destiny(dto.getDestiny())
                .startDate(Instant.parse(dto.getStartDate()).getEpochSecond())
                .backDate(Instant.parse(dto.getBackDate()).getEpochSecond())
                .radioGoBack(GoBankEnum.values()[dto.getRadioGoBack()])
                .adult(dto.getAdult())
                .kids(dto.getKids())
                .build();
    }
}
