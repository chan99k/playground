package chan99k.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Prgr_92341Test {
	private final Prgr_92341 sol = new Prgr_92341();

	@ParameterizedTest
	@MethodSource("defaultTestCases")
	void test(int[] fees, String[] records, int[] expected) {
		int[] result = sol.solution(fees, records);

		assertArrayEquals(expected, result);
	}

	private static Stream<Arguments> defaultTestCases() {
		return Stream.of(
			Arguments.of(
				new int[] {180, 5000, 10, 600},
				new String[] {
					"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT",
					"07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN",
					"23:00 5961 OUT"
				},
				new int[] {14600, 34400, 5000}
			),
			Arguments.of(
				new int[] {120, 0, 60, 591},
				new String[] {
					"16:00 3961 IN", "16:00 0202 IN", "18:00 3961 OUT", "18:00 0202 OUT",
					"23:58 3961 IN"
				},
				new int[] {0, 591}
			),
			Arguments.of(
				new int[] {1, 461, 1, 10},
				new String[] {"00:00 1234 IN"},
				new int[] {14841}
			)
		);
	}
}
