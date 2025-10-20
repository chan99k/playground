package chan99k.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OtpCodeUtilTest {
	@Test
	@DisplayName("6자리 숫자값이 나와야 함")
	void test01() {
		String s = OtpCodeUtil.generateOtpCode();

		assertThat(s.chars()).allMatch(Character::isDigit);
		assertThat(s.length()).isEqualTo(6);
	}

}