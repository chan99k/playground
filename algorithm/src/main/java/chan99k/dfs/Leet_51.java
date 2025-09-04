package chan99k.dfs;

import java.util.ArrayList;
import java.util.List;

public class Leet_51 {
	List<List<String>> result = new ArrayList<>();

	boolean[] colMarker;
	boolean[] dia1Marker;
	boolean[] dia2Marker;
	int[] board;
	int n;

	public List<List<String>> solveNQueens(int n) {
		this.n = n;
		colMarker = new boolean[n];
		dia1Marker = new boolean[2 * n - 1]; // 오른쪽 위-원쪽아래 대각선 : row + col 범위 : (0 ~ 2n-2)
		dia2Marker = new boolean[2 * n - 1]; // 왼쪽 위 - 오른쪽 아래 대각선 : row - col 범위 : -(n-1) ~ (n - 1)

		board = new int[n]; // 퀸 위치 저장

		dfs(0);
		return result;
	}

	private void dfs(int row) {
		if (row == n) {
			addSolution();
			return;
		}

		for (int col = 0; col < n; col++) {
			if (!colMarker[col] && !dia1Marker[row + col] && !dia2Marker[row - col + n - 1]) { // 킬러 퀸을 놓을 수 있는 자리라면
				colMarker[col] = true;
				dia1Marker[row + col] = true;
				dia2Marker[row - col + n - 1] = true;
				board[row] = col;

				dfs(row + 1);

				dia2Marker[row - col + n - 1] = false;
				dia1Marker[row + col] = false;
				colMarker[col] = false;
			}
		}
	}

	private void addSolution() {
		List<String> solution = new ArrayList<>();
		for (int row = 0; row < n; row++) {
			var rowString = new StringBuilder();
			for (int col = 0; col < n; col++) {
				if (board[row] == col) { // board[row] (퀸을 놓은 col) 이 현재 순회중인 col 과 같다면
					rowString.append("Q");
				} else {
					rowString.append(".");
				}
			}
			solution.add(rowString.toString());
		}
		result.add(solution);
	}

}
