package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_11403 {
	
	static int N;
	static ArrayList<Integer>[] graph;
	static int[][] board;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		graph = new ArrayList[N+1];
		for(int i=1;i<=N;i++) {
			graph[i] = new ArrayList<>();
		}
		board = new int[N+1][N+1];
		
		for(int i=1;i<=N;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=1;j<=N;j++) {
				int flag = Integer.parseInt(st.nextToken());
				board[i][j] = flag;
				if(flag == 1) {
					graph[i].add(j);
				}
			}
		} // input end
		
		StringBuilder sb = new StringBuilder();
		
		for(int k=1;k<=N;k++) {
			for(int i=1;i<=N;i++) {
				for(int j=1;j<=N;j++) {
					if(board[i][k] == 1 && board[k][j] == 1) {
						board[i][j] = 1;
					}
				}
			}
		}
		
		for(int i=1;i<=N;i++) {
			for(int j=1;j<=N;j++) {
				sb.append(board[i][j]+" ");
			}
			sb.append("\n");
		}
		
//		for(int i=1;i<=N;i++) {
//			for(int j=1;j<=N;j++) {
//				if(i != j && solve(i,j)) {
//					sb.append(1+" ");
//				}
//				else {
//					sb.append(0+" ");
//				}
//			}
//			sb.append("\n");
//		}
		
		System.out.println(sb.toString());
	}

	private static boolean solve(int i, int j) {
		Queue<Integer> q = new LinkedList<>();
		q.add(i);
		boolean[] visited = new boolean[N+1];
		visited[i] = true;
		
		while(!q.isEmpty()) {
			int temp = q.poll();
			
			if(temp == j && i != j) {
				return true;
			}
			
			for(int next : graph[temp]) {
				if(!visited[next]) {
					visited[next] = true;
					q.add(next);
				}
			}
		}
		return false;
	}

}
