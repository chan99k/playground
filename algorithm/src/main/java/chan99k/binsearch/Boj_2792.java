package chan99k.binsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 보석 공장에서 보석 상자를 유치원에 기증했다. 각각의 보석은 M가지 서로 다른 색상 중 한 색상이다. 원장 선생님은 모든 보석을 N명의 학생들에게 나누어 주려고 한다. 이때, 보석을 받지 못하는 학생이 있어도 된다. 하지만, 학생은 항상 같은 색상의 보석만 가져간다.
 * <p>
 * 한 아이가 너무 많은 보석을 가져가게 되면, 다른 아이들이 질투를 한다. 원장 선생님은 이런 질투심을 수치화하는데 성공했는데, 질투심은 가장 많은 보석을 가져간 학생이 가지고 있는 보석의 개수이다. 원장 선생님은 질투심이 최소가 되게 보석을 나누어 주려고 한다.
 * <p>
 * 상자에 빨간 보석이 4개 (RRRR), 파란 보석이 7개 (BBBBBBB) 있었고, 이 보석을 5명의 아이들에게 나누어 주는 경우를 생각해보자. RR, RR, BB, BB, BBB로 보석을 나누어주면 질투심은 3이 되고, 이 값보다 작게 나누어 줄 수 없다.
 * <p>
 * 상자 안의 보석 정보와 학생의 수가 주어졌을 때, 질투심이 최소가 되게 보석을 나누어주는 방법을 알아내는 프로그램을 작성하시오.
 */
public class Boj_2792 {
	/**
	 * 첫째 줄에 아이들의 수 N과 색상의 수 M이 주어진다. (1 ≤ N ≤ 109, 1 ≤ M ≤ 300,000, M ≤ N)
	 * <p>
	 * 다음 M개 줄에는 구간 [1, 109]에 포함되는 양의 정수가 하나씩 주어진다. K번째 줄에 주어지는 숫자는 K번 색상 보석의 개수이다.
	 */
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int[] NM = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		int N = NM[0];
		int M = NM[1];
		int[] colors = new int[M];
		for (int i = 0; i < M; i++) {
			colors[i] = Integer.parseInt(br.readLine());
		}

		invoke(Arrays.copyOf(colors, colors.length), N, M);
	}

	/**
	 * 첫째 줄에 질투심의 최솟값을 출력한다.
	 * mid라는 질투심(한 명이 받을 수 있는 최대 보석 수) 값이 주어졌을 때,
	 * 색상별로 몇 명의 학생이 필요할지 합산해서
	 * 그 합이 N명 이하인지 판단
	 */
	private static void invoke(int[] colors, int N, int M) {
		int left = 1;
		int right = Arrays.stream(colors).max().getAsInt();
		int mid = (left + right) / 2;
		int answer = Integer.MAX_VALUE;

		while (left <= right) {
			int count = 0;
			for (int color : colors) {
				count += (color + mid - 1) / mid;
			}

			if (count > N) {
				left = mid + 1;
			} else {
				answer = mid;
				right = mid - 1;
			}
			mid = (left + right) / 2;
		}

		System.out.println(answer);
	}
}
