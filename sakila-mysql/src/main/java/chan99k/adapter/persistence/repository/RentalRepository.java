package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}