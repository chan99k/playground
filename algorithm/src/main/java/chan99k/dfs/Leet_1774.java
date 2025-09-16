package chan99k.dfs;

public class Leet_1774 {
	private int closestCost = Integer.MAX_VALUE;
	private int minDiff = Integer.MAX_VALUE;

	public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {

		for (int baseCost : baseCosts) {
			dfs(toppingCosts, 0, baseCost, target);
		}
		return closestCost;
	}

	private void dfs(int[] toppingCosts, int index, int currCost, int target) {
		int diff = Math.abs(currCost - target);
		if (diff < minDiff || (diff == minDiff && currCost < closestCost)) {
			minDiff = diff;
			closestCost = currCost;
		}
		if (currCost == target) {
			return;
		}
		if (index >= toppingCosts.length || currCost > target + minDiff) {
			return;
		}

		for (int count = 0; count < 3; count++) {
			dfs(toppingCosts, index + 1, currCost + toppingCosts[index] * count, target);
		}
	}

	public static void main(String[] args) {
		Leet_1774 sol = new Leet_1774();
		int i = sol.closestCost(new int[] {1, 7}, new int[] {3, 4}, 10);
		System.out.println(i);
	}
}
