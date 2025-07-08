package chan99k.experiments.domain;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trust_scores")
public class TrustScore {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "content_id", nullable = false, unique = true)
	private Content content;

	@Column(nullable = false)
	private double score;

	/**
	 * '계산된 시점'이라는 도메인 의미를 가지는 필드
	 */
	@Column(nullable = false)
	private Instant calculatedAt;

	private String algorithmVersion;

	// 시스템이 직접 생성/수정하므로 Auditing 필드는 불필요
	// protected Instant createdAt;
	// protected Instant lastModifiedAt;
	protected TrustScore() {
	}
}