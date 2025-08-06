package chan99k.binsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 수열 S가 어떤 수 Sk를 기준으로 S1 < S2 < ... Sk-1 < Sk > Sk+1 > ... SN-1 > SN을 만족한다면, 그 수열을 바이토닉 수열이라고 한다.
 * <p>
 * 예를 들어, {10, 20, 30, 25, 20}과 {10, 20, 30, 40}, {50, 40, 25, 10} 은 바이토닉 수열이지만, {1, 2, 3, 2, 1, 2, 3, 2, 1}과 {10, 20, 30, 40, 20, 30} 은 바이토닉 수열이 아니다.
 * <p>
 * 수열 A가 주어졌을 때, 그 수열의 부분 수열 중 바이토닉 수열이면서 가장 긴 수열의 길이를 구하는 프로그램을 작성하시오.
 */
public class Boj_11054 {
	private int N;
	private int[] A;

	/**
	 * 첫째 줄에 수열 A의 크기 N이 주어지고, 둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ N ≤ 1,000, 1 ≤ Ai ≤ 1,000)
	 */
	private void getInput() {
		var br = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(br.readLine());
			A = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printAnswer() {
		int[] up = new int[N];
		int[] down = new int[N];

		Arrays.fill(up, 1);
		Arrays.fill(down, 1);

        // up[i] = i번째 요소를 마지막으로 하는 가장 긴 증가 부분 수열(LIS)의 길이
        // 앞에서부터 탐색하면서, 자신보다 작은 A[j]를 찾고 그 dp 값을 이용해 갱신
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < i; j++) {
                // A[j] < A[i]인 경우만 증가 수열이 가능하므로
                // up[j]에 1을 더한 값으로 up[i]를 갱신
				if (A[j] < A[i]) {
					up[i] = Math.max(up[i], up[j] + 1);
				}
			}
		}

        // down[i] = i번째 요소를 시작으로 하는 가장 긴 감소 부분 수열(LDS)의 길이
        // 뒤에서부터 탐색하면서, 자신보다 작은 A[j]를 찾고 그 dp 값을 이용해 갱신
		for (int i = N - 1; i >= 0; i--) {
			for (int j = N - 1; j > i; j--) {
                // A[j] < A[i]인 경우만 감소 수열이 가능하므로
                // down[j]에 1을 더한 값으로 down[i]를 갱신
				if (A[j] < A[i]) {
					down[i] = Math.max(down[i], down[j] + 1);
				}
			}
		}

		int answer = 0;
		for (int i = 0; i < N; i++) {
			answer = Math.max(answer, up[i] + down[i] - 1);
		}

		System.out.println(answer);
	}

	public static void main(String[] args) {
		var sol = new Boj_11054();
		sol.getInput();
		sol.printAnswer();
	}
}
