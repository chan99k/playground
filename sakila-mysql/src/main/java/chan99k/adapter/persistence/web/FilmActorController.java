package chan99k.adapter.persistence.web;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chan99k.application.FilmActorResponse;
import chan99k.application.FilmActorService;

@RestController
@RequestMapping("/api/film-actors")
public class FilmActorController {
	private final FilmActorService filmActorService;

	public FilmActorController(FilmActorService filmActorService) {
		this.filmActorService = filmActorService;
	}

	@GetMapping
	public List<FilmActorResponse> getAllFilmActorsWithNPlusOne(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10000") int size // 기본 사이즈를 큰 사이즈로 하여 전체 레코드 조회하도록 함
	) {
		Pageable pageable = PageRequest.of(0, size);
		return filmActorService.getAllFilmActorsWithNPlusOne(pageable);
	}

	@GetMapping("/entity-graph")
	public List<FilmActorResponse> getAllFilmActorsWithEntityGraph(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10000") int size) {
		Pageable pageable = PageRequest.of(0, size);
		return filmActorService.getAllFilmActorsWithEntityGraph(pageable);
	}

	@GetMapping("/fetch-join")
	public List<FilmActorResponse> getAllFilmActorsWithFetchJoin(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10000") int size) {
		Pageable pageable = PageRequest.of(0, size);
		return filmActorService.getAllFilmActorsWithFetchJoin(pageable);
	}
}

