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

public class Solution_pro_가장_짧은_길_전부_청소하기 {
	
	static int N,M;
	static long result;
	static ArrayList<Node>[] graph;
	static long[] sumDist;
	static int[] dist;
	static class Node implements Comparable<Node>{
		int to;
		long cost;
		
		Node(int to, long cost){
			this.to = to;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return Long.compare(this.cost ,o.cost);
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
			
			graph = new ArrayList[N+1];
			for(int i=0;i<=N;i++) {
				graph[i] = new ArrayList<>();
			}
			
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				
				graph[x].add(new Node(y, cost));
				graph[y].add(new Node(x, cost));
			} // input end
			
			solve();
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void solve() {
		sumDist = new long[N+1];
		Arrays.fill(sumDist, Long.MAX_VALUE);
		dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		sumDist[1] = 0;
		dist[1] = 0;
		pq.add(new Node(1, 0));
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(temp.cost > sumDist[temp.to]) continue;
			
			for(Node next : graph[temp.to]) {
				
				if(sumDist[next.to] > temp.cost + next.cost) {
					sumDist[next.to] = temp.cost + next.cost;
					dist[next.to] = (int) next.cost;
					pq.add(new Node(next.to, sumDist[next.to]));
				}
				else if(sumDist[next.to] == temp.cost + next.cost) {
					dist[next.to] = Math.min(dist[next.to], (int)next.cost);
				}
			}
		}

		result = 0;
		
		for(int i=1;i<=N;i++) {
			result += dist[i];
		}
	}

}
