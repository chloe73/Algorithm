package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1238_파티 {
	
	static int N,M,X;
	static ArrayList<Node>[] alist, blist;
	static class Node implements Comparable<Node> {
		int end;
		int cost;
		
		public Node(int end, int cost) {
			this.end = end;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		alist = new ArrayList[N+1];
		blist = new ArrayList[N+1];
		
		for(int i=0;i<=N;i++) {
			alist[i] = new ArrayList<>();
			blist[i] = new ArrayList<>();
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			alist[a].add(new Node(b, cost));
			blist[b].add(new Node(a, cost));
		} // input end
		
		int[] go = dijkstra(alist);
		int[] back = dijkstra(blist);
		
		int result = Integer.MIN_VALUE;
		for(int i=1;i<=N;i++) {
			int distance = go[i] + back[i];
			
			if(distance > result)
				result = distance;
		}
		
		System.out.println(result);
	}

	private static int[] dijkstra(ArrayList<Node>[] list) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] check = new boolean[N+1];
		int[] dp = new int[N+1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		
		pq.add(new Node(X, 0));
		dp[X] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			int to = temp.end;
			
			if(check[to]) continue;
			
			check[to] = true;
			
			for(Node next : list[to]) {
				if(dp[to] + next.cost < dp[next.end]) {
					dp[next.end] = dp[to] + next.cost;
					pq.add(new Node(next.end, dp[next.end]));
				}
			}
		}
		
		return dp;
	}
}
