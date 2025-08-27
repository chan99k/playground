package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.sakila.adapter.persistence.entities.FilmActor;
import chan99k.sakila.adapter.persistence.entities.FilmActorId;

public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
}