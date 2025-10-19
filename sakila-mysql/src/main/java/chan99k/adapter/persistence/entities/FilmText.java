package chan99k.adapter.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "film_text")
public class FilmText {
	@Id
	@Column(name = "film_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer id;

	@Column(name = "title", nullable = false)
	private String title;

	@Lob
	@Column(name = "description")
	private String description;

}