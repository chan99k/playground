package chan99k.segtree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Boj_1275 {
	static long[] arr;
	static long[] tree;

	private static long init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = arr[start];
		}

		int mid = start + (end - start) / 2;

		return tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
	}

	private static long query(int node, int start, int end, int left, int right) {
		if (right < start || end < left) {
			return 0;
		}

		if (left <= start && end <= right) {
			return tree[node];
		}

		int mid = start + (end - start) / 2;
		long left_node = query(node * 2, start, mid, left, right);
		long right_node = query(node * 2 + 1, mid + 1, end, left, right);

		return left_node + right_node;
	}

	private static void update(int node, int start, int end, int index, long newValue) {
		if (index < start || end < index) {
			return;
		}

		if (start == end) {
			arr[index] = newValue;
			tree[node] = newValue;
			return;
		}

		int mid = start + (end - start) / 2;
		update(node * 2, start, mid, index, newValue);
		update(node * 2 + 1, mid + 1, end, index, newValue);
		tree[node] = tree[node * 2] + tree[node * 2 + 1];
	}

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()), Q = Integer.parseInt(st.nextToken());

		tree = new long[N * 4];
		arr = Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray();

		init(1, 0, N - 1);

		var bw = new BufferedWriter(new OutputStreamWriter(System.out));

		while (Q-- > 0) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()), y = Integer.parseInt(st.nextToken()),
				a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());

			if (x > y) {
				int temp = x;
				x = y;
				y = temp;
			}

			long query = query(1, 0, N - 1, x - 1, y - 1);
			bw.write(query + "\n");
			update(1, 0, N - 1, a - 1, b);
		}
		bw.flush();
		bw.close();
		br.close();

	}
}
