package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_16562_친구비 {
	
	static int N,M,K;
	static int[] friendCost, parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		friendCost = new int[N+1];
		parent = new int[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=N;i++) {
			friendCost[i] = Integer.parseInt(st.nextToken());
			parent[i] = i;
		}
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			union(v, w);
		} // input end
		
		int sum = 0;
		
		for(int i=1;i<=N;i++) {
			if(parent[i] == i) {
				sum += friendCost[i];
			}
		}
		
		if(K-sum < 0)
			System.out.println("Oh no");
		else
			System.out.println(sum);
	}
	
	private static void union(int a, int b) {
		int A = find(a);
		int B = find(b);
		
		if(A != B) {
			if(friendCost[A] > friendCost[B]) {
				parent[A] = B;
			}
			else {
				parent[B] = A;
			}
		}
	}
	
	private static int find(int a) {
		if(a == parent[a]) return a;
		
		return parent[a] = find(parent[a]);
	}

}
