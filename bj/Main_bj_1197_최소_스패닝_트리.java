package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

// 크루스칼 알고리즘
// : 간선의 가중치의 합이 최솟값이 되도록 모든 노드를 연결
// 1. 간선 데이터를 오름차순 정렬
// 2. 간선을 확인하면서 현 간선이 사이클을 발생시키는지 확인
// 		발생시키지 않으면 MST 포함
// 3. 모든 간선에 대해 반복한다.
public class Main_bj_1197_최소_스패닝_트리 {
	
	static int V,E,result;
	static int[] parents;
	static ArrayList<Node> nodeList;
	static class Node implements Comparable<Node> {
		int from;
		int to;
		int cost;
		
		public Node(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		parents = new int[V+1];
		nodeList = new ArrayList<>();
		
		for(int i=0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			nodeList.add(new Node(from, to, cost));
		} // input end
		
		// 간선 가중치 기준 오름차순 정렬
		Collections.sort(nodeList);
		
		kruskal();
		
		System.out.println(result);
	}

	private static void kruskal() {
		int cnt = 0;
		
		make();
		
		for(Node n : nodeList) {
			if(union(n.from, n.to)) {
				result += n.cost;
				if(++cnt == V-1) return;
			}
		}
		
		return;
	}
	
	private static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if(aRoot == bRoot) return false;
		
		parents[aRoot] = bRoot;
		return true;
	}

	private static int find(int a) {
		if(a == parents[a]) return a;
		return parents[a] = find(parents[a]);
	}
	
	private static void make() {
		for(int i=1;i<=V;i++) {
			parents[i] = i;
		}
	}

}