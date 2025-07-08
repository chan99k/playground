package chan99k.experiments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "critiques")
public class Critique extends AbstractEntity { // 변경

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quote_id", nullable = false)
	private Quote quote;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@Lob
	@Column(nullable = false)
	private String critiqueText;

	// createdAt, updatedAt 필드 제거

	protected Critique() {}
}