package chan99k.arrays;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class N24PhantPlaypTest {
	N24PhantPlayp sol;

	@BeforeEach
	void setUp() {
		sol = new N24PhantPlayp();
	}

	@ParameterizedTest(name = "[{index}] emotions={0}, orders={1} -> results={2}")
	@MethodSource("cases")
	@DisplayName("test01")
	void test01(int[] emotions, int[] orders, int[] results) {
		var answer = sol.solution(emotions, orders);
		assertThat(answer).isEqualTo(results);
	}

	static Stream<Arguments> cases() {
		return Stream.of(
			Arguments.of(new int[] {2, 3, 1, 2}, new int[] {3, 1, 2, 1, 4, 1}, new int[] {4, 2, 2, 2, 2, 1}),
			Arguments.of(new int[] {5, 5, 5}, new int[] {1, 2, 1, 2, 3}, new int[] {3, 3, 3, 3, 3}),
			Arguments.of(new int[] {5, 5, 5}, new int[] {1, 2, 1, 2, 1}, new int[] {3, 3, 3, 3, 2}),
			Arguments.of(new int[] {2, 1, 3, 4, 3}, new int[] {2, 2, 2, 2, 5, 5, 5}, new int[] {5, 4, 2, 1, 0, 0, 0})
		);

	}

}