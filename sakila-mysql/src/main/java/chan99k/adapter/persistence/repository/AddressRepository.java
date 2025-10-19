package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}