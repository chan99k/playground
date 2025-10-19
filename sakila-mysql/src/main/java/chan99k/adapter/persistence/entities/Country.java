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
@Table(name = "country")
public class Country {
	@Id
	@Column(name = "country_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer id;

	@Column(name = "country", nullable = false, length = 50)
	private String country;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

}