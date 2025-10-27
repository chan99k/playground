package chan99k.binary_search;

public class Leet_875 {
	public int minEatingSpeed(int[] piles, int h) {
		long total = 0;
		for (int pile : piles) {
			total += pile;
		}
		// 하한선을 평균 속도 로 지정
		// 상한선의 경우,  piles.length - 각 더미- 마다 최소 1시간씩은 소요됨. 즉 piles.length 시간은 기본으로 필요함 -> 남는 시간은 h - piles.length 시간
		int l = (int)((total - 1L) / h) + 1, r = (int)((total - piles.length) / (h - piles.length + 1)) + 1;

		while (l <= r) {
			int mid = l + (r - l) / 2;
			if (mid == 0) {
				l = 1;
				continue;
			}

			if (cond(piles, mid, h)) { //  mid 시간 안에 먹을 수 있으면
				r = mid - 1;           // 더 짧은 시간안에도 먹을 수 있는지 확읺
			} else {
				l = mid + 1;  // 못 먹으니까 더 빠르게 먹기
			}
		}

		return l;
	}

	private boolean cond(int[] piles, int k, int h) {
		long curr = 0;

		for (int pile : piles) {
			curr += (pile + k - 1) / k;
			if (curr > h)
				return false;
		}

		return true;
	}
}
