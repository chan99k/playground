package chan99k.implementation;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Prgr_118667 {
	public int solution(int[] queue1, int[] queue2) {
		long currentSum = Arrays.stream(queue1).asLongStream().sum();
		long totalSum = currentSum + Arrays.stream(queue2).asLongStream().sum();

		if (totalSum % 2 != 0) {
			return -1;
		}

		int[] combined = IntStream.concat(Arrays.stream(queue1), Arrays.stream(queue2)).toArray();

		return getMinimumCount(totalSum, queue1.length, currentSum, combined);

	}

	private static Integer getMinimumCount(long totalSum, int len, long currentSum, int[] combined) {
		long target = totalSum / 2;

		int p1 = 0;
		int p2 = len;
		int count = 0;

		int limit = len * 4;

		while (count <= limit) {
			if (currentSum == target) {
				return count;
			}
			if (currentSum < target) {
				if (p2 >= len * 2)
					break;
				currentSum += combined[p2];
				p2++;
			} else {
				currentSum -= combined[p1];
				p1++;
			}
			count++;
		}
		return -1;
	}
}
