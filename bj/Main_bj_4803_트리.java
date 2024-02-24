package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_bj_4803_트리 {
	
	static int N,M;
	static int[] parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int tc = 0;
		while(true) {
			tc++;
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			if(N == 0 && M == 0) break;
			
			parent = new int[N+1];
			for(int i=1;i<=N;i++) {
				parent[i] = i;
			}
			// 트리는 사이클이 없는 연결 요소이다. 
			// 트리에는 여러 성질이 있다. 
			// 트리는 정점이 n개, 간선이 n-1개 있다. 
			// 또, 임의의 두 정점에 대해서 경로가 유일하다.
			for(int i=0;i<M;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());

				union(a, b);
			} // 간선 정보 input end
			
			HashSet<Integer> tree = new HashSet<>();
			for(int i=1;i<=N;i++) {
				int A = find(i);
				if(A > 0) tree.add(A);
			}
			
			sb.append("Case "+tc+": ");
			if(tree.size() == 0) {
				sb.append("No trees."+"\n");
			}
			else if(tree.size() == 1) {
				sb.append("There is one tree."+"\n");
			}
			else {
				sb.append("A forest of "+tree.size()+" trees."+"\n");
			}
		} // input end
		
		System.out.println(sb.toString());
	}
	
	private static void union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		if(A > B) {
			int temp = A;
			A = B;
			B = temp;
		}
		
		if(A == B) {
			parent[A] = 0;
		}
		else {
			parent[B] = A;
		}
	}
	
	private static int find(int a) {
		if(a == parent[a]) return a;
		
		return parent[a] = find(parent[a]);
	}

}
