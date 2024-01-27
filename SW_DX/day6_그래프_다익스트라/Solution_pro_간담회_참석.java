package algo.SW_DX.day6_그래프_다익스트라;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_pro_간담회_참석 {
	
	static int N,M,X,result;
	static class Node implements Comparable<Node>{
		int to,cost;
		Node(int to, int cost){
			this.to = to;
			this.cost = cost;
		}
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			ArrayList<Node>[] list1 = new ArrayList[N+1];
			ArrayList<Node> [] list2 = new ArrayList[N+1];
			for(int i=0;i<=N;i++) {
				list1[i] = new ArrayList<>();
				list2[i] = new ArrayList<>();
			}
			
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());
				
				list1[s].add(new Node(e, t));
				list2[e].add(new Node(s, t));
			} // input end
			
			int[] dist1 = new int[N+1];
			int[] dist2 = new int[N+1];
			Arrays.fill(dist1, Integer.MAX_VALUE);
			Arrays.fill(dist2, Integer.MAX_VALUE);
			
			dijkstra(X,list1,dist1);
			dijkstra(X,list2,dist2);
			
			result = Integer.MIN_VALUE;
			for(int i=1;i<=N;i++) {
				if(dist1[i] == Integer.MAX_VALUE || dist2[i] == Integer.MAX_VALUE) continue;
				result = Math.max(result, dist1[i] + dist2[i]);
			}
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void dijkstra(int start, ArrayList<Node>[] list, int[] dist) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[N+1];
		
		dist[start] = 0;
		pq.add(new Node(start, 0));
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(visited[temp.to]) continue;
			
			visited[temp.to] = true;
			
			for(Node next : list[temp.to]) {
				if(dist[next.to] > dist[temp.to] + next.cost) {
					dist[next.to] = dist[temp.to] + next.cost;
					pq.add(new Node(next.to, dist[next.to]));
				}
			}
		}
	}

}
