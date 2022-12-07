package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1976_여행가자 {
	
	static int N,M;
	static int[] parent;
	static boolean flag;


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		parent = new int[N+1];
		for(int i=1;i<=N;i++) {
			parent[i] = i;
		}
		
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1;j<=N;j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 1) {
					union(i,j);
				}
			}
		}
		flag = true;
		st = new StringTokenizer(br.readLine());
		int start = find(Integer.parseInt(st.nextToken()));
		for(int i=1;i<M;i++) {
			int now = Integer.parseInt(st.nextToken());
			
			if(start != find(now)) {
				flag = false;
				break;
			}
		}
		
		if(flag) System.out.println("YES");
		else System.out.println("NO");
	}

	private static int find(int x) {
		if(x == parent[x]) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
	
	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		if(x != y) {
			if(x < y) {
				parent[y] = x;
			} else {
				parent[x] = y;
			}
		}
	}

}
