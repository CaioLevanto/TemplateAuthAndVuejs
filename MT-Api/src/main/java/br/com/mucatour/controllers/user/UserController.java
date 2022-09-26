package br.com.mucatour.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mucatour.repository.PersonalRepository;
import br.com.mucatour.repository.UserRepository;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    UserRepository userRepository;
    PersonalRepository personalRepository;

    @PostMapping("/hasEmail")
    public Boolean hasEmail(@RequestBody String email) {
        return userRepository.existsByEmail(email);
    }

    @PostMapping("/hasRg")
    public Boolean hasRg(@RequestBody String rg) {
        return personalRepository.existsByRg(rg);
    }

    @PostMapping("/hasCpf")
    public Boolean hasCpf(@RequestBody String cpf) {
        return personalRepository.existsByCpf(cpf);
    }

}
