package chan99k.arrays;

import java.util.Arrays;

public class AddingArrayNewRow {
	public static void main(String[] args) {
		int[][] array = {
			{1, 2, 3},
			{4, 5, 6},
			{7, 8, 9}
		};

		// Adding a new row to our array
		int[][] newArray = new int[array.length + 1][3];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = Arrays.copyOf(array[i], array[i].length);
		}

		newArray[3] = new int[] {10, 11, 12};

		for (int[] ints : newArray) {
			for (int anInt : ints) {
				System.out.print(anInt + " ");
			}
			System.out.println();
		}
	}
}
