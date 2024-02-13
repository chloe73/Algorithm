package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1717_집합의_표현 {
	
	static int N,M;
	static int[] parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		parent = new int[N+1];
		for(int i=0;i<=N;i++) {
			parent[i] = i;
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int cmd = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if(cmd == 0	) {
				// 합집합은 0 a b의 형태로 입력이 주어진다. 
				// 이는 a가 포함되어 있는 집합과, b가 포함되어 있는 집합을 합친다는 의미이다. 
				union(a, b);
			}
			else {
				// 두 원소가 같은 집합에 포함되어 있는지를 확인하는 연산은 
				// 1 a b의 형태로 입력이 주어진다. 
				// 이는 a와 b가 같은 집합에 포함되어 있는지를 확인하는 연산이다.
				int A = find(a);
				int B = find(b);
				
				if(A == B) sb.append("YES"+"\n");
				else sb.append("NO"+"\n");
			}
		} // input end

		System.out.println(sb.toString());
	}
	
	private static void union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		if(A == B) return;
		
		if(A>B)
			parent[A] = B;
		else 
			parent[B] = A;
	}
	
	private static int find(int x) {
		if(x == parent[x]) return x;
		return parent[x] = find(parent[x]);
	}

}
