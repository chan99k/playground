import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 많은 양의 데이터에 액세스하는 것이 너무 느리다고 판단되는 경우 일반적인 속도 향상 기술은 캐시라고 하는 액세스하기 쉬운 위치에 소량의 데이터를 보관하는 것입니다.
 * 특정 데이터에 처음 액세스할 때는 slow 메서드를 사용해야 합니다. 그러나 데이터는 캐시에 저장되므로 다음에 필요할 때 훨씬 더 빠르게 액세스할 수 있습니다.
 * 예를 들어, 데이터베이스 시스템은 하드 드라이브를 읽을 필요가 없도록 데이터를 메모리에 캐시된 상태로 유지할 수 있습니다.
 * 또는 웹 브라우저는 네트워크를 통해 웹 페이지를 다운로드할 필요가 없도록 로컬 컴퓨터에 웹 페이지 캐시를 유지할 수 있습니다.
 * <p>
 * 일반적으로 캐시는 필요한 모든 데이터를 보관하기에는 너무 작기 때문에 어느 시점에서 새 데이터를 위한 공간을 확보하기 위해 캐시에서 무언가를 제거해야 합니다.
 * 목표는 곧 다시 검색될 가능성이 더 높은 항목을 유지하는 것입니다. 이를 위해서는 캐시에서 제거할 항목을 선택하기 위한 합리적인 알고리즘이 필요합니다.
 * 간단하지만 효과적인 알고리즘 중 하나는 LRU(Least Recently Used) 알고리즘입니다. LRU 캐싱을 수행할 때 항상 가장 최근에 사용된 데이터를 버립니다.
 * <p>
 * 예를 들어 최대 5개의 데이터를 저장할 수 있는 캐시를 상상해 보겠습니다. A, B, C의 세 가지 데이터에 액세스한다고 가정해 보겠습니다.
 * 각 항목에 액세스할 때 캐시에 저장하므로 이 시점에서 캐시에는 세 개의 데이터가 있고 두 개의 빈 공간이 있습니다(그림 1). 이제 D와 E에 액세스한다고 가정해 보겠습니다. 캐시에도 추가되어 캐시를 채웁니다.
 * 다음으로 A에 다시 액세스한다고 가정합니다. A는 이미 캐시에 있으므로 캐시는 변경되지 않습니다. 그러나 이 액세스는 사용으로 간주되므로 A가 가장 최근에 사용되었습니다.
 * 이제 F에 접근하려면 F를 위한 공간을 만들기 위해 무언가를 버려야 합니다. 이 시점에서 B는 가장 최근에 사용되지 않았으므로 버리고 F로 대체합니다(그림 2).
 * 이제 B에 다시 액세스한다면, 그것은 우리가 처음 액세스했을 때와 똑같을 것입니다: 우리는 그것을 검색하여 캐시에 저장하고, 가장 최근에 가장 적게 사용된 데이터(이번에는 C)를 버려 공간을 확보할 것입니다.
 * <p>
 * 이 문제점에 대한 태스크는 데이터 액세스 시퀀스를 수행하고 LRU 캐시를 시뮬레이션하는 것입니다. 요청되면 가장 최근에 사용한 것부터 가장 최근에 사용한 것까지 순서대로 캐시의 내용을 출력합니다.
 */
public class Boj_4568 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int simulationNumber = 1;
		String line;

		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.equals("0")) {
				break;
			}

			String[] parts = line.split(" ");
			int cacheSize = Integer.parseInt(parts[0]);
			String operations = parts[1];

			bw.write("Simulation " + simulationNumber);
			bw.newLine();

			NodeLRU cache = new NodeLRU(cacheSize);

			for (char c : operations.toCharArray()) {
				if (c == '!') {
					// 캐시 내용 출력 (LRU → MRU 순서)
					bw.write(cache.getCacheContents());
					bw.newLine();
				} else {
					// 데이터 접근
					cache.access(c);
				}
			}

			simulationNumber++;
		}

		bw.flush();
		bw.close();
		br.close();
	}

	static class CacheNode {
		char data;
		long timestamp;

		public CacheNode(char data) {
			this.data = data;
			this.timestamp = 0;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			CacheNode node = (CacheNode)obj;
			return data == node.data;
		}

		@Override
		public int hashCode() {
			return Character.hashCode(data);
		}
	}

	static class NodeLRU {
		private final int capacity;
		private long currentTime = 0;
		private final Map<Long, Set<CacheNode>> timeToNodes = new HashMap<>();
		private final Map<CacheNode, Long> nodeToTime = new HashMap<>();
		private final Map<Character, CacheNode> dataToNode = new HashMap<>();

		public NodeLRU(int capacity) {
			this.capacity = capacity;
		}

		public void access(char data) {
			currentTime++;

			CacheNode node = dataToNode.get(data);

			if (node != null) {
				long oldTime = nodeToTime.get(node);

				timeToNodes.get(oldTime).remove(node);
				if (timeToNodes.get(oldTime).isEmpty()) {
					timeToNodes.remove(oldTime);
				}

				long newTime = currentTime;
				node.timestamp = newTime;
				nodeToTime.put(node, newTime);

				timeToNodes.putIfAbsent(newTime, new HashSet<>());
				timeToNodes.get(newTime).add(node);
			} else {
				while (nodeToTime.size() >= capacity) {
					removeLRU();
				}

				node = new CacheNode(data);
				node.timestamp = currentTime;

				dataToNode.put(data, node);
				nodeToTime.put(node, currentTime);

				timeToNodes.putIfAbsent(currentTime, new HashSet<>());
				timeToNodes.get(currentTime).add(node);
			}
		}

		private void removeLRU() {
			long minTime = Collections.min(timeToNodes.keySet());
			Set<CacheNode> oldestNodes = timeToNodes.get(minTime);

			// 가장 오래된 노드 중 하나 제거
			CacheNode nodeToRemove = oldestNodes.iterator().next();

			oldestNodes.remove(nodeToRemove);
			if (oldestNodes.isEmpty()) {
				timeToNodes.remove(minTime);
			}

			nodeToTime.remove(nodeToRemove);
			dataToNode.remove(nodeToRemove.data);
		}

		public String getCacheContents() {
			if (nodeToTime.isEmpty())
				return "";

			List<CacheNode> sortedNodes = new ArrayList<>(nodeToTime.keySet());

			sortedNodes.sort((a, b) -> Long.compare(a.timestamp, b.timestamp));

			StringBuilder sb = new StringBuilder();
			for (CacheNode node : sortedNodes) {
				sb.append(node.data);
			}
			return sb.toString();
		}

		public void printState() {
			System.out.println("Current time: " + currentTime);
			System.out.println("Time to nodes: " + timeToNodes);
			System.out.println("Node to time: " + nodeToTime);
			System.out.println("Data to node: " + dataToNode.keySet());
		}
	}
}
