package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_13549_숨바꼭질3 {
	
	static int N,K,result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		result = Integer.MAX_VALUE;
		
		bfs();
		
		System.out.println(result);
	}

	private static void bfs() {
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(new int[] {N,0});
		boolean[] visited = new boolean[100002];
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int X = temp[0];
			int time = temp[1];
			
			if(X == K) {
				result = Math.min(result, time);
			}
			
			if(X * 2 <= 100001 && !visited[X*2]) {
				q.add(new int[] {X*2,time});
				visited[X*2] = true;
			}
			
			if(X - 1 >= 0 && !visited[X-1]) {
				q.add(new int[] {X-1,time+1});
				visited[X-1] = true;
			}
			
			if(X + 1 <= 100001 && !visited[X+1]) {
				q.add(new int[] {X+1,time+1});
				visited[X+1] = true;
			}
		}
	}

}
