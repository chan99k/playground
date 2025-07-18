package chan99k.binsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prgr_72412 {
	// 가능한 모든 조합과 해당하는 점수
	private final Map<String, List<Integer>> resumeMap = new HashMap<>();

	public int[] solution(String[] info, String[] query) {
		Arrays.stream(info)
			.map(s -> s.split(" "))
			.forEach(parts -> {
				// 와일드카드를 포함한 16가지의 키 조합을 만들기
				String[] keys = generateKeys(parts[0], parts[1], parts[2], parts[3]);
				int score = Integer.parseInt(parts[4]);
				for (String key : keys) {
					resumeMap.computeIfAbsent(key, k -> new ArrayList<>()).add(score);
				}
			});

		resumeMap.values().forEach(Collections::sort);

		return Arrays.stream(query)
			.map(this::parseQuery)
			.mapToInt(q -> {
				List<Integer> scores = resumeMap.getOrDefault(q.key, Collections.emptyList());
				return scores.size() - lowerBound(scores, q.score);
			})
			.toArray();
	}

	/**
	 * 각 필드마다 실제 값 또는 -를 넣은 배열을 만들어서
	 * 중첩 flatMap, 2⁴ = 16개의 조합 생성
	 */
	private String[] generateKeys(String lang, String role, String level, String food) {
		String[] langs = {lang, "-"};
		String[] roles = {role, "-"};
		String[] levels = {level, "-"};
		String[] foods = {food, "-"};

		return Arrays.stream(langs)
			.flatMap(l -> Arrays.stream(roles)
				.flatMap(r -> Arrays.stream(levels)
					.flatMap(c -> Arrays.stream(foods)
						.map(f -> String.join("_", l, r, c, f)))))
			.toArray(String[]::new);
	}

	private Query parseQuery(String q) {
		String[] parts = q.replace(" and", "").split(" ");
		return new Query(String.join("_", parts[0], parts[1], parts[2], parts[3]),
			Integer.parseInt(parts[4]));
	}

	/**
	 * 이분 탐색으로, target 이상이 처음 등장하는 인덱스를 반환
	 * 전체 수 - lowerBound 위치 = 조건을 만족하는 지원자 수
	 */
	private int lowerBound(List<Integer> list, int target) {
		int left = 0, right = list.size();
		while (left < right) {
			int mid = (left + right) / 2;
			if (list.get(mid) < target)
				left = mid + 1;
			else
				right = mid;
		}
		return left;
	}

	private static class Query {
		final String key;
		final int score;

		Query(String key, int score) {
			this.key = key;
			this.score = score;
		}
	}
}
