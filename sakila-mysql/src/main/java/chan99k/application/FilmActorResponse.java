package chan99k.application;

public record FilmActorResponse(
	Integer filmId,
	String filmTitle,
	Integer actorId,
	String actorFirstName,
	String actorLastName
) {
}

