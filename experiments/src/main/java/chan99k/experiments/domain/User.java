package chan99k.experiments.domain;

import jakarta.persistence.*; // JPA 어노테이션 import
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users") // 데이터베이스 테이블명을 'users'로 명시적으로 지정
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)") // UUID를 BINARY(16) 타입으로 저장하여 공간 효율성 및 인덱스 성능 향상
    private UUID id;

    @Column(nullable = false, length = 50) // not-null 제약조건 및 길이 제한
    private String name;

    @Column(nullable = false, unique = true) // not-null 및 unique 제약조건
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장 (e.g., "AMBASSADOR")
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Column(nullable = false, updatable = false) // 생성 시에만 값이 들어가고 업데이트되지 않음
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // JPA는 기본 생성자를 필요로 합니다.
    protected User() {}
}