package chan99k.tobyspring.chap06.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chan99k.tobyspring.chap05.domain.Level;
import chan99k.tobyspring.chap05.domain.User;

public class UserTest {
	chan99k.tobyspring.chap05.domain.User user;

	@BeforeEach
	public void setUp() {
		user = new User();
	}

	@Test
	public void upgradeLevel() {
		chan99k.tobyspring.chap05.domain.Level[] levels = chan99k.tobyspring.chap05.domain.Level.values();
		for (chan99k.tobyspring.chap05.domain.Level level : levels) {
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
		chan99k.tobyspring.chap05.domain.Level[] levels = chan99k.tobyspring.chap05.domain.Level.values();
		for (Level level : levels) {
			if (level.nextLevel() != null) {
				continue;
			}

			user.setLevel(level);
		}
		assertThrows(IllegalStateException.class, () -> user.upgradeLevel());
	}
}
