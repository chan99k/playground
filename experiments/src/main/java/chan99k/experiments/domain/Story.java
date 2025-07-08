package chan99k.experiments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "stories")
public class Story extends Content { // Content 상속

	// id, author, createdAt, updatedAt 필드는 부모 클래스로 이동하여 제거

	@Column(nullable = false, length = 200)
	private String title;

	@Column(length = 500)
	private String summary;

	@Lob
	@Column(nullable = false)
	private String markdownContent;

	private String thumbnailUrl;

	protected Story() {
		super();
	}
}