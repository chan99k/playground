package chan99k.arrays;

import java.util.Arrays;

public class N24PhantPlayp {

	public int[] solution(int[] emotions, int[] orders) {
		int[] initial = Arrays.copyOf(emotions, emotions.length);
		int count = Math.toIntExact(Arrays.stream(initial).filter(p -> p > 0).count());

		int[] result = new int[orders.length];
		result[0] = count;

		for (int i = 0; i < orders.length; i++) {        // i+1 번째 사이클
			var watered = orders[i] - 1;                // 물을 준 식물의 인덱스

			if (initial[watered] > 0) {
				emotions[watered] = initial[watered];    // 초기 상태로 롤백
			}

			count = depress(initial, emotions, count, watered);

			result[i] = count;
		}

		return result;
	}
	// FIXME : 매번 전체 집합을 순회할 필요 없이, 기분 완전히 상한 식물은 스킵하도록 해야 하지 않을까
	private int depress(int[] initialState, int[] emotions, int count, int watered) {
		for (int i = 0; i < initialState.length; i++) {
			if (initialState[i] != 0 && emotions[i] > 0 && i != watered) {  // 물을 안줬지만 아직 기분 좋은 식물만 (물을 준 식물은 스킵)
				emotions[i] -= 1;     // 기분 상하기
			}

			if (emotions[i] == 0 && initialState[i] != 0) {    // 기분이 완전히 상한 식물은
				initialState[i] = 0;  // 초기 상태를 0으로 초기화
				count--;              // 기분 좋은 식물 카운트 감소
			}
		}
		return count;
	}
}
