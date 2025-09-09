package chan99k.map;

import java.util.HashMap;
import java.util.Map;
// TODO :: freq array 방법으로 다시 풀기
public class Leet_3185 {
	public long countCompleteDayPairs(int[] hours) {
		Map<Integer, Integer> map = new HashMap<>();
		long count = 0;

		for (int hour : hours) {
			int remainder = hour % 24;
			// remainder 가 0 인 경우 (hour가 24의 배수인 경우),
			// complement도 똑같이 0 이어야 완전한 날이 되므로, 이를 위해 모듈러 연산
			// (모듈러 연산이 없으면 comp 가 24가 되어 버림, map에는 24가 절대 저장되지 않으므로 카운트가 안됨)
			int complement = (24 - remainder) % 24;

			count += map.getOrDefault(complement, 0); // 이미 있다면 ? 현재 remainder 값과 완전한 날이므로 +1 : += 0

			map.put(remainder, map.getOrDefault(remainder, 0) + 1); // 중복을 허락하므로 && i < j 인 경우만 맵에서 확인할 수 있으므로
		}

		return count;
	}

	public static void main(String[] args) {
		var sol = new Leet_3185();
		System.out.println(sol.countCompleteDayPairs(new int[] {12, 12, 30, 24, 24}));
	}
}


/*
 * 	public long countCompleteDayPairs(int[] hours) {
		long count = 0;
		for (int i = 0; i < hours.length - 1; i++){
			int hi = hours[i] % 24;
			for (int j = i+1; j < hours.length; j++){
				int hj = hours[j] % 24;
				if((hi+hj)%24 == 0){
					count++;
				}
			}
		}
		return count;
	}
 */