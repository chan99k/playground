package chan99k.binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Boj_14003 {
	private int N;
	private int[] Ai;

	/**
	 * 첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다.
	 * <p>
	 * 둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (-1,000,000,000 ≤ Ai ≤ 1,000,000,000)
	 */
	private void getInput() {
		var br = new BufferedReader(
			new BufferedReader(new BufferedReader(new BufferedReader(new InputStreamReader(System.in)))));
		try {
			N = Integer.parseInt(br.readLine());
			Ai = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.
	 * <p>
	 * 둘째 줄에는 정답이 될 수 있는 가장 긴 증가하는 부분 수열을 출력한다.
	 */
	private void solution() {
		// 숫자, 인덱스
		List<Map.Entry<Integer, Integer>> lis = new ArrayList<>();
		int[] prev = new int[N];
		Arrays.fill(prev, -1);

		for (int i = 0; i < N; i++) {
			var curr = new AbstractMap.SimpleImmutableEntry<>(Ai[i], i);
			int idx = Collections.binarySearch(lis, curr, Map.Entry.comparingByKey());
			idx = idx < 0 ? -(idx + 1) : idx;
			if (lis.size() == idx) {
				lis.add(curr);
			} else {
				lis.set(idx, curr);
			}

			if (idx > 0) {
				// lis 내 원소의 바로 전 위치에 있는 원소의 원래 배열에서의 인덱스
				prev[i] = lis.get(idx - 1).getValue(); // 이전 수의 인덱스를 기억하도록
			}
		}
		printAnswer(lis, prev);
	}

	private void printAnswer(List<Map.Entry<Integer, Integer>> lis, int[] prev) {

		// 1. LIS 수열의 길이 출력
		System.out.println(lis.size());

		// LIS의 마지막 원소의 원래 배열에서의 인덱스 가져오기
		int k = lis.get(lis.size() - 1).getValue();
		// LIS 역추적
		List<Integer> answer = new ArrayList<>();
		while (k >= 0) {
			answer.add(Ai[k]); // 원래 원소 값 추가
			k = prev[k]; // 이전 원소의 인덱스로 이동
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		// 역순으로 저장됐으니 다시 앞에서부터 출력
		try {
			for (int i = answer.size() - 1; i >= 0; i--) {
				bw.write(String.valueOf(answer.get(i)));
				if (i > 0) {
					bw.write(" ");
				}
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		var sol = new Boj_14003();
		sol.getInput();
		sol.solution();
	}
}
