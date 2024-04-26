package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_1967 {
	
	static int N,result;
	static ArrayList<Node>[] list;
	static boolean[] visited;
	static class Node {
		int s,cost;
		public Node(int s, int cost) {
			this.s = s;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		list = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0;i<N-1;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int p = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			list[p].add(new Node(s, cost));
			list[s].add(new Node(p, cost));
		} // input end
		
		result = 0;
		for(int i=1;i<=N;i++) {
			visited = new boolean[N+1];
			visited[i] = true;
			dfs(i,0,visited);
		}

		System.out.println(result);
	}

	private static void dfs(int temp, int sum, boolean[] visited) {
		
		result = Math.max(result, sum);
		
		for(Node next : list[temp]) {
			if(visited[next.s]) continue;
			
			visited[next.s] = true;
			dfs(next.s, sum+next.cost, visited);
		}
	}

}
