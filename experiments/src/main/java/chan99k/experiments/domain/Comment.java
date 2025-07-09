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
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends AbstractEntity implements Authorable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	// 회원이 작성한 경우에만 값이 존재 (비회원 댓글은 null)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = true)
	private User author;

	@Lob
	@Column(nullable = false)
	private String text;

	// --- 비회원(Guest) 정보 ---
	@Column(length = 50)
	private String guestName;

	private String guestPassword; // 해시된 비밀번호

	// --- Business Methods ---

	// 1. 회원용 생성자
	public Comment(Content content, User author, String text) {
		Assert.notNull(content, "Content must not be null");
		Assert.notNull(author, "Author must not be null");
		Assert.hasText(text, "Text must not be empty");

		this.content = content;
		this.author = author;
		this.text = text;
	}

	// 2. 비회원용 생성자
	public Comment(Content content, String guestName, String hashedGuestPassword, String text) {
		Assert.notNull(content, "Content must not be null");
		Assert.hasText(guestName, "Guest name must not be empty");
		Assert.hasText(hashedGuestPassword, "Guest password must not be empty");
		Assert.hasText(text, "Text must not be empty");

		this.content = content;
		this.guestName = guestName;
		this.guestPassword = hashedGuestPassword;
		this.text = text;
	}

	/**
	 * 댓글의 내용을 수정합니다.
	 * @param newText 새로운 댓글 내용
	 */
	public void update(String newText) {
		Assert.hasText(newText, "Text must not be empty");
		this.text = newText;
	}

	// isOwnedBy 메서드는 Authorable 인터페이스의 default 메서드로 대체되었으므로 제거합니다.

	/**
	 * 이 댓글이 주어진 비회원 정보와 일치하는지 확인합니다.
	 * @param guestName     확인할 비회원 이름
	 * @param rawPassword   확인할 평문 비밀번호
	 * @param encoder       비밀번호 비교에 사용할 인코더
	 * @return 정보가 일치하면 true
	 */
	public boolean verifyGuestOwnership(String guestName, String rawPassword, PasswordEncoder encoder) {
		// 회원 댓글이거나, 입력 정보가 없으면 false
		if (this.author != null || guestName == null || rawPassword == null) {
			return false;
		}
		// 이름이 일치하고, 비밀번호가 일치하는지 확인
		return this.guestName.equals(guestName) && encoder.matches(rawPassword, this.guestPassword);
	}
}