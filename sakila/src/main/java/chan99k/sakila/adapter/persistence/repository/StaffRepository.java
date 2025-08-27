package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.sakila.adapter.persistence.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Short> {
}