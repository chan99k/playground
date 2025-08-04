package chan99k.twopointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Boj_30804 {
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] tanghuru = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		invoke(tanghuru);
	}

	private static void invoke(int[] tanghuru) {
		int left = 0, maxLen = 0, start = 0, end = 0;
		Map<Integer, Integer> freq = new HashMap<>();

		for (int right = 0; right < tanghuru.length; right++) {
			freq.put(tanghuru[right], freq.getOrDefault(tanghuru[right], 0) + 1);
			while (freq.size() > 2) {
				freq.put(tanghuru[left], freq.get(tanghuru[left]) - 1);
				if (freq.get(tanghuru[left]) == 0) {
					freq.remove(tanghuru[left]);
				}
				left++;
			}

			if (right - left + 1 > maxLen) {
				maxLen = right - left + 1;
				start = left;
				end = right;
			}
		}
		System.out.println(maxLen);

	}
}
