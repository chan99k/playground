package chan99k.twopointer;

import java.util.Arrays;

public class Leet_167 {
	public int[] twoSum(int[] numbers, int target) {
		return twoPointer(numbers, target, 0, numbers.length - 1);
	}
	// StackOverflow 위험 있음, 다만 재귀 연습용으로 사용
	private int[] twoPointer(int[] nums, int target, int left, int right) {
		if (left > right)
			return new int[] {};

		int twosome = nums[left] + nums[right];
		if (target == twosome)
			return new int[] {left + 1, right + 1};
		else if (target > twosome)
			return twoPointer(nums, target, left + 1, right);
		else
			return twoPointer(nums, target, left, right - 1);
	}

	public static void main(String[] args) {
		var sol = new Leet_167();
		System.out.println(Arrays.toString(sol.twoSum(new int[] {2, 7, 11, 15}, 9)));
	}
}
