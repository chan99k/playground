package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import chan99k.sakila.adapter.persistence.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Short>, JpaSpecificationExecutor<Category> {
}