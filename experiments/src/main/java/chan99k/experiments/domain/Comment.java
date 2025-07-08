package chan99k.experiments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends AbstractEntity { // 변경

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	@Lob
	@Column(nullable = false)
	private String text;

	// createdAt, updatedAt 필드 제거

	protected Comment() {}
}