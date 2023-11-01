package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2638_치즈 {
	
	static int N,M,result;
	static int cheeseCnt;
	static ArrayList<int[]> cheeseList;
	static int[][] board;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		cheeseCnt = 0;
		cheeseList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 1) {
					cheeseCnt++;
					cheeseList.add(new int[] {i,j});
				}
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(cheeseCnt > 0) {
			visited = new boolean[N][M];
			
			// 가장자리 치즈 표시 (1시간 뒤에 사라짐)
			check_cheese();
			
			// 1시간 지난 가장자리 치즈 사라짐.
			remove_cheese();
			
			result++;
		}
		
	}
	
	private static void check_cheese() {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {0,0});
		visited[0][0] = true;
		board[0][0] = 2; // 외부 공기 표시
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == 1) {
					continue;
				}
				
				board[nx][ny] = 2;
				q.add(new int[] {nx,ny});
				visited[nx][ny] = true;
			}
		}
		
	}

	private static void remove_cheese() {
		
		for(int i=0;i<cheeseList.size();i++) {
			int[] temp = cheeseList.get(i);
			int x = temp[0];
			int y = temp[1];
			int cnt = 0;
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny)) continue;
				
				if(board[nx][ny] == 2) {
					cnt++;
				}
			}
			
			if(cnt >= 2) {
				board[x][y] = 0;
				cheeseCnt--;
				cheeseList.remove(i);
				i--;
			}
		}
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
