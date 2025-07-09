package chan99k.experiments.domain;

import java.util.UUID;

import org.springframework.util.Assert;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	// 닉네임 역할을 하므로 nullable로 변경
	@Column(nullable = true, length = 50)
	private String name;

	@Embedded
	@AttributeOverride(name = "address", column = @Column(name = "email", nullable = false, unique = true))
	private Email email;

	@Embedded
	// Password record의 필드명이 'encoded'이므로 name 속성을 정확히 지정
	@AttributeOverride(name = "encoded", column = @Column(name = "password", nullable = false))
	private Password password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private UserRole role;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private UserStatus status;

	// name이 nullable이므로 생성자에서 제거하고, 필수 값만 받도록 수정
	public User(UUID id, Email email, Password password, UserRole role) {
		Assert.notNull(id, "ID must not be null");
		Assert.notNull(email, "Email must not be null");
		Assert.notNull(password, "Password must not be null");
		Assert.notNull(role, "Role must not be null");

		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
		this.status = UserStatus.PENDING; // 생성 시 기본 상태는 PENDING
	}

	// --- Business Methods ---

	/**
	 * 입력된 비밀번호가 사용자의 비밀번호와 일치하는지 확인합니다.
	 * 로그인은 ACTIVE 상태의 사용자만 가능합니다.
	 */
	public boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
		if (this.status != UserStatus.ACTIVE) {
			return false;
		}
		return this.password.matches(rawPassword, passwordEncoder);
	}

	/**
	 * 사용자의 닉네임을 변경합니다.
	 * @param newName 새로운 닉네임
	 */
	public void changeName(String newName) {
		Assert.notNull(newName, "New nickname must not be null");
		this.name = newName;
	}

	/**
	 * 사용자의 비밀번호를 변경합니다.
	 * 참고: 이전 비밀번호 확인 로직은 이 메서드를 호출하는 서비스 계층의 책임입니다.
	 * @param newPassword 새로운 비밀번호 값 객체
	 */
	public void changePassword(Password newPassword) {
		Assert.notNull(newPassword, "New password must not be null");
		this.password = newPassword;
	}

	/**
	 * 사용자를 활성화합니다. (예: 이메일 인증 완료 후)
	 * PENDING 상태에서만 활성화가 가능합니다.
	 */
	public void activate() {
		if (this.status == UserStatus.PENDING) {
			this.status = UserStatus.ACTIVE;
		}
	}

	/**
	 * 사용자를 비활성화(탈퇴) 처리합니다.
	 * 사용자의 데이터는 삭제되지 않고, 상태만 변경됩니다.
	 */
	public void deactivate() {
		this.status = UserStatus.DEACTIVATED;
	}
}