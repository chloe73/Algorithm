package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1766_문제집 {
	
	static int N,M;
	static ArrayList<Integer>[] graph;
	static int[] beSolvedFirst;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		beSolvedFirst = new int[N+1];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			graph[a].add(b);
			beSolvedFirst[b]++;
		} // input end
		
		bfs();
		System.out.println(sb.toString());
	}
	
	private static void bfs() {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		
		for(int i=1;i<=N;i++) {
			if(beSolvedFirst[i] == 0) 
				pq.add(i);
		}
		
		while(!pq.isEmpty()) {
			int temp = pq.poll();
			
			for(int num : graph[temp]) {
				beSolvedFirst[num]--;
			}
			
			sb.append(temp+" ");
			
			for(int next : graph[temp]) {
				if(beSolvedFirst[next] == 0)
					pq.add(next);
			}
		}
	}

}
