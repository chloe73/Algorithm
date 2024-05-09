package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_5567 {
	
	static int N,M,result;
	static ArrayList<Integer>[] graph;
	static boolean[] visited;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		graph = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		
		for(int i=0;i<M;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			graph[a].add(b);
			graph[b].add(a);
		} // input end
		
		// 상근이는 자신의 결혼식에 학교 동기 중 자신의 친구와 친구의 친구를 초대하기로 했다.
		
		result = 0;
		if(graph[1].size() > 0) {
			visited = new boolean[N+1];
			visited[1] = true;
			
			for(int next : graph[1]) {
				find(next);
			}
			
			for(int i=2;i<=N;i++) {
				if(visited[i]) result++;
			}
		}
		
		System.out.println(result);
	}

	private static void find(int next) {
		
		visited[next] = true;
		
		for(int k : graph[next]) {
			if(!visited[k]) {
				visited[k] = true;				
			}
		}
	}

}
