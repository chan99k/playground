package chan99k.binary_search;

public class Leet_278 {
	public int firstBadVersion(int n) {
		int left = 1, right = n;

		while (left <= right) {
			int mid = left + (right - left) / 2;

			if (isBadVersion(mid)) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return left;
	}

	private boolean isBadVersion(int n){
		return true; // 실제로는 LeetCode 제공 API 호출
	}
}
