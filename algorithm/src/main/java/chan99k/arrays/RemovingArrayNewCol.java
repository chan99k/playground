package chan99k.arrays;

public class RemovingArrayNewCol {

	public static void main(String[] args) {
		int[][] array = {
			{1, 2, 3},
			{4, 5, 6},
			{7, 8, 9}
		};

		// Removing the second column
		int[][] newArray = new int[array.length][array[0].length - 1];
		for (int i = 0; i < array.length; i++) {
			int index = 0;
			for (int j = 0; j < array[i].length; j++) {
				if (j != 1) {
					newArray[i][index] = array[i][j];
					index++;
				}
			}
		}

		for (int[] ints : newArray) {
			for (int anInt : ints) {
				System.out.print(anInt + " ");
			}
			System.out.println();
		}
	}
}

