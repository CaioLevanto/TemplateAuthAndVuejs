package br.com.mucatour.repository;

import br.com.mucatour.Models.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Boolean existsByRg(String rg);
    Boolean existsByCpf(String cpf);
}
