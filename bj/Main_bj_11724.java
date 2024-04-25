package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_11724 {
	
	static int N,M;
	static ArrayList<Integer>[] list;
	static boolean[] visited;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			list[i] = new ArrayList<>();
		}

		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			
			list[u].add(v);
			list[v].add(u);
		} // input end
		
		int result = 0;
		visited = new boolean[N+1];
		
		if(M > 0) {
			for(int i=1;i<=N;i++) {
				if(!visited[i]) {
					result++;
					visited[i] = true;
					bfs(i);
				}
			}			
		}
		else {
			result = N;
		}
		
		System.out.println(result);
	}

	private static void bfs(int i) {
		Queue<Integer> q = new LinkedList<>();
		q.add(i);
		
		while(!q.isEmpty()) {
			int temp = q.poll();
			
			for(int next : list[temp]) {
				if(!visited[next]) {
					visited[next] = true;
					q.add(next);
				}
			}
		}
	}

}
