package chan99k.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * FlyweightFactory: 플라이웨이트 객체를 생성하고 공유를 관리한다.
 */
public class TreeFactory {
	// 공유되는 TreeModel 객체를 저장하는 풀(Pool)
	private static final Map<String, TreeType> treeTypes = new HashMap<>();

	public static TreeType getTreeType(String name) {
		// 맵에 없으면 새로 생성하고, 있으면 기존 객체를 반환한다.
		return treeTypes.computeIfAbsent(name, n -> new TreeModel());
	}
}
