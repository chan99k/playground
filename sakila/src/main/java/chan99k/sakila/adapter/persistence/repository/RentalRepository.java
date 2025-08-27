package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.sakila.adapter.persistence.entities.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
}