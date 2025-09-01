package chan99k.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 식물의 기분이 상하는 시점이 정해져 있다.
 * x-y 축을 바꿔서 생각해보면 좋다.
 * 사이클 하나를 지날때마다 로우가 없어지는 형태
 * 물을 주면 한 사이클이 밀려나는 개념( 언제 없어지는 지 힙 안에서)
 * 사이클 횟수를 키로, 물을 주면 초기값 3에 3만큼 더해서 사이클 6으로 밀기
 * 리버스 맵도 하나 더 필요하다. LRU 캐시 구현할 때 많이 쓰는 방식
 */
public class N24PhantPlayp {

	public int[] solution(int[] emotions, int[] orders) {
		int[] initial = Arrays.copyOf(emotions, emotions.length);
		int count = Math.toIntExact(Arrays.stream(initial).filter(p -> p > 0).count());

		LinkedList<Integer> remaining = copyPlants(emotions);

		int[] result = new int[orders.length];
		result[0] = count;

		invokeCycle(emotions, orders, initial, count, remaining, result);

		return result;
	}

	private static void invokeCycle(int[] emotions, int[] orders, int[] initial, int count, LinkedList<Integer> remaining,
		int[] result) {
		for (int i = 0; i < orders.length; i++) {        // i+1 번째 사이클
			var watered = orders[i] - 1;                // 물을 준 식물의 인덱스

			if (initial[watered] > 0) {
				emotions[watered] = initial[watered];    // 초기 상태로 롤백
			}

			count = depress(remaining.iterator(), initial, emotions, count, watered);

			result[i] = count;
		}
	}

	private static LinkedList<Integer> copyPlants(int[] emotions) {
		LinkedList<Integer> remaining = new LinkedList<>();
		for (int i = 0; i < emotions.length; i++) {
			remaining.add(i);
		}
		return remaining;
	}

	private static int depress(Iterator<Integer> it, int[] initialState, int[] emotions, int count, int watered) {

		while (it.hasNext()) {
			int i = it.next();
			if (initialState[i] != 0 && emotions[i] > 0 && i != watered) {  // 물을 준 식물은 이번 사이클에는 스킵
				emotions[i] -= 1;     // 기분 상하기
			}

			if (emotions[i] == 0 && initialState[i] != 0) {    // 기분이 완전히 상한 식물은
				initialState[i] = 0;  // 초기 상태를 0으로 초기화
				count--;              // 기분 좋은 식물 카운트 감소
				it.remove();
			}
		}

		return count;
	}
}
