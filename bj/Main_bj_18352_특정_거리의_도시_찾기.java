package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_18352_특정_거리의_도시_찾기 {
	
	static int N,M,K,X;
	static ArrayList<Node>[] graph;
	static int[] dist;
	static boolean[] visited;
	static class Node implements Comparable<Node>{
		int num;
		int cost;
		
		Node(int num, int dist){
			this.num = num;
			this.cost = dist;
		}
		
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		visited = new boolean[N+1];
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			graph[x].add(new Node(y,1));
		}

		solve();
		
		boolean flag = false;
		for(int i=1;i<=N;i++) {
			if(dist[i] == K) {
				flag = true;
				bw.write(i+"\n");
			}
		}
		
		if(!flag) bw.write(-1+"");

		bw.flush();
		br.close();
		bw.close();
	}

	private static void solve() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(X,0));
		dist[X] = 0;
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			
			if(!visited[now.num])
				visited[now.num] = true;
			
			for(Node next : graph[now.num]) {
				if(!visited[next.num] && dist[next.num] > now.cost + next.cost) {
					dist[next.num] = now.cost + next.cost;
					pq.add(new Node(next.num, dist[next.num]));
				}
			}
		}
		
		
	}

}
