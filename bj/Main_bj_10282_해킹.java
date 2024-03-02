package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_10282_해킹 {
	
	static int N,D,C;
	static ArrayList<Node>[] graph;
	static int[] dist;
	static class Node implements Comparable<Node>{
		int to,time;
		public Node(int to, int time) {
			this.to = to;
			this.time = time;
		}
		public int compareTo(Node o) {
			return this.time - o.time;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			D = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			dist = new int[N+1];
			graph = new ArrayList[N+1];
			for(int i=1;i<=N;i++) {
				graph[i] = new ArrayList<>();
			}
			
			for(int i=0;i<D;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int s = Integer.parseInt(st.nextToken());
				
				graph[b].add(new Node(a, s));
			} // input end
			
			dijkstra(C);
			
			int cnt = 0;
			int time = 0;
			for(int i=1;i<=N;i++) {
				if(dist[i] != Integer.MAX_VALUE) {
					cnt++;
					time = Math.max(time, dist[i]);
				}
			}
			 
			sb.append(cnt+" "+ time +"\n");
		}

		System.out.println(sb.toString());
	}
	
	private static void dijkstra(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			for(Node next : graph[temp.to]) {
				if(dist[next.to] > dist[temp.to] + next.time) {
					dist[next.to] = dist[temp.to] + next.time;
					pq.add(new Node(next.to, dist[next.to]));
				}
			}
		}
	}

}
