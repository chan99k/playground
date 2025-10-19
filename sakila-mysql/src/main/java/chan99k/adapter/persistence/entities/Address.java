package chan99k.adapter.persistence.entities;

import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "address")
public class Address {
	@Id
	@Column(name = "address_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer id;

	@Column(name = "address", nullable = false, length = 50)
	private String address;

	@Column(name = "address2", length = 50)
	private String address2;

	@Column(name = "district", nullable = false, length = 20)
	private String district;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "city_id", nullable = false)
	private City city;

	@Column(name = "postal_code", length = 10)
	private String postalCode;

	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

/*
 TODO [Reverse Engineering] create field to map the 'location' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "location", columnDefinition = "geometry not null")
    private Object location;
*/
}