package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1504_특정한_최단_경로 {
	
	static int N,E;
	static int v1,v2;
	static ArrayList<Node>[] graph;
	static int[] dist;
	static boolean[] visited;
	static class Node implements Comparable<Node>{
		int v;
		int cost;
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
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 정점의 개수
		E = Integer.parseInt(st.nextToken()); // 간선의 개수

		graph = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		
		// 양방향 그래프
		for(int i=0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			graph[a].add(new Node(b, c));
			graph[b].add(new Node(a, c));
		}
		
		st = new StringTokenizer(br.readLine());
		v1 = Integer.parseInt(st.nextToken());
		v2 = Integer.parseInt(st.nextToken());
		
		dist = new int[N+1];
		
		int result1 = 0;
		result1 += dijkstra(1,v1);
		result1 += dijkstra(v1,v2);
		result1 += dijkstra(v2,N);
		
		int result2 = 0;
		result2 += dijkstra(1,v2);
		result2	+= dijkstra(v2,v1);
		result2	+= dijkstra(v1,N);
		
		System.out.println(result1+result2 >= 200000000 ? -1 : Math.min(result1, result2));
	}

	private static int dijkstra(int start, int end) {
		// 1번 정점에서 N번 정점으로 이동할 때, 
		// 주어진 두 정점을 반드시 거치면서 
		// 최단 경로로 이동하는 프로그램을 작성
		Arrays.fill(dist, 200000000);
		visited = new boolean[N+1];
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(!visited[temp.v]) {
				visited[temp.v] = true;
				
				for(Node n : graph[temp.v]) {
					if(dist[n.v] > dist[temp.v] + n.cost) {
						dist[n.v] = dist[temp.v] + n.cost;
						pq.add(new Node(n.v, dist[n.v]));
						
					}
				}
			}
		}
		
		return dist[end];
	}

}