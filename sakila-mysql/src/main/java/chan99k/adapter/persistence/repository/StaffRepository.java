package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Short> {
}