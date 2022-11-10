package br.com.mucatour.security.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import br.com.mucatour.repository.RefreshTokenRepository;
import br.com.mucatour.repository.UserRepository;
import br.com.mucatour.security.jwt.exception.TokenRefreshException;
import br.com.mucatour.security.jwt.models.RefreshToken;

import javax.transaction.Transactional;

@Service
@Transactional
public class RefreshTokenService {

    @Value("${mucatour.app.jwtRefreshExpirationMs}")
    Long refreshTokenDurationMs;

    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        return refreshTokenRepository.save(
                    RefreshToken.buildRefreshToken(userId, refreshTokenDurationMs));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.deleteRefreshTokenById(token.getId());
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userId);
    }

}