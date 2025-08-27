package chan99k.sakila.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chan99k.sakila.adapter.persistence.entities.FilmActor;
import chan99k.sakila.adapter.persistence.repository.FilmActorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class FilmActorService {
	private final FilmActorRepository filmActorRepository;

	public FilmActorService(FilmActorRepository filmActorRepository) {
		this.filmActorRepository = filmActorRepository;
	}

	// N+1 문제 발생
	public List<FilmActorResponse> getAllFilmActorsWithNPlusOne(Pageable pageable) {
		long startTime = System.currentTimeMillis();
		List<FilmActor> filmActors = filmActorRepository.getAllFilmActorsWithNPlusOne(pageable).getContent();

		List<FilmActorResponse> result = filmActors.stream()
			.map(filmActor -> new FilmActorResponse(
				filmActor.getFilm().getId(),
				filmActor.getFilm().getTitle(),        // N+1 발생!
				filmActor.getActor().getId(),
				filmActor.getActor().getFirstName(),   // N+1 발생!
				filmActor.getActor().getLastName()
			))
			.toList();

		long endTime = System.currentTimeMillis();
		log.debug("N+1 처리 시간: {}ms, 결과 개수: {}", endTime - startTime, result.size());
		return result;
	}

	public List<FilmActorResponse> getAllFilmActorsWithEntityGraph(Pageable pageable) {
		long startTime = System.currentTimeMillis();
		List<FilmActor> filmActors = filmActorRepository.getAllFilmActorsWithEntityGraph(pageable).getContent();

		List<FilmActorResponse> result = filmActors.stream()
			.map(filmActor -> new FilmActorResponse(
				filmActor.getFilm().getId(),
				filmActor.getFilm().getTitle(),
				filmActor.getActor().getId(),
				filmActor.getActor().getFirstName(),
				filmActor.getActor().getLastName()
			))
			.toList();

		long endTime = System.currentTimeMillis();
		log.debug("EntityGraph 처리 시간: {}ms, 결과 개수: {}", endTime - startTime, result.size());
		return result;
	}

	public List<FilmActorResponse> getAllFilmActorsWithFetchJoin(Pageable pageable) {
		long startTime = System.currentTimeMillis();
		List<FilmActor> filmActors = filmActorRepository.getAllFilmActorsWithFetchJoin(pageable).getContent();

		List<FilmActorResponse> result = filmActors.stream()
			.map(filmActor -> new FilmActorResponse(
				filmActor.getFilm().getId(),
				filmActor.getFilm().getTitle(),
				filmActor.getActor().getId(),
				filmActor.getActor().getFirstName(),
				filmActor.getActor().getLastName()
			))
			.toList();

		long endTime = System.currentTimeMillis();
		log.debug("FetchJoin 처리 시간: {}ms, 결과 개수: {}", endTime - startTime, result.size());
		return result;
	}
}
