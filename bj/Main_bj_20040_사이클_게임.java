package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_20040_사이클_게임 {
	
	static int N;
	static int[] parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		parent = new int[N];
		for(int i=0;i<N;i++) {
			parent[i] = i;
		}
		
		int result = 0;
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if(find(a) == find(b)) {
				result = i;
				break;
			}
			else {
				union(a, b);
			}
		} // input end

		System.out.println(result);
	}
	
	private static int find(int a) {
		if(a == parent[a]) return a;
		
		return parent[a] = find(parent[a]);
	}
	
	private static void union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		if(A < B) {
			parent[B] = A;
		}
		else {
			parent[A] = B;
		}
	}

}
