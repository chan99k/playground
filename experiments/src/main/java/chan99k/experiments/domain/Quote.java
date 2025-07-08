package chan99k.experiments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "quotes")
public class Quote extends AbstractWriteOnceEntity { // 변경

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "story_id", nullable = false)
	private Story story;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@Lob
	@Column(nullable = false)
	private String originalText;

	private String textFragmentUrl;

	// createdAt, updatedAt 필드 제거 (lastModifiedAt은 필요 없으므로)

	protected Quote() {}
}