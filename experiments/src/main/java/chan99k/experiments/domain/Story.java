package chan99k.experiments.domain;

import java.util.UUID;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "stories")
public class Story extends Content {

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

	// --- Business Methods ---

	/**
	 * 새로운 Story를 생성합니다.
	 * 필수 값들이 누락되지 않도록 생성자에서 강제합니다.
	 */
	public Story(UUID id, User author, String title, String markdownContent) {
		super(id, author); // 부모 클래스의 생성자 호출
		Assert.hasText(title, "Title must not be empty");
		Assert.hasText(markdownContent, "Content must not be empty");
		this.title = title;
		this.markdownContent = markdownContent;
	}

	/**
	 * Story의 내용을 수정합니다.
	 * 이 메서드는 하나의 비즈니스 트랜잭션을 나타냅니다.
	 *
	 * @param newTitle 새로운 제목
	 * @param newSummary 새로운 요약
	 * @param newMarkdownContent 새로운 본문
	 * @param newThumbnailUrl 새로운 썸네일 URL
	 */
	public void update(String newTitle, String newSummary, String newMarkdownContent, String newThumbnailUrl) {
		Assert.hasText(newTitle, "Title must not be empty");
		Assert.hasText(newMarkdownContent, "Content must not be empty");

		this.title = newTitle;
		this.summary = newSummary;
		this.markdownContent = newMarkdownContent;
		this.thumbnailUrl = newThumbnailUrl;
	}
}