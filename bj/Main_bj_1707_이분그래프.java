package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_1707_이분그래프 {
	
	static int V,E;
	static ArrayList<Integer>[] graph;
	static int[] visited;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			st = new StringTokenizer(br.readLine());
			
			V = Integer.parseInt(st.nextToken()); // 정점의 개수
			E = Integer.parseInt(st.nextToken()); // 간선의 개수
			
			graph = new ArrayList[V+1];
			visited = new int[V+1];
			for(int i=1;i<=V;i++) {
				graph[i] = new ArrayList<>();
			}
			
			for(int i=0;i<E;i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				
				graph[u].add(v);
				graph[v].add(u);
			} // 각각의 tc input end
			
			solve();
		}
		
		System.out.println(sb.toString());
	}

	private static void solve() {
		Queue<Integer> q = new LinkedList<>();
		
		for(int i=1;i<=V;i++) {
			if(visited[i] == 0) {
				q.add(i);
				visited[i] = 1;
			}
			
			while(!q.isEmpty()) {
				int temp = q.poll();
				
				for(int next : graph[temp]) {
					if(visited[next] == 0) {
						q.add(next);
					}
					
					if(visited[next] == visited[temp]) {
						sb.append("NO"+"\n");
						return;
					}
					
					if(visited[temp] == 1 && visited[next] == 0) {
						visited[next] = 2;
					}
					else if(visited[temp] == 2 && visited[next] == 0) {
						visited[next] = 1;
					}
				}
			}
		}
		
		sb.append("YES"+"\n");
	}

}
