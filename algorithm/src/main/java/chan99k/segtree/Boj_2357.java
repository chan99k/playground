package chan99k.segtree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Boj_2357 {

	static int[] arr;
	static long[][] tree;

	static long[] init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = new long[] {arr[start], arr[start]};
		}

		int mid = start + (end - start) / 2;
		long[] left = init(node * 2, start, mid);
		long[] right = init(node * 2 +1, mid + 1, end);

		long min = Math.min(left[0], right[0]);
		long max = Math.max(left[1], right[1]);

		return tree[node] = new long[] {min, max};
	}

	static long[] query(int node, int start, int end, int left, int right) {
		if (right < start || end < left) {
			return new long[] {Long.MAX_VALUE, Long.MIN_VALUE};
		}

		if (left <= start && end <= right) {
			return tree[node];
		}

		// 검색 범위가 현재 노드의 구간과 일부 겹치면
		int mid = start + (end - start) / 2;

		long[] node_left = query(node * 2, start, mid, left, right);
		long[] node_right = query(node * 2 + 1, mid + 1, end, left, right);

		long min = Math.min(node_left[0], node_right[0]);
		long max = Math.max(node_left[1], node_right[1]);

		return new long[] {min, max};
	}

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		arr = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		tree = new long[N * 4][];

		init(1, 1, N);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (int j = 1; j <= M; j++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			long[] result = query(1, 1, N, a, b);

			bw.write(String.valueOf(result[0]));
			bw.write(" ");
			bw.write(String.valueOf(result[1]));
			bw.append('\n');
		}
		bw.flush();
	}
}
