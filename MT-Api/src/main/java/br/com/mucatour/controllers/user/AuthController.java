package br.com.mucatour.controllers.user;

import br.com.mucatour.exception.AuthException;
import br.com.mucatour.model.user.Address;
import br.com.mucatour.model.user.ERole;
import br.com.mucatour.model.user.Personal;
import br.com.mucatour.model.user.Role;
import br.com.mucatour.model.user.User;
import br.com.mucatour.payload.request.user.LoginRequest;
import br.com.mucatour.payload.request.user.SignupRequest;
import br.com.mucatour.payload.request.user.TokenRefreshRequest;
import br.com.mucatour.payload.response.JwtResponse;
import br.com.mucatour.payload.response.MessageResponse;
import br.com.mucatour.payload.response.TokenRefreshResponse;
import br.com.mucatour.repository.AddressRepository;
import br.com.mucatour.repository.PersonalRepository;
import br.com.mucatour.repository.RoleRepository;
import br.com.mucatour.repository.UserRepository;
import br.com.mucatour.security.jwt.JwtUtils;
import br.com.mucatour.security.jwt.exception.TokenRefreshException;
import br.com.mucatour.security.jwt.models.RefreshToken;
import br.com.mucatour.security.services.RefreshTokenService;
import br.com.mucatour.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
@RequestMapping("/api/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

  AuthenticationManager authenticationManager;
  UserRepository userRepository;
  JwtUtils jwtUtils;
  RoleRepository roleRepository;
  PersonalRepository personalRepository;
  AddressRepository addressRepository;
  PasswordEncoder encoder;
  RefreshTokenService refreshTokenService;

  @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String jwt = jwtUtils.generateJwtToken(userDetails);
    User hasUser = userRepository.findById(userDetails.getId()).get();
    String name = (hasUser.getFirstName() + " " + hasUser.getSecondName());

    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    if (roles.isEmpty()) {
      // Adiciona Role caso não possua por padrão ou tenha sido deletado.
      Set<Role> setRole = new HashSet<>();
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      setRole.add(userRole);
      hasUser.setRoles(setRole);

      userRepository.save(hasUser);
      roles.add(hasUser.getRoles().toString());
    }
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
    return ResponseEntity.ok(
        new JwtResponse(jwt,
            refreshToken.getToken(),
            userDetails.getId(),
            userDetails.getEmail(),
            name,
            roles));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refToken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();
    Optional<RefreshToken> rToken = refreshTokenService.findByToken(requestRefreshToken);

    if (rToken.isPresent()) {
      User hasToken = refreshTokenService.verifyExpiration(rToken.get()).getUser();
      return ResponseEntity.ok(new TokenRefreshResponse(
          jwtUtils.generateTokenFromUsername(hasToken.getEmail()), requestRefreshToken));
    } else {
      return ResponseEntity.badRequest().body(new TokenRefreshException(requestRefreshToken,
          "Refresh token não está no banco de dados"));
    }
  }

  @PostMapping("/signup")
  @Transactional(rollbackOn = AuthException.class)
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws AuthException {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new AuthException("Error: Email já está cadastro!");
    }

    if (signUpRequest.getPersonal() != null) {
      if (personalRepository.existsByCpf(signUpRequest.getPersonal().getCpf())) {
        throw new AuthException("CPF informado já está cadastrado no sistema, entre em contato com Assessor");
      }
      if (personalRepository.existsByRg(signUpRequest.getPersonal().getRg())) {
        throw new AuthException("RG informado já está cadastrado no sistema, entre em contato com Assessor");
      }
    } else {
      throw new AuthException("Os dados pessoais não foram possível salvar.");
    }

    User user = User.buildToUser(signUpRequest);

    Personal upPerson = personalRepository.save(Personal
        .buildToPersonal(signUpRequest.getPersonal()));

    Address upAddress = addressRepository.save(Address
        .buildToAddress(signUpRequest.getAddress()));

    user.setRoles(getRoles(signUpRequest));
    user.setPersonal(upPerson);
    user.setAddress(upAddress);

    return ResponseEntity.ok(new MessageResponse(userRepository.save(user), "Usuário criado com sucesso!"));
  }

  private Set<Role> getRoles(SignupRequest signUpRequest) {
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException(
                    "Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException(
                    "Error: Role is not found."));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException(
                    "Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    return roles;
  }

  @GetMapping("/logout/{id}")
  public void logoutUser(@PathVariable("id") Long logoutId) {
    refreshTokenService.deleteByUserId(logoutId);
  }
}
