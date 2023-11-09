package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_11779_최소비용_구하기_2 {
	
	static int N,M,start,end;
	static int[] dist;
	static int[] route;
	static ArrayList<Node>[] list;
	static class Node implements Comparable<Node>{
		int e;
		int cost;
		
		public Node(int e, int cost) {
			this.e = e;
			this.cost = cost;
		}
		
		public int compareTo(Node o) {
			return this.cost-o.cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		list = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			list[s].add(new Node(e, c));
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		dist = new int[N+1];
		route = new int[N+1];
		Arrays.fill(dist, 1000000001);
		
		dijkstra();
		
		sb.append(dist[end]).append("\n");
		
		Stack<Integer> stack = new Stack<>();
		stack.add(end);
		
		while(true) {
			if(end == start)
				break;
			
			stack.add(route[end]);
			end = route[end];
		}
		
		sb.append(stack.size()).append("\n");
		
		while(!stack.isEmpty()) {
			sb.append(stack.pop()).append(" ");
		}
		
		System.out.println(sb.toString());
	}

	private static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start, 0));
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			if(dist[temp.e] < temp.cost)
				continue;
			
			if(temp.e == end)
				break;
			
			for(Node n : list[temp.e]) {
				if(dist[temp.e] + n.cost < dist[n.e]) {
					dist[n.e] = dist[temp.e] + n.cost;
					pq.add(new Node(n.e, dist[n.e]));
					route[n.e] = temp.e;
				}
			}
		}
	}

}
