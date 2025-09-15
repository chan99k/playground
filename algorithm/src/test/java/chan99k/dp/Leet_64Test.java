package chan99k.dp;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class Leet_64Test {
	private Leet_64 target = new Leet_64();

	@Test
	@DisplayName("test01")
	void test01() {
		assertThat(target.minPathSum(new int[][] {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}})).isEqualTo(7);
	}

	@Test
	@DisplayName("test02")
	public void test02() {
		assertThat(target.minPathSum(new int[][] {{1, 2, 3}, {4, 5, 6}})).isEqualTo(12);
	}

	// @Test
	// @DisplayName("test03")
	// public void test03() {
	// 	assertThat(0).isEqualTo(0);
	// }

}
