package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_11725_트리의_부모_찾기 {
	
	static int N;
	static ArrayList<Integer>[] tree;
	static boolean[] visited;
	static int[] result;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		tree = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			tree[i] = new ArrayList<>();
		}
		
		for(int i=0;i<N-1;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			tree[a].add(b);
			tree[b].add(a);
		} // input end
		
		visited = new boolean[N+1];
		result = new int[N+1];
		
		solve(1);

		System.out.println(sb.toString());
		
		br.close();
	}

	private static void solve(int p) {
		
		visited[p] = true;
		Queue<Integer> q = new LinkedList<>();
		
		for(int i : tree[p]) {
			result[i] = p;
			visited[i] = true;
			q.add(i);
		}
		
		while(!q.isEmpty()) {
			int temp = q.poll();
			
			for(int i : tree[temp]) {
				if(!visited[i]) {
					result[i] = temp;
					visited[i] = true;
					q.add(i);					
				}
			}
		}
		
		for(int i=2;i<=N;i++) {
			sb.append(result[i]).append("\n");
		}
	}

}
