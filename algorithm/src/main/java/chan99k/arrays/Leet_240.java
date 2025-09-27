package chan99k.arrays;

public class Leet_240 {
	// 문제는 이진탐색 카테고리지만 이진 탐색으로 풀면 그 이점을 누리지 못함
	public boolean searchMatrix(int[][] matrix, int target) {
		int row = 0;
		int col = matrix[0].length - 1;

		while (row < matrix.length && col >= 0) {
			int found = matrix[row][col];
			if (found == target) {
				return true;
			} else if (found > target) {
				col--;
			} else {
				row++;
			}
		}

		return false;
	}
}
