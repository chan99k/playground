package chan99k.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Boj_12738 {
	private int N;
	private int[] Ai;

	/**
	 * 첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다.
	 * <p>
	 * 둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (-1,000,000,000 ≤ Ai ≤ 1,000,000,000)
	 */
	private void getInput() {
		var br = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(br.readLine());
			Ai = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void solution() {
		List<Integer> lis = new ArrayList<>();
		for (int num : Ai) {
			int idx = Collections.binarySearch(lis, num);
			idx = idx < 0 ? -(idx + 1) : idx;
			if (lis.size() == idx) {
				lis.add(num);
			} else {
				lis.set(idx, num);
			}
		}
		System.out.println(lis.size());
	}

	public static void main(String[] args) {
		var sol = new Boj_12738();
		sol.getInput();
		sol.solution();
	}
}
