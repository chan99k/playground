package chan99k.adapter.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@lombok.Getter
@lombok.Setter
@Embeddable
public class FilmActorId implements Serializable {
	private static final long serialVersionUID = -938694525770063735L;
	@Column(name = "actor_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer actorId;

	@Column(name = "film_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer filmId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		FilmActorId entity = (FilmActorId)o;
		return Objects.equals(this.actorId, entity.actorId) &&
			Objects.equals(this.filmId, entity.filmId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId, filmId);
	}

}