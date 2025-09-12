package chan99k.arrays;

import java.util.Arrays;
import java.util.Comparator;

public class Leet_3169 {
	public int countDays(int days, int[][] meetings) {
		// Arrays.sort(meetings, Comparator.comparingInt(o -> o[0]));
		Arrays.sort(meetings, new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});

		int count = 0;
		int start = meetings[0][0];
		int end = meetings[0][1];

		// 첫 구간 전 빈 날
		count += start - 1;

		for (int i = 1; i < meetings.length; i++) {
			int a = meetings[i][0];
			int b = meetings[i][1];

			if (a > end) {
				count += a - end - 1;
				end = b;
			} else {
				end = Math.max(end, b);
			}
		}

		// 마지막 구간 뒤 빈 날
		count += days - end;

		return count;
	}

	public static void main(String[] args) {
		var sol = new Leet_3169();
		System.out.println(sol.countDays(8, new int[][] {{3, 4}, {4, 8}, {2, 5}, {3, 8}}));
	}
}
