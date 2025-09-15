package chan99k.dp;

public class Leet_64 {
	public int minPathSum(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;

		int[] dp = new int[m];

		dp[0] = grid[0][0];

		for (int j = 1; j < m; j++) {
			dp[j] = dp[j - 1] + grid[0][j];
		}

		// 현재 참조하는 "위쪽" dp 값은 이전 행에서 처리해둔 값
		// 현재 참조하는 "왼쪽" dp 값은 이미 이번 루프에서 갱신한 값
		for (int i = 1; i < n; i++) {
			dp[0] += grid[i][0];

			int[] row = grid[i];
			for (int j = 1; j < m; j++) {
				dp[j] = Math.min(dp[j], dp[j - 1]) + row[j];
			}
		}

		return dp[m - 1];
	}

	public int minPathSum01(int[][] grid) {
		int[][] map = new int[grid.length][grid[0].length];
		int n = map.length;
		int m = map[0].length;
		map[0][0] = grid[0][0];

		for (int i = 1; i < n; i++) {
			map[i][0] = grid[i][0] + map[i - 1][0];
		}

		for (int i = 1; i < m; i++) {
			map[0][i] = grid[0][i] + map[0][i - 1];
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				map[i][j] = Math.min(map[i - 1][j] + grid[i][j], map[i][j - 1] + grid[i][j]);
			}
		}

		return map[n - 1][m - 1];
	}

}
