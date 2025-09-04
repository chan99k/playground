package chan99k.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Boj_9663 {
	static int n;
	static int count = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());

		solution(0, 0, 0, 0);

		System.out.println(count);
	}

	private static void solution(int row, int cols, int mainDiagonals, int antiDiagonals) {
		if (Integer.bitCount(cols) == n) {
			count++;
			return;
		}

		// 2^n -1 : n개의 1로 칠해진 마스크 생성 & 퀸을 놓을 수 있는 자리 마스크 생성
		int availablePositions = ((1 << n) - 1) & (~(cols | mainDiagonals | antiDiagonals));

		while (availablePositions > 0) { // 퀸을 놓을 수 있는 자리(=비트) 에 1이 남아있다면 반복
			// availablePositions 에 있는 1 중에서 가장 오른쪽 비트만 남기기 -> 퀸을 놓을 수 있는 col 위치중 가장 낮은 위치
			int rightMostBit =
				availablePositions & (-availablePositions);

			solution(row + 1,
				cols | rightMostBit,               // cols: 현재 열에 퀸을 놓았다고 표시하기
				(mainDiagonals | rightMostBit) << 1,
				// mainDiagonals: 주 대각선 마스크에 새로운 퀸의 위치를 추가하고, 주 대각선을 다음 행에 맞게 왼쪽으로 1칸 시프트
				(antiDiagonals | rightMostBit) >> 1
				// antiDiagonals: 반대 대각선 마스크에 새로운 퀸의 위치를 추가하고, 반대 대각선을 다음 행에 맞게 오른쪽으로 1칸 시프트
			);

			availablePositions ^= rightMostBit; // 방금 사용한 비트를 availablePositions에서 제거하여 다음 위치 탐색
		}
	}
}
