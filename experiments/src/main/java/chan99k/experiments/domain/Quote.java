package chan99k.experiments.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "quotes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quote extends AbstractWriteOnceEntity implements Authorable {

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

	// --- Business Methods ---

	/**
	 * 새로운 Quote를 생성합니다.
	 * Quote는 불변(Immutable) 객체이므로, 생성 시점에 모든 상태가 결정되어야 합니다.
	 *
	 * @param story           인용의 출처가 되는 Story
	 * @param author          인용을 생성한 작성자
	 * @param originalText    인용된 원본 텍스트
	 * @param textFragmentUrl 텍스트 위치로 이동할 URL Fragment
	 */
	public Quote(Story story, User author, String originalText, String textFragmentUrl) {
		Assert.notNull(story, "Story must not be null");
		Assert.notNull(author, "Author must not be null");
		Assert.hasText(originalText, "Original text must not be empty");

		this.story = story;
		this.author = author;
		this.originalText = originalText;
		this.textFragmentUrl = textFragmentUrl;
	}

}