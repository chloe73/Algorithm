package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_15683_감시 {
	
	static int N,M,result;
	static int[][] board;
	static int[][][] move = {
			{{0}},
			{{0},{1},{2},{3}},
			{{0,1},{2,3}},
			{{0,3},{1,3},{1,2},{2,0}},
			{{0,2,3},{1,2,3},{0,1,3},{0,1,2}},
			{{0,1,2,3}},
	};
	static ArrayList<Point> cctvList;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int type,x,y;
		public Point(int type, int x, int y) {
			this.type = type;
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		cctvList = new ArrayList<>();
		int cnt = N * M; // 처음 0 개수
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] >= 1 && board[i][j] <= 5) {
					cctvList.add(new Point(board[i][j],i,j));
					cnt--;
				}
				if(board[i][j] == 6) cnt--;
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		
		solve(0,cnt,board);
		
		System.out.println(result);
	}

	private static void solve(int idx, int cnt, int[][] arr) {
		if(idx == cctvList.size()) {
			result = Math.min(result, cnt);
			return;
		}
		
//		if(cnt > result) return;
		
		Point temp = cctvList.get(idx);
		int x = temp.x;
		int y = temp.y;
		
		int[][] arr_copy = new int[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				arr_copy[i][j] = arr[i][j];
			}
		}
		
		for(int i=0;i<move[temp.type].length;i++) {
			int count = cnt;
			for(int j=0;j<move[temp.type][i].length;j++) {
				int d = move[temp.type][i][j];
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				while(isValid(nx,ny)) {
					if(arr_copy[nx][ny] == 0) {
						count--;
						arr_copy[nx][ny] = -1;
					}
					if(arr_copy[nx][ny] == 6) break;
					
					nx += dx[d];
					ny += dy[d];
				}
				
			}
			
			solve(idx+1, count, arr_copy);
			// 재귀에서 돌아온 후, 이전 배열 값으로 초기화
			for(int k=0;k<N;k++) {
				for(int j=0;j<M;j++) {
					arr_copy[k][j] = arr[k][j];
				}
			}
		}
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
