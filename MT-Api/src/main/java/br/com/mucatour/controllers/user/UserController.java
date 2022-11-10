package br.com.mucatour.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.mucatour.model.user.User;
import br.com.mucatour.repository.PersonalRepository;
import br.com.mucatour.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    UserRepository userRepository;
    PersonalRepository personalRepository;

    @Autowired
    public UserController(UserRepository userRepository, PersonalRepository personalRepository) {
        this.userRepository = userRepository;
        this.personalRepository = personalRepository;
    }

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

    @GetMapping("/findUser/{id}")
    public ResponseEntity<?> findUser(@PathVariable("id") Long id) throws Exception {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new Exception("Não foi possível localizar o usuário"));

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/delete/user/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }
}
