package chan99k.experiments.domain;

import org.springframework.util.Assert;

import jakarta.persistence.Embeddable;

/**
 * 이메일을 나타내는 불변 값 객체(Value Object)입니다.
 * 생성 시점에 유효성 검증을 수행합니다.
 */
@Embeddable
public record Email(String address) {
	public Email {
		Assert.hasText(address, "Email must not be empty");
		Assert.isTrue(address.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"), "Invalid email format");
	}
}