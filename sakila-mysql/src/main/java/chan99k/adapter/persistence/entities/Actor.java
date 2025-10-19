package chan99k.adapter.persistence.entities;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "actor")
public class Actor {
	@Id
	@Column(name = "actor_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer id;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

}