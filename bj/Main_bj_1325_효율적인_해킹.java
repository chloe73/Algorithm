package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_1325_효율적인_해킹 {
	
	static int N,M,max;
	static ArrayList<Integer>[] graph;
	static ArrayList<Integer> result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}

		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			graph[b].add(a);
		} // input end
		
		result = new ArrayList<>();
		max = 0;
		for(int i=1;i<=N;i++) {
			int ret = 0;
			
			Queue<Integer> q = new LinkedList<>();
			boolean[] visited = new boolean[N+1];
			q.add(i);
			visited[i] = true;
			
			while(!q.isEmpty()) {
				int temp = q.poll();
				
				for(int next : graph[temp]) {
					if(!visited[next]) {
						visited[next] = true;
						ret++;
						q.add(next);
					}
				}
			}
			
			
			if(ret > max) {
				max = ret;
				result = new ArrayList<>();
				result.add(i);
			}
			else if(ret == max) {
				result.add(i);
			}
		}
		
		for(int i :result) {
			sb.append(i+" ");
		}
		
		System.out.println(sb.toString());
	}
	
	private static int bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[N+1];
		q.add(start);
		visited[start] = true;
		int cnt = 0;
		
		while(!q.isEmpty()) {
			int temp = q.poll();
			
			for(int next : graph[temp]) {
				if(!visited[next]) {
					visited[next] = true;
					cnt++;
					q.add(next);
				}
			}
		}
		
		return cnt;
	}

}
