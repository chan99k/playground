package chan99k.binary_search;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Prgr_72412Test {
	Prgr_72412 sol;

	static Stream<Arguments> provideTestCases() {
		return Stream.of(
			org.junit.jupiter.params.provider.Arguments.of(
				new String[] {
					"java backend junior pizza 150",
					"python frontend senior chicken 210",
					"python frontend senior chicken 150",
					"cpp backend senior pizza 260",
					"java backend junior chicken 80",
					"python backend senior chicken 50"
				},
				new String[] {
					"java and backend and junior and pizza 100",
					"python and frontend and senior and chicken 200",
					"cpp and - and senior and pizza 250",
					"- and backend and senior and - 150",
					"- and - and - and chicken 100",
					"- and - and - and - 150"
				},
				new int[] {1, 1, 1, 1, 2, 4}
			),
			org.junit.jupiter.params.provider.Arguments.of(
				new String[] {
					"java backend senior pizza 200",
					"python backend senior pizza 180"
				},
				new String[] {
					"java and backend and senior and pizza 100",
					"python and backend and senior and pizza 190"
				},
				new int[] {1, 0}
			),
			org.junit.jupiter.params.provider.Arguments.of(
				new String[] {
					"cpp frontend junior chicken 50",
					"cpp frontend junior pizza 70",
					"cpp frontend junior pizza 90"
				},
				new String[] {
					"cpp and frontend and junior and pizza 60",
					"- and - and - and - 60"
				},
				new int[] {2, 2}
			)
		);
	}

	@BeforeEach
	void setUp() {
		sol = new Prgr_72412();
	}

	@ParameterizedTest(name = "[{index}]")
	@MethodSource("provideTestCases")
	@DisplayName("기본 테스트 케이스")
	void test(String[] info, String[] query, int[] expected) {
		var actual = sol.solution(info, query);
		assertThat(actual).isEqualTo(expected);
	}
}