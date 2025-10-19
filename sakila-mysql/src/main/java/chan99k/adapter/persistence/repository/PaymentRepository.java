package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}