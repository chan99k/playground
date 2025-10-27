package chan99k.djikstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Boj_1261 {
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int[] mn = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt).toArray();
		int M = mn[0], N = mn[1];

		char[][] graph = new char[N][M];
		for (int i = 0; i < N; i++) {
			char[] row = br.readLine().toCharArray();
			graph[i] = row;
		}

		int[][] dist = new int[N][M];
		for (int[] arr : dist) {
			Arrays.fill(arr, Integer.MAX_VALUE);
		}
		boolean[][] visited = new boolean[N][M];
		dist[0][0] = 0;

		PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt(o -> dist[o.x][o.y]));
		pq.offer(new Point(0, 0));

		while (!pq.isEmpty()) {
			var curr = pq.poll();

			if (visited[curr.x][curr.y]) {
				continue;
			}

			visited[curr.x][curr.y] = true;

			for (int k = 0; k < 4; k++) {
				int nx = curr.x + dx[k];
				int ny = curr.y + dy[k];
				if (0 <= nx && nx < N && 0 <= ny && ny < M && !visited[nx][ny]) {
					int newCost = dist[curr.x][curr.y] + (graph[nx][ny] - '0');
					if (newCost < dist[nx][ny]) {
						dist[nx][ny] = newCost;
						pq.offer(new Point(nx, ny));
					}
				}
			}
		}

		System.out.println(dist[N - 1][M - 1]);
	}

	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
