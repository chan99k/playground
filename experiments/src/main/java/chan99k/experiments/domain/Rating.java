package chan99k.experiments.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ratings",
	uniqueConstraints = @UniqueConstraint(columnNames = {"content_id", "user_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating extends AbstractWriteOnceEntity implements Authorable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false)
	private Content content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private double score;

	// --- Business Methods ---

	/**
	 * 새로운 Rating을 생성합니다.
	 * 생성 시점에 점수(score)가 유효한 범위(1.0 ~ 5.0)에 있는지 검증하여
	 * 도메인의 일관성을 보장합니다.
	 *
	 * @param content 평점 대상 콘텐츠
	 * @param user    평점을 남긴 사용자
	 * @param score   사용자가 부여한 점수
	 */
	public Rating(Content content, User user, double score) {
		Assert.notNull(content, "Content must not be null");
		Assert.notNull(user, "User must not be null");
		Assert.isTrue(score >= 1.0 && score <= 5.0, "Score must be between 1.0 and 5.0");

		this.content = content;
		this.user = user;
		this.score = score;
	}

	/**
	 * Authorable 인터페이스의 계약을 이행합니다.
	 * 이 엔티티에서는 'user' 필드가 작성자 역할을 합니다.
	 * @return 작성자 User 객체
	 */
	@Override
	public User getAuthor() {
		return this.user;
	}

}