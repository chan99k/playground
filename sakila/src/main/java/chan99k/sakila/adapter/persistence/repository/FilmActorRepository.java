package chan99k.sakila.adapter.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import chan99k.sakila.adapter.persistence.entities.FilmActor;
import chan99k.sakila.adapter.persistence.entities.FilmActorId;

public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
	// N+1 문제 발생용
	@Query("SELECT fa FROM FilmActor fa ORDER BY fa.film.id")
	Page<FilmActor> getAllFilmActorsWithNPlusOne(Pageable pageable);

	@EntityGraph(attributePaths = {"film", "actor"})
	@Query("SELECT fa FROM FilmActor fa ORDER BY fa.film.id")
	Page<FilmActor> getAllFilmActorsWithEntityGraph(Pageable pageable);

	@Query("SELECT fa FROM FilmActor fa JOIN FETCH fa.film JOIN FETCH fa.actor ORDER BY fa.film.id")
	Page<FilmActor> getAllFilmActorsWithFetchJoin(Pageable pageable);

}