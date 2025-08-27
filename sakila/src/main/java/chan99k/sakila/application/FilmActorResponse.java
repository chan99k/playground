package chan99k.sakila.application;

public record FilmActorResponse(
	Integer filmId,
	String filmTitle,
	Integer actorId,
	String actorFirstName,
	String actorLastName
) {
}

