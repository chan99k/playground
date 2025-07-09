package chan99k.experiments.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

/**
 * 모든 구체적인 콘텐츠 타입(예: Story, RestaurantReview)의 부모가 되는 추상 엔티티입니다.
 * 공통적인 속성(ID, 작성자)과 행위(소유권 확인)를 정의합니다.
 *
 * @Inheritance(strategy = InheritanceType.JOINED)
 * - 상속 관계 매핑 전략으로 '조인 전략'을 사용합니다.
 * - 이유:
 *   1. **데이터 정규화**: 각 하위 엔티티(Story, RestaurantReview 등)는 자신만의 테이블을 가지며,
 *      공통 속성은 부모 테이블(contents)에 저장됩니다. 이는 데이터 중복을 없애고 스키마를 명확하게 합니다.
 *   2. **데이터 무결성**: 각 하위 테이블의 필드에 NOT NULL과 같은 제약조건을 명확하게 설정할 수 있습니다.
 * - 트레이드오프: 다형적 쿼리(예: 모든 Content 조회) 시 조인이 발생하여 약간의 성능 저하가 있을 수 있으나,
 *   데이터 모델의 명확성과 무결성 확보의 이점이 더 크다고 판단하여 이 전략을 채택합니다.
 */
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contents")
public abstract class Content extends AbstractEntity implements Authorable {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	protected Content() {
	}

	protected Content(UUID id, User author) {
		this.id = id;
		this.author = author;
	}

	// isOwnedBy 메서드는 Authorable 인터페이스의 default 메서드로 대체되었으므로 제거합니다.
}