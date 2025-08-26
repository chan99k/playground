package chan99k.sakila.adapter.persistence.entities;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "payment")
public class Payment {
	@Id
	@Column(name = "payment_id", columnDefinition = "smallint UNSIGNED not null")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "staff_id", nullable = false)
	private Staff staff;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "rental_id")
	private Rental rental;

	@Column(name = "amount", nullable = false, precision = 5, scale = 2)
	private BigDecimal amount;

	@Column(name = "payment_date", nullable = false)
	private Instant paymentDate;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_update")
	private Instant lastUpdate;

}