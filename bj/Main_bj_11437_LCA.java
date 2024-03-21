package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_11437_LCA {
	
	static int N;
	static ArrayList<Integer>[] list;
	static int[] depth, parent;

	public static void main(String[] args) throws IOException{
		BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		list = new ArrayList[N+1];
		depth = new int[N+1];
		parent = new int[N+1];
		for(int i=1;i<=N;i++) {
			list[i] = new ArrayList<>();
		}

		for(int i=0;i<N-1;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			list[a].add(b);
			list[b].add(a);
		}
		
		init(1, 1, 0);
		
		int M = Integer.parseInt(br.readLine());
		for(int i=0;i<M;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			sb.append(LCA(a, b)+"\n");
		} // input end
		
		System.out.println(sb.toString());
	}
	
	private static void init(int temp, int h, int p) {
		depth[temp] = h;
		parent[temp] = p;
		
		for(int next: list[temp]) {
			if(next != p) {
				init(next, h+1, temp);
			}
		}
	}
	
	private static int LCA(int a, int b) {
		int ah = depth[a];
		int bh = depth[b];
		
		while(ah > bh) {
			a = parent[a];
			ah--;
		}
		
		while(bh > ah) {
			b = parent[b];
			bh--;
		}
		
		while(a != b) {
			a = parent[a];
			b = parent[b];
		}
		
		return a;
	}

}
