package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_15649 {
	
	static int N,M;
	static int[] number;
	static boolean[] visited;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		sb = new StringBuilder();
		number = new int[M];
		visited = new boolean[N+1];
		perm(0);
		
		System.out.println(sb.toString());
	}

	private static void perm(int idx) {
		if(idx == M) {
			for(int i=0;i<M;i++) {
				sb.append(number[i]+" ");
			}
			sb.append("\n");
			return;
		}
		
		for(int i=1;i<=N;i++) {
			if(!visited[i]) {
				visited[i] = true;
				number[idx] = i;
				perm(idx+1);
				visited[i] = false;
			}
		}
	}
}
