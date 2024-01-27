package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1854_K번째_최단경로_찾기 {
	
	static int N,M,K;
	static ArrayList<Node>[] list;
	static PriorityQueue<Integer>[] dist;
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
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N+1];
		dist = new PriorityQueue[N+1];
		for(int i=0;i<=N;i++) {
			list[i] = new ArrayList<>();
			dist[i] = new PriorityQueue<>(Collections.reverseOrder()); // 최대 힙
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			
			list[s].add(new Node(e, t));
		} // input end

		dijkstra(1);
		
		for(int i=1;i<=N;i++) {
			if(dist[i].size() == K) bw.write(dist[i].peek()+"\n");
			else bw.write(-1+"\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}

	private static void dijkstra(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start,0));
		dist[start].add(0);
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			for(Node next : list[temp.to]) {
				
				if(dist[next.to].size() < K) {
					// 경로 K개 채워질 때까지 추가
					dist[next.to].add(temp.cost + next.cost);
					pq.add(new Node(next.to, temp.cost+next.cost));
				}
				// 경로 K개의 최대경로가 현재 비용보다 크면 pq에서 빼내고
				// 현재 경로로 추가하기
				// 왜냐하면, K번째 최단경로를 찾는 문제이므로.
				else if(dist[next.to].peek() > temp.cost+next.cost) {
					dist[next.to].poll();
					dist[next.to].add(temp.cost+next.cost);
					pq.add(new Node(next.to, temp.cost+next.cost));
				}
			}
		}
		
	}

}
