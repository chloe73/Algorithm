package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

// 최소 스패닝 트리 - 크루스칼 알고리즘
public class Main_bj_1922_네트워크_연결 {
	
	static int N,M;
	static int[] parents;
	static ArrayList<Node> nodeList;
	static class Node implements Comparable<Node>{
		int start;
		int end;
		int cost;
		
		public Node(int start, int end, int cost) {
			this.start = start;
			this.end = end;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		parents = new int[N+1];
		for(int i=1;i<=N;i++) {
			parents[i] = i;
		}
		
		nodeList = new ArrayList<>();
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			nodeList.add(new Node(start, end, cost));
		} // input end
		
		Collections.sort(nodeList);
		
		int result = 0;
		for(int i=0;i<nodeList.size();i++) {
			Node temp = nodeList.get(i);
			
			// 사이클이 발생하는 간선은 제외
			if(find(temp.start) != find(temp.end)) {
				result += temp.cost;
				union(temp.start, temp.end);
			}
		}

		System.out.println(result);
	}
	
	private static int find(int a) {
		if(a == parents[a]) return a;
		
		return parents[a] = find(parents[a]);
	}
	
	private static void union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if(aRoot != bRoot) {
			parents[bRoot] = aRoot;
		}
	}

}
