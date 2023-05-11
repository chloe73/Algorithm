package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_11780_플로이드2 {
	
	static int N,M;
	static int[][] dist,path;
	static int INF = 1000000;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		dist = new int[N+1][N+1];
		path = new int[N+1][N+1];
		for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                path[i][j] = -1;
                if(i == j) continue;
                dist[i][j] = INF;
            }
        }
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			dist[a][b] = Math.min(dist[a][b], c);
			path[a][b] = a;
		} // input end
		
		solve();
		
		print();
		
	}

	private static void print() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=1;i<=N;i++) {
			for(int j=1;j<=N;j++) {
				if(dist[i][j] == INF) dist[i][j] = 0;
				sb.append(dist[i][j]+" ");
			}
			sb.append("\n");
		}
		
		for(int i=1;i<=N;i++) {
			for(int j=1;j<=N;j++) {
				if(path[i][j] == -1) sb.append(0 + "\n");
				else {
					Stack<Integer> p = new Stack<>();
					int pre = j;
					p.add(pre);
					
					while(i != path[i][pre]) {
						pre = path[i][pre];
						p.push(pre);
					}
					
					sb.append(p.size()+1+" ");
					
					sb.append(i+" ");
					while(!p.isEmpty()) {
						sb.append(p.pop()+" ");
					}
					sb.append("\n");
				}
			}
		}
		
		System.out.println(sb.toString());
	}

	private static void solve() {
		
		for(int k=1;k<=N;k++) {
			for(int i=1;i<=N;i++) {
				for(int j=1;j<=N;j++) {
					if(dist[i][j] > dist[i][k]+dist[k][j]) {
						dist[i][j] = dist[i][k]+dist[k][j];
						path[i][j] = path[k][j];						
					}
				}
			}
		}
		
	}

}
