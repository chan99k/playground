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
@Table(name = "critiques")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Critique extends AbstractEntity implements Authorable {

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

	// --- Business Methods ---

	/**
	 * 새로운 Critique를 생성합니다.
	 * @param quote        비평의 대상이 되는 Quote
	 * @param author       비평을 작성한 사용자
	 * @param critiqueText 비평 내용
	 */
	public Critique(Quote quote, User author, String critiqueText) {
		Assert.notNull(quote, "Quote must not be null");
		Assert.notNull(author, "Author must not be null");
		Assert.hasText(critiqueText, "Critique text must not be empty");

		this.quote = quote;
		this.author = author;
		this.critiqueText = critiqueText;
	}

	/**
	 * 비평의 내용을 수정합니다.
	 * @param newCritiqueText 새로운 비평 내용
	 */
	public void update(String newCritiqueText) {
		Assert.hasText(newCritiqueText, "Critique text must not be empty");
		this.critiqueText = newCritiqueText;
	}

	// isOwnedBy 메서드는 Authorable 인터페이스의 default 메서드로 대체되었으므로 제거합니다.
}