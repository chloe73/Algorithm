package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_24042 {
	
	static int N,M,result;
	static ArrayList<Node>[] graph;
	static ArrayList<int[]> cmd;
	static class Node {
		int to, signal;
		public Node(int to, int signal) {
			this.to = to;
			this.signal = signal;
		}
	}
	static class Point implements Comparable<Point>{
		int num,time;
		int beforeSignal;
		public Point(int num, int time, int beforeSignal) {
			this.num = num;
			this.time = time;
			this.beforeSignal = beforeSignal;
		}
		public int compareTo(Point o) {
			return this.time - o.time;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		
		cmd = new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			cmd.add(new int[] {a,b});
			graph[a].add(new Node(b, i));
			graph[b].add(new Node(a, i));
		} // input end
		
		solve();
		
		System.out.println(result+1);
	}

	private static void solve() {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(1, 0, 0));
		result = Integer.MAX_VALUE;
		int[] visited = new int[N+1];
		Arrays.fill(visited, Integer.MAX_VALUE);
		visited[1] = 0;
		
		while(!pq.isEmpty()) {
			Point p = pq.poll();
			
			if(visited[p.num] < p.time) continue;
			
			for(Node next : graph[p.num]) {
				
				int s = p.beforeSignal; 
				int[] signal = cmd.get(s);
//				if((signal[0] == p.num || signal[1] == p.num) && (signal[0] == next.to || signal[1] == next.to)
//						&& visited[next.to] > p.time+1) {
//					visited[next.to] = p.time+1;
//					pq.add(new Point(next.to, p.time+1, s));
//					continue;
//				}

				int t = 0;
				while(true) {
					s = change_signal(s);
					signal = cmd.get(s);
					t++;
					
					if((signal[0] == p.num || signal[1] == p.num) && (signal[0] == next.to || signal[1] == next.to)) {
						break;
					}
				}
				
				if(visited[next.to] > p.time+t) {
					visited[next.to] = p.time+t;
					pq.add(new Point(next.to, p.time+t, s));					
				}
			}
		}
		
		result = visited[N];
	}
	
	private static int change_signal(int s) {
		if(s == M-1) return 0;
		return s+1;
	}

}
