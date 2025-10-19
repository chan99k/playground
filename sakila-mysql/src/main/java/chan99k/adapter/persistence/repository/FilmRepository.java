package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
}