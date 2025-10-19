package chan99k.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.adapter.persistence.entities.FilmText;

public interface FilmTextRepository extends JpaRepository<FilmText, Integer> {
}