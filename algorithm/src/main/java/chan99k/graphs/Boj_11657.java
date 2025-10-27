package chan99k.graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Boj_11657 {

	static class Edge {
		int from, to, cost;

		public Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}

	static final long INF = Long.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 간선 정보 저장
		List<Edge> edges = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			edges.add(new Edge(A, B, C));
		}

		long[] dist = new long[N + 1];
		Arrays.fill(dist, INF);
		dist[1] = 0;

		for (int i = 0; i < N - 1; i++) {
			for (Edge e : edges) {
				if (dist[e.from] != INF &&
					dist[e.from] + e.cost < dist[e.to]) {
					dist[e.to] = dist[e.from] + e.cost;
				}
			}
		}

		boolean negativeCycle = false;
		// 딱 한번만 더 돌아서 음수 사이클 확인
		for (Edge e : edges) {
			if (dist[e.from] != INF &&
				dist[e.from] + e.cost < dist[e.to]) {
				negativeCycle = true;
				break;
			}
		}

		var sb = new StringBuilder();
		if (negativeCycle) {
			sb.append(-1);
		} else {
			for (int i = 2; i <= N; i++) {
				if (dist[i] == INF) {
					sb.append(-1).append('\n');
				} else {
					sb.append(dist[i]).append('\n');
				}
			}
		}
		System.out.println(sb);
	}
}
