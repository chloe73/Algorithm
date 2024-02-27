package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_2310_어드벤처_게임 {
	
	static int N;
	static ArrayList<Integer>[] graph;
	static boolean[] visited;
	static char[] cmd;
	static int[] cost;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			N = Integer.parseInt(br.readLine());
			
			if(N == 0) break;
			
			graph = new ArrayList[N+1];
			visited = new boolean[N+1];
			cmd = new char[N+1];
			cost = new int[N+1];
			for(int i=1;i<=N;i++) {
				graph[i] = new ArrayList<>();
			}
			
			for(int i=1;i<=N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				String s = st.nextToken();
				
				if(s.equals("E")) {
					//빈 방
					cmd[i] = 'E';
					cost[i] = Integer.parseInt(st.nextToken());
					
					while(true) {
						int next = Integer.parseInt(st.nextToken());
						
						if(next == 0) break;
						
						graph[i].add(next);
					}
				}
				else if(s.equals("L")) {
					// 레프리콘
					cmd[i] = 'L';
					cost[i] = Integer.parseInt(st.nextToken());
					
					while(true) {
						int next = Integer.parseInt(st.nextToken());
						
						if(next == 0) break;
						
						graph[i].add(next);
					}
				}
				else {
					// 트롤
					cmd[i] = 'T';
					cost[i] = Integer.parseInt(st.nextToken());
					
					while(true) {
						int next = Integer.parseInt(st.nextToken());
						
						if(next == 0) break;
						
						graph[i].add(next);
					}
				}
			}
			
			solve(1,0);
			
			boolean flag = true;
			for(int i=1;i<=N;i++) {
				if(!visited[i]) {
					flag = false;
					break;
				}
			}
			
			if(flag)
				sb.append("Yes"+"\n");
			else
				sb.append("No"+"\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void solve(int tempIdx, int coin) {
		
		if(tempIdx == N+1) return;
		
		char temp = cmd[tempIdx];
		
		if(visited[tempIdx]) return;
		visited[tempIdx] = true;
		
		if(temp == 'E') {
			// 빈 방
			for(int next : graph[tempIdx]) {
				solve(next,coin);
			}
		}
		else if(temp == 'L') {
			if(coin < cost[tempIdx]) {
				coin = cost[tempIdx];
			}
			for(int next : graph[tempIdx]) {				
				solve(next, coin);
			}
		}
		else {
			if(coin >= cost[tempIdx]) {
				coin -= cost[tempIdx];
				for(int next : graph[tempIdx]) {					
					solve(next, coin);
				}
			}
			else {
				visited[tempIdx] = false;
				return;
			}
		}
	}
}
