package chan99k.flyweight;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

/**
 * Client: 외재적 상태를 관리하며 플라이웨이트 객체를 사용한다.
 */
public class Forest {
	private List<Tree> trees = new ArrayList<>();

	public void plantTree(Position position, double height, double thickness) {
		// 팩토리로부터 공유 객체(TreeModel)를 받는다.
		TreeType type = TreeFactory.getTreeType("StandardTree");
		// 외재적 상태와 함께 Tree 객체를 생성한다.
		trees.add(new Tree(position, height, thickness, type));
	}
}