package chan99k.implementation;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Prgr_118667Test {
	private final Prgr_118667 sol = new Prgr_118667();

	@ParameterizedTest
	@MethodSource("defaultTestCases")
	@DisplayName("기본테스트케이스")
	void test(int[] queue1, int[] queue2, int expected) {
		assertThat(sol.solution(queue1, queue2)).isEqualTo(expected);
	}

	private static Stream<Arguments> defaultTestCases() {
		return Stream.of(
			Arguments.of(
				new int[] {3, 2, 7, 2},
				new int[] {4, 6, 5, 1},
				2
			),
			Arguments.of(
				new int[] {1, 2, 1, 2},
				new int[] {1, 10, 1, 2},
				7
			), Arguments.of(
				new int[] {1, 1},
				new int[] {1, 5},
				-1
			)
		);
	}
}