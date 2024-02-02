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

public class Main_bj_1884_고속도로 {
	
	static int K,N,result;
	static ArrayList<Node>[] road;
	static int[][] cost;
	static class Node implements Comparable<Node>{
		int to,len,cost;
		public Node(int to, int len, int cost) {
			this.to = to;
			this.len = len;
			this.cost = cost;
		}
		public int compareTo(Node o) {
			return this.len - o.len;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		K = Integer.parseInt(br.readLine());
		N = Integer.parseInt(br.readLine());
		int R = Integer.parseInt(br.readLine());
		
		road = new ArrayList[N+1];
		// A에 도착해서 남은 교통비가 B일 때의 최단 거리를 기록하는 Cost[A][B] 2차원 배열
		cost = new int[N+1][K+1];
		for(int i=0;i<=N;i++) {
			road[i] = new ArrayList<>();
			for(int j=0;j<=K;j++) {
				cost[i][j] = Integer.MAX_VALUE;
			}
		}
		
		for(int i=0;i<R;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()); // 출발 도시
			int d = Integer.parseInt(st.nextToken()); // 도착 도시
			int l = Integer.parseInt(st.nextToken()); // 도로의 길이
			int t = Integer.parseInt(st.nextToken()); // 도로의 통행료
			
			road[s].add(new Node(d, l, t));
		} // input end
		
		result = Integer.MAX_VALUE;
		
		solve();
		
		for(int i=0;i<=K;i++) {
			result = Math.min(result, cost[N][i]);
		}
		
//		첫 줄에 정해진 예산 내에서 이용할 수 있는 경로 중 제일 짧은 것의 길이를 출력한다. 만약 가능한 경로가 없을 때에는 -1을 출력한다.
		if(result == Integer.MAX_VALUE) result = -1;
		bw.write(result+"");
		bw.flush();
		br.close();
		bw.close();
	}
	
	private static void solve() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(1, 0, K));
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			if(cost[temp.to][temp.cost] < temp.len) continue; 
			
			for(Node next : road[temp.to]) {
				
				if(temp.cost < next.cost) continue;
				
				if(cost[next.to][temp.cost-next.cost] > temp.len + next.len) {
					cost[next.to][temp.cost-next.cost] = temp.len + next.len;
					pq.add(new Node(next.to, cost[next.to][temp.cost-next.cost], temp.cost-next.cost));
				}
			}
		}
	}

}
