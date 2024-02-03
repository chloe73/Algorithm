package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_9370_미확인_도착지 {
	
	static int N,S,G,H;
	static ArrayList<Node>[] road;
	static ArrayList<Integer> resultList;
	static class Node implements Comparable<Node> {
		int to,cost;
		public Node(int to, int cost) {
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
		StringBuilder sb = new StringBuilder();
		
		// 첫 번째 줄에는 테스트 케이스의 T(1 ≤ T ≤ 100)가 주어진다. 각 테스트 케이스마다
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			// 첫 번째 줄에 3개의 정수 n, m, t (2 ≤ n ≤ 2 000, 1 ≤ m ≤ 50 000 and 1 ≤ t ≤ 100)가 주어진다. 각각 교차로, 도로, 목적지 후보의 개수이다.
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			
			// 두 번째 줄에 3개의 정수 s, g, h (1 ≤ s, g, h ≤ n)가 주어진다. s는 예술가들의 출발지이고, g, h는 문제 설명에 나와 있다. (g ≠ h)
			st = new StringTokenizer(br.readLine());
			S = Integer.parseInt(st.nextToken());
			G = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			// 그 다음 m개의 각 줄마다 3개의 정수 a, b, d (1 ≤ a < b ≤ n and 1 ≤ d ≤ 1 000)가 주어진다. a와 b 사이에 길이 d의 양방향 도로가 있다는 뜻이다.
			road = new ArrayList[N+1];
			for(int i=0;i<=N;i++) {
				road[i] = new ArrayList<>();
			}
			for(int i=0;i<m;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				road[a].add(new Node(b, c));
				road[b].add(new Node(a, c));
			}
			
			// 그 다음 t개의 각 줄마다 정수 x가 주어지는데, t개의 목적지 후보들을 의미한다. 이 t개의 지점들은 서로 다른 위치이며 모두 s와 같지 않다.
			resultList = new ArrayList<>();
			for(int i=0;i<t;i++) {
				int x = Integer.parseInt(br.readLine());
				
				// S에서 x까지 가는 최단경로가 (G,H) 사이를 지나가면 가능한거임.
				// (S->G) + (G->H) + (H->x) 최단경로
				int result1 = dijkstra(S, G) + dijkstra(G, H) +dijkstra(H, x);
				// (S->H) + (H->G) + (G->x) 최단경로
				int result2 = dijkstra(S, H) + dijkstra(H, G) + dijkstra(G, x);
				// (S->x) 최단경로
				int result3 = dijkstra(S, x);
				
				if(Math.min(result1, result2) == result3)
					resultList.add(x);
			}
			
			// 입력에서 주어진 목적지 후보들 중 불가능한 경우들을 제외한 목적지들을 공백으로 분리시킨 오름차순의 정수들로 출력한다.
			Collections.sort(resultList);
			for(int i : resultList) {
				sb.append(i+" ");
			}
			sb.append("\n");
		} // input end
		
		bw.write(sb.toString());
		bw.flush();
		br.close();
		bw.close();
	}

	private static int dijkstra(int start, int end) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[] dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		pq.add(new Node(start,0));
		dist[start] = 0;
		
		// 최단 경로 중 -> G, H를 지나가면 return true;
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(dist[temp.to] < temp.cost) continue;
			
			for(Node next : road[temp.to]) {
				
				if(dist[next.to] > temp.cost + next.cost) {
					dist[next.to] = temp.cost + next.cost;
					pq.add(new Node(next.to, dist[next.to]));
				}
			}
		}
		return dist[end];
	}

}
