package chan99k.segtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj_11505 {
	static long[] arr;
	static long[] tree;
	static final long MOD = 1_000_000_007;

	static long init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = arr[start] % MOD;
		}

		int mid = start + (end - start) / 2;

		return tree[node] = (init(node * 2, start, mid) * init(node * 2 + 1, mid + 1, end)) % MOD;
	}

	static long query(int node, int start, int end, int left, int right) {
		// base
		if (left > end || right < start) {
			return 1;
		}
		// 현재 노드의 구간이 검색 구간 내부에 완전히 들어가면
		if (left <= start && end <= right) {
			return tree[node];
		}

		int mid = start + (end - start) / 2;

		return (query(node * 2, start, mid, left, right) * query(node * 2 + 1, mid + 1, end, left, right)) % MOD;
	}

	static void update(int node, int start, int end, int index, int newValue) { // mod 를 써도 되긴 하는데 일단
		if (index < start || end < index) {
			return;
		}
		// 리프노드인 경우
		if (start == end) {
			arr[index] = newValue;
			tree[node] = newValue % MOD;
			return;
		}

		int mid = start + (end - start) / 2;

		update(node * 2, start, mid, index, newValue);
		update(node * 2 + 1, mid + 1, end, index, newValue);

		tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % MOD;
	}

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		arr = new long[N + 1];
		tree = new long[N * 4];

		for (int i = 1; i < N + 1; i++) {
			arr[i] = Long.parseLong((br.readLine()));
		}

		init(1, 1, N);

		for (int i = 0; i < M + K; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			if (a == 1) { // b를 c 로 업데이트
				update(1, 1, N, b, c);
			} else if (a == 2) { // b 부터 c 쿼리
				long result = query(1, 1, N, b, c);
				System.out.println(result);
			}
		}
	}
}
