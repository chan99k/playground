package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import chan99k.adapter.persistence.entities.City;

public interface CityRepository extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {
}