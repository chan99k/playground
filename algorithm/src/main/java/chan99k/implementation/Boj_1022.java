package chan99k.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj_1022 {
	/**
	 * 첫째 줄에 네 정수 r1, c1, r2, c2가 주어진다.
	 */
	public static void main(String[] args) throws IOException {
		// 표준 입력에서 한 줄 읽음 (예: -2 -2 1 1)
		var br = new BufferedReader(new InputStreamReader(System.in));

		// 입력값을 공백으로 나눠 int 배열로 변환
		int[] rc = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();

		// 좌표 정보 추출
		int r1 = rc[0], c1 = rc[1], r2 = rc[2], c2 = rc[3];

		// 출력할 행(row) 수와 열(col) 수 계산
		int rows = r2 - r1 + 1;
		int cols = c2 - c1 + 1;

		// 소용돌이 숫자를 저장할 1차원 배열 (rows * cols 크기만큼 필요)
		int[] flat = new int[rows * cols];

		// 내부 클래스 선언: 소용돌이 숫자를 생성해 flat 배열에 채움
		class Solution {
			// 방향 배열: 오른쪽, 위, 왼쪽, 아래 (반시계 방향)
			int[] dr = {0, -1, 0, 1};
			int[] dc = {1, 0, -1, 0};

			int r = 0, c = 0;      // 현재 좌표
			int num = 1;           // 현재 숫자 (1부터 시작)
			int dir = 0;           // 현재 방향 인덱스
			int len = 1;           // 현재 방향으로 이동할 칸 수
			int maxNum = 1;        // 출력 정렬을 위한 최대 숫자 저장

			void invoke(int r1, int r2, int c1, int c2) {
				int countFilled = 0;          // 지금까지 채운 칸 수
				int total = rows * cols;      // 채워야 할 총 칸 수

				// 시작 위치가 범위 안이면 첫 값 넣기
				if (r >= r1 && r <= r2 && c >= c1 && c <= c2) {
					int idx = (r - r1) * cols + (c - c1);
					flat[idx] = num;
					maxNum = num;
					countFilled++;
				}

				// 모든 칸을 다 채울 때까지 반복
				while (countFilled < total) {
					for (int d = 0; d < 4; d++) {        // 4방향 순회
						for (int i = 0; i < len; i++) {   // 현재 방향으로 len칸 이동
							r += dr[dir];  // 행 이동
							c += dc[dir];  // 열 이동
							num++;         // 숫자 증가

							// 현재 위치가 출력 범위 안에 있으면 flat[]에 저장
							if (r >= r1 && r <= r2 && c >= c1 && c <= c2) {
								int idx = (r - r1) * cols + (c - c1);
								flat[idx] = num;
								maxNum = Math.max(maxNum, num);  // 최대값 갱신
								countFilled++;
							}

							// 필요한 모든 칸을 다 채웠으면 종료
							if (countFilled == total) {
								return;
							}
						}

						// 방향 전환 (0→1→2→3→0 ...)
						dir = (dir + 1) % 4;

						// 매 2방향마다 이동 거리 1 증가
						if (d % 2 == 1) {
							len++;
						}
					}
				}
			}
		}

		// 소용돌이 생성기 실행
		var sol = new Solution();
		sol.invoke(r1, r2, c1, c2);

		// 출력 정렬을 위한 너비 계산 (최대 숫자의 자릿수)
		int width = String.valueOf(sol.maxNum).length();

		// flat 배열을 2차원 형태로 예쁘게 출력
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// flat 배열에서 해당 위치의 숫자 가져오기
				int val = flat[i * cols + j];

				// 숫자를 width 자리수에 맞춰 오른쪽 정렬하여 출력
				System.out.print(String.format("%" + width + "d", val));

				// 마지막 열이 아니면 공백 추가
				if (j < cols - 1)
					System.out.print(" ");
			}
			// 줄바꿈
			System.out.println();
		}
	}

}
