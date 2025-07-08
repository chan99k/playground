package chan99k.experiments.domain;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/**
 * 생성/수정 시간 및 생성/수정 주체를 관리하기 위한 추상 엔티티.
 * Instant 타입을 사용하여 타임존에 독립적인 시간 저장을 보장한다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 활성화
public abstract class AbstractEntity {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private Instant lastModifiedAt;

	/**
	 * User의 ID 타입이 UUID 이므로 UUID로 매핑
	 */
	@CreatedBy
	@Column(columnDefinition = "BINARY(16)", updatable = false)
	private UUID createdBy;

	@LastModifiedBy
	@Column(columnDefinition = "BINARY(16)")
	private UUID lastModifiedBy;

}