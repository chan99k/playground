package chan99k.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 개똥벌레 한 마리가 장애물(석순과 종유석)로 가득찬 동굴에 들어갔다. 동굴의 길이는 N미터이고, 높이는 H미터이다. (N은 짝수) 첫 번째 장애물은 항상 석순이고, 그 다음에는 종유석과 석순이 번갈아가면서 등장한다.
 * <p>
 * 아래 그림은 길이가 14미터이고 높이가 5미터인 동굴이다. (예제 그림)
 * <p>
 * 이 개똥벌레는 장애물을 피하지 않는다. 자신이 지나갈 구간을 정한 다음 일직선으로 지나가면서 만나는 모든 장애물을 파괴한다.
 * <p>
 * 위의 그림에서 4번째 구간으로 개똥벌레가 날아간다면 파괴해야하는 장애물의 수는 총 여덟개이다. (4번째 구간은 길이가 3인 석순과 길이가 4인 석순의 중간지점을 말한다)
 * <p>
 * 하지만, 첫 번째 구간이나 다섯 번째 구간으로 날아간다면 개똥벌레는 장애물 일곱개만 파괴하면 된다.
 * <p>
 * 동굴의 크기와 높이, 모든 장애물의 크기가 주어진다. 이때, 개똥벌레가 파괴해야하는 장애물의 최솟값과 그러한 구간이 총 몇 개 있는지 구하는 프로그램을 작성하시오.
 */
public class Boj_3020 {
	/**
	 * 첫째 줄에 N과 H가 주어진다. N은 항상 짝수이다. (2 ≤ N ≤ 200,000, 2 ≤ H ≤ 500,000)
	 * <p>
	 * 다음 N개 줄에는 장애물의 크기가 순서대로 주어진다. 장애물의 크기는 H보다 작은 양수이다.
	 */
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		String[] firstLine = br.readLine().split(" ");
		int N = Integer.parseInt(firstLine[0]);
		int H = Integer.parseInt(firstLine[1]);

		int[] suckHeights = new int[N / 2]; // 석순
		int[] jongHeights = new int[N / 2]; // 종유석

		for (int i = 0; i < N; i++) {
			int h = Integer.parseInt(br.readLine());
			if (i % 2 == 0)
				suckHeights[i / 2] = h; // 석순
			else
				jongHeights[i / 2] = h;            // 종유석
		}

		// 높이에 따라 이진탐색이 가능하도록 정렬
		Arrays.sort(suckHeights);
		Arrays.sort(jongHeights);

		invoke(H, suckHeights, jongHeights);
	}

	private static void invoke(int H, int[] suckHeights, int[] jongHeights) {
		int min = Integer.MAX_VALUE;
		int count = 0;

		for (int height = 1; height <= H; height++) {
			int suckCrush = suckHeights.length - lowerBound(suckHeights, height);
			int jongCrush = jongHeights.length - lowerBound(jongHeights, H - height + 1); // 종유석은 뒤집어서 처리
			int totalCrush = suckCrush + jongCrush;

			if (totalCrush < min) {
				min = totalCrush;
				count = 1;
			} else if (totalCrush == min) {
				count++;
			}
		}

		System.out.println(min + " " + count);
	}

	private static int lowerBound(int[] arr, int target) {
		int left = 0, right = arr.length;
		while (left < right) {
			int mid = (left + right) / 2;
			if (arr[mid] >= target)
				right = mid;
			else
				left = mid + 1;
		}
		return left;
	}
}
