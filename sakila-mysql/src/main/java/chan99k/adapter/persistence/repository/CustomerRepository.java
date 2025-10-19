package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}