package br.com.mucatour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mucatour.security.jwt.models.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM refreshtoken rt where rt.id=:id")
    void deleteRefreshTokenById(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM refreshtoken rt where rt.user.id=:id")
    void deleteByUser(@Param("id") Long id);
}
