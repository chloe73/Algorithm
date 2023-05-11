package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_11404_플로이드 {
	
	static int N,M;
	static int[][] dist;
	static int INF = 1000000000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		dist = new int[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(i == j) continue;
				dist[i][j] = INF;
			}
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			dist[x-1][y-1] = Math.min(dist[x-1][y-1], c);
		} // input end
		
		solve();
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(dist[i][j] == INF) sb.append(0+" ");
				else sb.append(dist[i][j]+" ");
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void solve() {
		
		for(int k=0;k<N;k++) {
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
				}
			}
		}

	} 
	
}
