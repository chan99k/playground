package chan99k.experiments.domain;

import java.time.Instant;
import java.util.UUID;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "trust_scores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrustScore extends AbstractEntity {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	// 하나의 콘텐츠는 오직 하나의 신뢰도 점수만 가집니다.
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false, unique = true)
	private Content content;

	@Column(nullable = false)
	private double score;

	@Column(nullable = false)
	private Instant calculatedAt;

	@Column(nullable = false)
	private String algorithmVersion;

	// --- Business Methods ---

	/**
	 * 새로운 TrustScore를 생성합니다.
	 * 시스템에 의해 최초로 점수가 계산될 때 호출됩니다.
	 */
	public TrustScore(UUID id, Content content, double score, String algorithmVersion) {
		Assert.notNull(id, "ID must not be null");
		Assert.notNull(content, "Content must not be null");
		Assert.hasText(algorithmVersion, "Algorithm version must not be empty");

		this.id = id;
		this.content = content;
		this.score = score;
		this.algorithmVersion = algorithmVersion;
		this.calculatedAt = Instant.now();
	}

	/**
	 * 신뢰도 점수를 새로운 값으로 갱신합니다.
	 * 이 메서드는 "점수를 재계산한다"는 비즈니스 행위를 캡슐화합니다.
	 *
	 * @param newScore           새롭게 계산된 점수
	 * @param newAlgorithmVersion 점수 계산에 사용된 알고리즘 버전
	 */
	public void update(double newScore, String newAlgorithmVersion) {
		Assert.hasText(newAlgorithmVersion, "Algorithm version must not be empty");

		this.score = newScore;
		this.algorithmVersion = newAlgorithmVersion;
		this.calculatedAt = Instant.now(); // 점수 갱신 시점을 기록합니다.
	}
}