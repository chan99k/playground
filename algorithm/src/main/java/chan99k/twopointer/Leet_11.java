package chan99k.twopointer;

public class Leet_11 {
	public int maxArea(int[] height) {
		int left = 0;
		int right = height.length - 1;
		int max = -1;

		while (left < right) {
			int h = Math.min(height[left], height[right]);
			int area = h * (right - left);
			max = Math.max(max, area);

			if (height[left] < height[right]) { // 더 낮은 쪽을 이동해야 커질 가능성이 있음
				left++;
			} else {
				right--;
			}
		}

		return max;
	}
}

