package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_10775_공항 {
	
	static int G,P;
	static int[] parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		G = Integer.parseInt(br.readLine()); // 게이트 수
		P = Integer.parseInt(br.readLine()); // 비행기 수
		
		parent = new int[G+1];
		for(int i=1;i<=G;i++) {
			parent[i] = i;
		}
		
		int result = 0;
		for(int i=0;i<P;i++) {
			int num = Integer.parseInt(br.readLine());
			
			if(find(num) == 0) break;
			
			result++;
			
			union(find(num), find(num)-1);
		} // input end
		
		System.out.println(result);
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

	private static int find(int a) {
		if(a == parent[a]) return a;
		
		return parent[a] = find(parent[a]);
	}
}
