package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_12851_숨바꼭질2 {
	
	static int N,K;
	static int[] visited;
	static int time,cnt;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		time = Integer.MAX_VALUE;
		visited = new int[100001];
		
		if(N == K) {
			time = 0;
			cnt = 1;
		}
		else
			bfs();

		System.out.println(time+"\n"+cnt);
	}

	private static void bfs() {
		Queue<Integer> q = new LinkedList<>();
		q.add(N);
		visited[N] = 1;
		
		while(!q.isEmpty()) {
			int now = q.poll();
			
			// 가지치기
			if(time < visited[now])
				return;
			
			for(int i=0;i<3;i++) {
				int next = 0;
				
				switch (i) {
				case 0:
					next = now + 1;
					break;
					
				case 1:
					next = now - 1;
					break;

				default:
					next = now * 2;
					break;
				}
				
				if(!is_valid(next)) continue;
				
				if(next == K) {
					time = visited[now];
					cnt++;
				}
				
				if(visited[next] == 0 || visited[next] == visited[now] + 1) {
					q.add(next);
					visited[next] = visited[now] + 1;
				}
			}
			
		}
		
	}
	
	private static boolean is_valid(int now) {
		if(now< 0 || now >= 100001) return false;
		return true;
	}

}