package chan99k.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prgr_77485 {
	public int[] solution(int rows, int columns, int[][] queries) {
		int[][] matrix = new int[rows][columns];
		int value = 1;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				matrix[i][j] = value++;

		List<Integer> min4query = new ArrayList<>();
		for (int[] query : queries) {
			min4query.add(rotateAndGetMin(matrix, query));
		}

		return min4query.stream().mapToInt(Integer::intValue).toArray();
	}

	private int rotateAndGetMin(int[][] matrix, int[] query) {
		int r1 = query[0] - 1;
		int c1 = query[1] - 1;
		int r2 = query[2] - 1;
		int c2 = query[3] - 1;

		List<Integer> borderValues = new ArrayList<>();

		// 테두리 값 추출
		for (int c = c1; c < c2; c++) borderValues.add(matrix[r1][c]);
		for (int r = r1; r < r2; r++) borderValues.add(matrix[r][c2]);
		for (int c = c2; c > c1; c--) borderValues.add(matrix[r2][c]);
		for (int r = r2; r > r1; r--) borderValues.add(matrix[r][c1]);

		// 회전
		borderValues.add(0, borderValues.remove(borderValues.size() - 1));

		// 회전된 값 다시 넣기
		int idx = 0;
		for (int c = c1; c < c2; c++) matrix[r1][c] = borderValues.get(idx++);
		for (int r = r1; r < r2; r++) matrix[r][c2] = borderValues.get(idx++);
		for (int c = c2; c > c1; c--) matrix[r2][c] = borderValues.get(idx++);
		for (int r = r2; r > r1; r--) matrix[r][c1] = borderValues.get(idx++);

		return Collections.min(borderValues);
	}
}

