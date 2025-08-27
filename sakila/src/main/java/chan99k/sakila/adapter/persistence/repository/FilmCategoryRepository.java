package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.sakila.adapter.persistence.entities.FilmCategory;
import chan99k.sakila.adapter.persistence.entities.FilmCategoryId;

public interface FilmCategoryRepository extends JpaRepository<FilmCategory, FilmCategoryId> {
}