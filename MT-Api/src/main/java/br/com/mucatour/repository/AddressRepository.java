package br.com.mucatour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mucatour.model.user.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
