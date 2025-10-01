package chan99k.dfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Leet_652 {
	public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
		List<TreeNode> result = new ArrayList<>();
		Map<String, TreeNode> subtrees = new HashMap<>();

		dfs(root, subtrees, result, new HashSet<>());

		return result.stream().toList();
	}

	private String dfs(TreeNode curr, Map<String, TreeNode> hm, List<TreeNode> result, Set<String> added) {
		if (curr == null)
			return "#";

		var serialized = curr.val + "," + dfs(curr.left, hm, result, added) + "," + dfs(curr.right, hm, result, added);

		if (hm.containsKey(serialized) && !added.contains(serialized)) {
			result.add(curr);
			added.add(serialized);
		}

		hm.putIfAbsent(serialized, curr);

		return serialized;

	}

	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

}
