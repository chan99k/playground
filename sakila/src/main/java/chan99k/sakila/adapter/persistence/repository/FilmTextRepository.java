package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.sakila.adapter.persistence.entities.FilmText;

public interface FilmTextRepository extends JpaRepository<FilmText, Integer> {
}