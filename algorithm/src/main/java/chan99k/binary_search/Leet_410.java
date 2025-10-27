package chan99k.binary_search;

public class Leet_410 {
	// 범위 : 서브배열의 합
	// 최솟값 : 한 개씩 나눴을 때의 최솟값
	// 최댓값 : 가능한 최대한 큰 값 -> 전체 배열의 합
	// cond : 특정 값을 찍어 봤을 때, m 으로 나눌 수 있나?
	public int splitArray(int[] nums, int k) {
		int l = -1;
		int r = 0;
		for (int n : nums) {
			l = Math.max(n, l);
			r += n;
		}

		while (l <= r) {
			int mid = l + (r - l) / 2;
			if (cond(mid, nums, k)) {
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}

		return l;
	}

	// k 개 만큼의 서브 배열로 나눌 수 있는가?
	// 현재 서브 배열의 합이 limit 를 넘어가면 서브배열 카운트를 증가시키고,
	// 서브배열의 합을 현재값으로 초기화 (새로운 서브배열 시작)
	// 최종적으로 서브배열 개수가 k 이하이면 조건 만족
	private boolean cond(int limit, int[] nums, int k) {
		int currSub = 0;
		int subCount = 1;
		for (int num : nums) {
			if (currSub + num > limit) {
				subCount++;
				currSub = num;
				if (num > limit)
					return false;
			} else {
				currSub += num;
			}
		}

		return subCount <= k;
	}
}
