package br.com.mucatour.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mucatour.Models.Address;
import br.com.mucatour.Models.ERole;
import br.com.mucatour.Models.Personal;
import br.com.mucatour.Models.Role;
import br.com.mucatour.Models.User;
import br.com.mucatour.payload.request.SignupRequest;
import br.com.mucatour.payload.response.MessageResponse;
import br.com.mucatour.repository.AddressRepository;
import br.com.mucatour.repository.PersonalRepository;
import br.com.mucatour.repository.RoleRepository;
import br.com.mucatour.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            "Error: Email is already in use!"));
        }
        if (signUpRequest.getPersonal() != null) {
            if (personalRepository.existsByCpf(signUpRequest.getPersonal().getCpf())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(
                                "CPF informado já está cadastrado no sistema, entre em contato com Assessor"));
            }
            if (personalRepository.existsByRg(signUpRequest.getPersonal().getRg())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(
                                "RG informado já está cadastrado no sistema, entre em contato com Assessor"));
            }
        } else {
            return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("É necessário informar os dados pessoais."));
        }
        
        User user = new User(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getPhone()
        );

        Personal hasPersonal = personalRepository.save(Personal.build(signUpRequest));
        Address hasAddress = addressRepository.save(Address.build(signUpRequest));

        user.setRoles(getRoles(signUpRequest));
        user.setPersonal(hasPersonal);
        user.setAddress(hasAddress);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
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
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }
}
