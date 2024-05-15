package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1719 {
	
	static int N,M;
	static StringBuilder sb;
	static ArrayList<Point>[] graph;
	static class Point {
		int to,cost;
		public Point(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[a].add(new Point(b, cost));
			graph[b].add(new Point(a, cost));
		} // input end
		
		for(int i=1;i<=N;i++) {
			for(int j=1;j<=N;j++) {
				if(i == j) {
					sb.append("- ");
				}
				else {
					dijkstra(i,j);
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	static class Node implements Comparable<Node>{
		int to,first,cost;
		public Node(int to, int first, int cost) {
			this.to = to;
			this.first = first;
			this.cost = cost;
		}
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	private static void dijkstra(int i, int j) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[] dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[i] = 0;
		pq.add(new Node(i, 0, 0));
		ArrayList<int[]> target = new ArrayList<>();
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(temp.to == j) {
				target.add(new int[] {temp.first, temp.cost});
				continue;
			}
			
			for(Point next : graph[temp.to]) {
				if(dist[next.to] > dist[temp.to] + next.cost) {
					dist[next.to] = temp.cost + next.cost;
					if(temp.first == 0) {
						pq.add(new Node(next.to, next.to, temp.cost + next.cost));
					}
					else {
						pq.add(new Node(next.to, temp.first, temp.cost+next.cost));
					}
				}
			}
		}
		
		Collections.sort(target, (o1,o2) -> {
			return o1[1]-o2[1]; // 최소비용 순으로 정렬
		});
		sb.append(target.get(0)[0]+" ");
		return;
	}

}
