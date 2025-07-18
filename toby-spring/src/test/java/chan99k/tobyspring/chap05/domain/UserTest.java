package chan99k.tobyspring.chap05.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
	User user;

	@BeforeEach
	public void setUp() {
		user = new User();
	}

	@Test
	public void upgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() == null) {
				continue;
			}
			user.setLevel(level);
			user.upgradeLevel();
			assertThat(user.getLevel()).isEqualTo(level.nextLevel());
		}
	}

	@Test
	@DisplayName("다음 레벨이 없는 경우 예외 발생")
	public void cannotUpgradeLevel() {
		Level[] levels = Level.values();
		for (Level level : levels) {
			if (level.nextLevel() != null) {
				continue;
			}

			user.setLevel(level);
		}
		assertThrows(IllegalStateException.class, () -> user.upgradeLevel());
	}
}
