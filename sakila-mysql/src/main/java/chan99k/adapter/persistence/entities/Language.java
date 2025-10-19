package chan99k.adapter.persistence.entities;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "language")
public class Language {
	@Id
	@Column(name = "language_id", columnDefinition = "tinyint UNSIGNED not null")
	private Short id;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

}