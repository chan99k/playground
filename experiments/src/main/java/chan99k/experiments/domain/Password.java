package chan99k.experiments.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Embeddable;

/**
 * 비밀번호를 나타내는 불변 값 객체(Value Object)입니다.
 * 비밀번호 비교 로직을 캡슐화합니다.
 */
@Embeddable
public record Password(String encoded) {
	public Password {
		Assert.hasText(encoded, "Password must not be empty");
		// 여기에 비밀번호 복잡도(길이, 특수문자 등) 검증 로직을 추가할 수 있습니다.
	}

	/**
	 * 입력된 비밀번호가 현재 비밀번호(암호화된)와 일치하는지 확인합니다.
	 * @param rawPassword 평문 비밀번호
	 * @param encoder 비교에 사용할 인코더
	 * @return 일치 여부
	 */
	public boolean matches(String rawPassword, PasswordEncoder encoder) {
		return encoder.matches(rawPassword, this.encoded);
	}
}