package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 최단 경로
// 다익스트라
public class Main_bj_5972_택배_배송 {
	
	static int N,M;
	static ArrayList<Node>[] graph;
	static int[] dist;
	static boolean[] visited;
	static class Node implements Comparable<Node>{
		int v,cost;
		
		public Node(int v, int cost) {
			this.v = v;
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
		
		graph = new ArrayList[N+1];
		visited = new boolean[N+1];
		dist = new int[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
			dist[i] = Integer.MAX_VALUE;
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[a].add(new Node(b, cost));
			graph[b].add(new Node(a, cost));
		} // input end
		
		dijkstra(1);
		
		System.out.println(dist[N]);

	}

	private static void dijkstra(int start) {
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(visited[temp.v]) continue;
			visited[temp.v] = true;
			
			for(Node next : graph[temp.v]) {
				
				if(dist[next.v] > temp.cost + next.cost) {
					dist[next.v] = temp.cost + next.cost;
					pq.add(new Node(next.v, dist[next.v]));
				}
				
//				if(dist[next.v] > dist[temp.v] + next.cost) {
//					dist[next.v] = dist[temp.v] + next.cost;
//					pq.add(new Node(next.v, dist[next.v]));
//				}
			}
			
		}
		
	}

}
