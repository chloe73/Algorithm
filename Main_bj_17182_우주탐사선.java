package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_17182_우주탐사선 {
	
	static int N,K,result = Integer.MAX_VALUE;
	static int[][] dist;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		dist = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				dist[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		// 플로이드 - 워샬 알고리즘
		for(int k=0;k<N;k++) {
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
				}
			}
		}
		
		visited = new boolean[N];
		visited[K] = true;
		solve(K,0,0);
		
		System.out.println(result);
	}

	private static void solve(int now, int sum, int depth) {
		if(depth == N-1) {
			result = Math.min(result, sum);
			return;
		}
		
		for(int i=0;i<N;i++) {
			if(!visited[i]) {
				visited[i] = true;
				solve(i, sum+dist[now][i], depth+1);
				visited[i] = false;
			}
		}
	}

}
