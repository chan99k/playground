package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Store;

public interface StoreRepository extends JpaRepository<Store, Short> {
}