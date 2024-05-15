package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1719 {
	
	static int N,M;
	static StringBuilder sb;
	static int[][] result;
	static ArrayList<Point>[] graph;
	static class Point implements Comparable<Point>{
		int to,cost;
		public Point(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
		public int compareTo(Point o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		result = new int[N+1][N+1];
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
			dijkstra(i);
		}
		
		for(int i=1;i<=N;i++) {
			for(int j=1;j<=N;j++) {
				if(i == j) {
					sb.append("- ");
				}
				else {
					sb.append(result[i][j]+" ");
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void dijkstra(int i) {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		int[] dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[i] = 0;
		pq.add(new Point(i, 0));
		
		while(!pq.isEmpty()) {
			Point temp = pq.poll();
			
			for(Point next : graph[temp.to]) {
				if(dist[next.to] > dist[temp.to] + next.cost) {
					dist[next.to] = temp.cost + next.cost;
					result[next.to][i] = temp.to;
					pq.add(new Point(next.to, temp.cost+next.cost));
					
				}
			}
		}

		return;
	}

}
