package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1647_도시_분할_계획 {
	
	static int N,M;
	static int max; // 가장 많은 비용이 드는 값
	static long result;
	static int[] parents;
	static PriorityQueue<Node> pq;
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
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		parents = new int[N+1];
		for(int i=1;i<=N;i++) {
			parents[i] = i;
		}
		
		pq = new PriorityQueue<>();
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			pq.add(new Node(from, to, cost));
		} // input end
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(!is_parent(temp.from, temp.to)) {
				union(temp.from, temp.to);
				result += temp.cost;
				// 결국 마지막에 들어오는 값이 최댓값임.
				max = temp.cost;
			}
		}
		
		System.out.println(result - max);
	}
	
	private static boolean is_parent(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		if(aRoot == bRoot) return true;
		else return false;
	}
	
	private static void union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		
		parents[bRoot] = aRoot;
	}
	
	private static int find(int a) {
		if(a == parents[a]) return a;
		return parents[a] = find(parents[a]);
	}

}