package chan99k.binsearch;

public class Leet_33 {
	public int search(int[] nums, int target) {
		int left = 0;
		int right = nums.length - 1;
		while (left < right) {
			int mid = left + (right - left) / 2;
			if (nums[mid] > nums[right])
				left = mid + 1;
			else
				right = mid;
		}
		int pivot = left;

		if (target == nums[pivot])
			return pivot;
		else if (target <= nums[nums.length - 1]) {
			// 피벗 기준 오른쪽 탐색
			return binSearch(nums, target, pivot, nums.length - 1);
		} else {
			// 피벇 기준 왼쪽 탐색
			return binSearch(nums, target, 0, pivot);
		}
	}

	private int binSearch(int[] nums, int target, int left, int right) {
		if (left > right) {
			return -1;
		}
		int mid = left + (right - left) / 2;
		if (nums[mid] == target) {
			return mid;
		} else if (nums[mid] > target) {
			return binSearch(nums, target, left, mid - 1);
		} else {
			return binSearch(nums, target, mid + 1, right);
		}
	}
}
