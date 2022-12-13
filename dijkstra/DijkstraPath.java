package algo.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class DijkstraPath {
	
	static int INF = 9999999;
	static class Node implements Comparable<Node> {
		int index;
		int distance;
		public Node(int index, int distance) {
			this.index = index;
			this.distance = distance;
		}
		@Override
		public int compareTo(Node o) {
			return this.distance - o.distance; // 오름차순 정렬
		}
	}
	static List<List<Node>> graph = new ArrayList<>();
	static int[] result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		// 정점의 개수 : V
		// 간선의 개수 : E
		
		st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		int startIndex = Integer.parseInt(br.readLine());
		
		result = new int[V+1];
		Arrays.fill(result, INF);
		
		for(int i=0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			graph.get(u).add(new Node(v, w));
		} // input end
		
		dijkstra(startIndex);
		
		for(int i=1;i<result.length;i++) {
			if(result[i] == INF) {
				System.out.println("INF");
			} else {
				System.out.println(result[i]);
			}
		}
		br.close();
	}

	private static void dijkstra(int index) {
		
	}
}
