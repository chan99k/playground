package chan99k.dfs;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leet_1391 {
	static final Map<Integer, List<int[]>> directions = new HashMap<>();

	static {
		directions.put(1, List.of(new int[] {0, 1}, new int[] {0, -1})); // 좌우
		directions.put(2, List.of(new int[] {1, 0}, new int[] {-1, 0})); // 상하
		directions.put(3, List.of(new int[] {0, -1}, new int[] {1, 0})); // 좌하
		directions.put(4, List.of(new int[] {0, 1}, new int[] {1, 0})); // 우하
		directions.put(5, List.of(new int[] {0, -1}, new int[] {-1, 0})); // 좌상
		directions.put(6, List.of(new int[] {0, 1}, new int[] {-1, 0})); // 우상
	}

	public boolean hasValidPath(int[][] grid) {
		int n = grid.length;
		int m = grid[0].length;
		int[] start = new int[] {0, 0};
		boolean[][] visited = new boolean[n][m];
		var stk = new ArrayDeque<int[]>();

		stk.push(start);
		visited[0][0] = true;

		while (!stk.isEmpty()) {
			int[] curr = stk.pop();
			int r = curr[0], c = curr[1];

			if (r == n - 1 && c == m - 1) {
				return true;
			}

			var dirs = directions.get(grid[r][c]);

			for (int[] dir : dirs) {
				int nr = r + dir[0];
				int nc = c + dir[1];

				if (nr < 0 || nr >= n || nc < 0 || nc >= m || visited[nr][nc])
					continue;

				int nextType = grid[nr][nc];

				var nextDirs = directions.get(nextType);
				boolean canConnect = false;
				for (int[] nextDir : nextDirs) {
					if (nr + nextDir[0] == r && nc + nextDir[1] == c) {
						canConnect = true;
						break;
					}
				}

				if (canConnect) {
					visited[nr][nc] = true;
					stk.push(new int[] {nr, nc});
				}
			}
		}

		return false;
	}
}
