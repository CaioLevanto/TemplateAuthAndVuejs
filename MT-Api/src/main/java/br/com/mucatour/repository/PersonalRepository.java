package br.com.mucatour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mucatour.model.user.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Boolean existsByRg(String rg);
    Boolean existsByCpf(String cpf);
}
