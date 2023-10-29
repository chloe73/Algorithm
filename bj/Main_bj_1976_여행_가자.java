package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1976_여행_가자 {
	
	static int N,M;
	static int[] parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		parent = new int[N+1];
		for(int i=1;i<=N;i++) {
			parent[i] = i;
		}
		
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1;j<=N;j++) {
				int temp = Integer.parseInt(st.nextToken());
				
				if(temp == 1) {
					union(i, j);
				}
			}
		}
		
		boolean flag = true;
		st = new StringTokenizer(br.readLine());
		int start = find(Integer.parseInt(st.nextToken()));
		for(int i=1;i<M;i++) {
			int temp = Integer.parseInt(st.nextToken());
			
			if(start != find(temp)) {
				flag = false;
				break;
			}
		}
		
		if(flag)
			sb.append("YES");
		else
			sb.append("NO");
			
		System.out.println(sb.toString());
	}
	
	private static int find(int x) {
		if(x == parent[x]) return x;
		
		return parent[x] = find(parent[x]);
	}
	
	private static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if(x != y) {
			if(x < y)
				parent[y] = x;
			else
				parent[x] = y;
		}
	}

}
