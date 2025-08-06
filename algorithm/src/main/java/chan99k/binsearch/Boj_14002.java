package chan99k.binsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Boj_14002 {
	private int N;
	private int[] Ai;

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
		List<int[]> lis = new ArrayList<>();
		int[] prev = new int[N];  // 이전 원소 인덱스 추적용
		Arrays.fill(prev, -1);

		for (int i = 0; i < N; i++) {
			int[] curr = {Ai[i], i};
			int idx = Collections.binarySearch(lis, curr, Comparator.comparingInt(o -> o[0]));
			idx = idx < 0 ? -(idx + 1) : idx;
			if (lis.size() == idx) {
				lis.add(curr);
			} else {
				lis.set(idx, curr);
			}

			// 현재 원소가 lis의 몇 번째 위치에 들어갔는지 기준으로
			// 만약 idx가 0이 아니면, 그 이전 원소가 누군지 저장
			// prev[i] = lis 내 바로 전 위치에 있는 원소의 원래 인덱스
			if (idx > 0) {
				prev[i] = lis.get(idx - 1)[1];
			}
		}

		System.out.println(lis.size());

		// LIS의 마지막 원소 인덱스 (원래 배열에서 위치) 가져오기
		int k = lis.get(lis.size() - 1)[1];
		// LIS 역추적
		List<Integer> answer = new ArrayList<>();
		while (k >= 0) { // prev 배열을 따라가며 LIS를 뒤에서부터 재구성
			answer.add(Ai[k]); // 현재 원소 값 추가
			k = prev[k]; 	// 이전 원소 인덱스로 이동
		}
		// 역순으로 저장됐으니 다시 앞에서부터 출력
		for (int i = answer.size() - 1; i >= 0; i--) {
			System.out.print(answer.get(i));
			if (i > 0){
				System.out.print(" ");
			}
		}
	}

	public static void main(String[] args) {
		var sol = new Boj_14002();
		sol.getInput();
		sol.solution();
	}
}

