package chan99k.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prgr_150370 {
	public int[] solution(String today, String[] terms, String[] privacies) {
		List<Integer> result = new ArrayList<>();
		int currDay = convertToDays(today);

		// 약관 종류 -> 유효 개월 수
		Map<String, Integer> termsMap = new HashMap<>();
		for (String term : terms) {
			String[] split = term.split(" ");
			termsMap.put(split[0], Integer.parseInt(split[1]));
		}

		// 각 개인정보 수집일 + 유효기간과 오늘 날짜 비교
		for (int i = 0; i < privacies.length; i++) {
			String[] split = privacies[i].split(" ");
			int collectedDay = convertToDays(split[0]);
			String kind = split[1];
			int expireDay = collectedDay + termsMap.get(kind) * 28;

			if (expireDay <= currDay) {
				result.add(i + 1); // 1-based index
			}
		}

		return result.stream().mapToInt(Integer::intValue).toArray();
	}

	private int convertToDays(String date) {
		String[] parts = date.split("\\.");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);
		return year * 12 * 28 + month * 28 + day;
	}
}