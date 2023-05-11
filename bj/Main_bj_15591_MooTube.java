package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_15591_MooTube {
	
	static int N,Q,result;
	static ArrayList<Node>[] list;
	static boolean[] visited;
	static class Node {
		int index, dist;
		public Node(int index, int dist) {
			this.index = index;
			this.dist = dist;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0;i<N-1;i++) {
			st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken());
			int q = Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			list[p].add(new Node(q,r));
			list[q].add(new Node(p,r));
		}
		
		for(int i=0;i<Q;i++) {
			result = 0;
			visited = new boolean[N+1];
			st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			solve(k,v);
			sb.append(result+"\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void solve(int k, int v) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(v, Integer.MAX_VALUE));
		
		while(!q.isEmpty()) {
			Node temp = q.poll();
			visited[temp.index] = true;
			
			for(Node t : list[temp.index]) {
				
				if(visited[t.index]) continue;
				
				int t_min = Math.min(t.dist, temp.dist);
				
				if(t_min >= k) {
					result++;
					q.add(new Node(t.index, t_min));
					visited[t.index] = true;
				}
			}
		}
	}

}
