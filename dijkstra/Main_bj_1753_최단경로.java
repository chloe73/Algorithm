package algo.dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1753_최단경로 {
	
	static int V,E,K,result;
	static ArrayList<Node>[] graph;
	static int[] dist;
	static boolean[] visited;
	static class Node implements Comparable<Node> {
		int v;
		int cost;
		
		public Node(int destination, int weight) {
			this.v = destination;
			this.cost = weight;
		}
		
		public int compareTo(Node o) {
			return this.cost - o.cost; // 오름차순
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 첫번째 줄 입력
		st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		// 두번째 줄 입력
		K = Integer.parseInt(br.readLine());
		
		graph = new ArrayList[V+1];
		dist = new int[V+1];
		visited = new boolean[V+1];
		for(int i=1;i<=V;i++) {
			graph[i] = new ArrayList<>();
			dist[i] = Integer.MAX_VALUE; // 최댓값으로 초기화, 최단거리를 찾기 위해서
		}
		// 3번째 줄 ~ E개의 줄 입력받기
		for(int i=0;i<E;i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			graph[u].add(new Node(v, w));
		}
		
		dijkstra(K);
		
		for(int i=1;i<=V;i++) {
			System.out.println(dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]);
		}
	}

	private static void dijkstra(int start) {
		//우선 순위 큐 사용, 가중치를 기준으로 오름차순한다.
//      PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			// 현재 최단거리가 가장 짧은 노드 꺼내서 방문 처리한다.
			Node now = pq.poll();
			
			if(!visited[now.v]) visited[now.v] = true;
			
			for(Node next : graph[now.v]) {
				
				// 방문하지 않았고 현재 노드를 거쳐서 다른 노드로 이동하는 거리가 더 짧으면
				if(!visited[next.v] && dist[next.v] > now.cost + next.cost) {
					dist[next.v] = now.cost + next.cost;
					pq.add(new Node(next.v, dist[next.v]));
				}
			}
		}
	}

}
