package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_16946_벽_부수고_이동하기_4 {
	
	static int N,M;
	static int[][] board, groupNum;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static HashMap<Integer, Integer> groupMap;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j) - '0';
			}
		} // input end
		
		// 1. 그룹 나누기
		visited = new boolean[N][M];
		groupMap = new HashMap<>();
		groupNum = new int[N][M];
		int index = 1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(!visited[i][j] && board[i][j] == 0) {
					groupMap.put(index, bfs(i, j, index));
					index++;
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == 1) {
					sb.append(make_sum(i,j));
				}
				else sb.append(0);
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	private static int make_sum(int x, int y) {
		int cnt = 1;
		HashSet<Integer> set = new HashSet<>();
		
		for(int d=0;d<4;d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(!is_valid(nx, ny)) continue;
			
			if(board[nx][ny] == 0) {
				set.add(groupNum[nx][ny]);
			}
		}
		
		for(int num : set) {
			cnt += groupMap.get(num);
		}
		
		return cnt % 10;
	}

	private static int bfs(int i, int j, int num) {
		int count = 1;
		
		Queue<int[]> q = new LinkedList<>();
		visited[i][j] = true;
		q.add(new int[] {i,j});
		groupNum[i][j] = num;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == 0) {
					visited[nx][ny] = true;
					groupNum[nx][ny] = num;
					count++;
					q.add(new int[] {nx,ny});
				}
			}
		}
		
		return count;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}