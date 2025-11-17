package chan99k;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Facebook_OnesInTheRangeTest {

	@Test
	@DisplayName("test01")
	void test01() {
		Facebook_OnesInTheRange sol = new Facebook_OnesInTheRange(new int[] {0, 0, 1, 0, 1});

		assertThat(sol.numOfOnes(2, 4)).isEqualTo(2);
	}

	@Test
	@DisplayName("test02")
	void test02() {
		Facebook_OnesInTheRange sol = new Facebook_OnesInTheRange(new int[] {0, 1, 1, 0, 0, 1, 1, 1});

		assertThat(sol.numOfOnes(2, 6)).isEqualTo(3);
	}

	@Test
	@DisplayName("test03")
	void test03() {
		Facebook_OnesInTheRange sol = new Facebook_OnesInTheRange(new int[] {0, 1, 1, 0, 0, 1, 1, 1});

		assertThat(sol.numOfOnes(1, 7)).isEqualTo(5);
	}

	@Test
	@DisplayName("naive 방법 테스트")
	void testNaive() {
		Facebook_OnesInTheRange sol = new Facebook_OnesInTheRange(new int[] {0, 1, 1, 0, 0, 1, 1, 1});

		assertThat(sol.numOfOnesNaive(2, 6)).isEqualTo(3);
		assertThat(sol.numOfOnesNaive(1, 7)).isEqualTo(5);
		assertThat(sol.numOfOnes(2, 6)).isEqualTo(sol.numOfOnesNaive(2, 6));
	}

	@Test
	@DisplayName("비트 최적화 방법 테스트")
	void testBitOptimized() {
		Facebook_OnesInTheRange sol = new Facebook_OnesInTheRange(new int[] {0, 1, 1, 0, 0, 1, 1, 1});

		assertThat(sol.numOfOnesBitOptimized(2, 6)).isEqualTo(3);
		assertThat(sol.numOfOnesBitOptimized(1, 7)).isEqualTo(5);
		assertThat(sol.numOfOnes(2, 6)).isEqualTo(sol.numOfOnesBitOptimized(2, 6));
		assertThat(sol.numOfOnes(0, 4)).isEqualTo(sol.numOfOnesBitOptimized(0, 4));
	}

}