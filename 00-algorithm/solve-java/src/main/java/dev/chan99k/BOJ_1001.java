package dev.chan99k;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1001 {

	public static void main(String[] args) throws IOException {
		FastReader fr = new FastReader();
		int a = fr.nextInt();
		int b = fr.nextInt();
		System.out.println(a - b);
	}

	static class FastReader {
		private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		private String[] tokens;
		private int index;

		String next() throws IOException {
			while (tokens == null || index >= tokens.length) {
				tokens = br.readLine().split(" ");
				index = 0;
			}
			return tokens[index++];
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

}
