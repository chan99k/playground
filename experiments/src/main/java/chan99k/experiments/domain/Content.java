package chan99k.experiments.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contents")
public abstract class Content extends AbstractEntity { // 변경

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	// 'author' 필드는 'createdBy'와 역할이 겹칠 수 있으나,
	// '작성자'라는 명시적인 도메인 의미를 가지므로 유지합니다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	// createdAt, updatedAt 필드 제거

	protected Content() {}

	public UUID getId() {
		return id;
	}

	public User getAuthor() {
		return author;
	}
}