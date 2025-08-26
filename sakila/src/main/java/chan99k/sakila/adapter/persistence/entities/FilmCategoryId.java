package chan99k.sakila.adapter.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@lombok.Getter
@lombok.Setter
@Embeddable
public class FilmCategoryId implements Serializable {
	private static final long serialVersionUID = -6547101471343474908L;
	@Column(name = "film_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer filmId;

	@Column(name = "category_id", columnDefinition = "tinyint UNSIGNED not null")
	private Short categoryId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		FilmCategoryId entity = (FilmCategoryId)o;
		return Objects.equals(this.filmId, entity.filmId) &&
			Objects.equals(this.categoryId, entity.categoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(filmId, categoryId);
	}

}