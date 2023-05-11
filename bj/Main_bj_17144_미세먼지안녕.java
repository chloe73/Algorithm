package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_17144_미세먼지안녕 {
	
	static int R,C,T,result;
	static int ax,bx; // 공기청정기 위치
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		board = new int[R][C];
		boolean flag = false;
		for(int i=0;i<R;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<C;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == -1 && !flag) {
					ax = i;
					flag = true;
				}
				else if(board[i][j] == -1 && flag) bx = i;
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int i=0;i<T;i++) {
			
			// 1. 미세먼지 확산 -> 미세먼지 있는 칸 동시에 일어난다.
			spread_dust();

			// 2. 공기청정기 작동
			move_air();
		}
		
		// 방에 남아있는 미세먼지 양 구하기
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(board[i][j] > 0) result += board[i][j];
			}
		}
	}

	private static void move_air() {
		
		// 공기청정기 위쪽 : 반시계 방향 이동
		// ↓
		for(int i=ax-2;i>=0;i--) {
			board[i+1][0] = board[i][0];
		}
		board[0][0] = 0;
		// ←
		for(int i=1;i<C;i++) {
			board[0][i-1] = board[0][i];
		}
		board[0][C-1] = 0;
		// ↑
		for(int i=1;i<=ax;i++) {
			board[i-1][C-1] = board[i][C-1];
		}
		board[ax][C-1] = 0;
		// →
		for(int i=C-2;i>0;i--) {
			board[ax][i+1] = board[ax][i];
		}
		board[ax][1] = 0;
		
		// 공기청정기 아래쪽 : 시계 방향 이동
		// ↑
		for(int i=bx+2;i<R;i++) {
			board[i-1][0] = board[i][0];
		}
		board[R-1][0] = 0;
		// ←
		for(int i=1;i<C;i++) {
			board[R-1][i-1] = board[R-1][i];
		}
		board[R-1][C-1] = 0;
		// ↓
		for(int i=R-2;i>=bx;i--) {
			board[i+1][C-1] = board[i][C-1];
		}
		board[bx][C-1] = 0;
		// →
		for(int i=C-2;i>0;i--) {
			board[bx][i+1] = board[bx][i];
		}
		board[bx][1] = 0;
	}

	private static void spread_dust() {
		int[][] copy = new int[R][C];
		
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(board[i][j] > 0) {
					int cnt = 0;
					int num = board[i][j] / 5;
					
					for(int d=0;d<4;d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						
						if(!isValid(nx,ny)) continue;
						
						if(board[nx][ny] == -1) continue;
						
						copy[nx][ny] += num;
						cnt++;
					}
					
					copy[i][j] -= num * cnt;
				}
			}
		}
		
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(copy[i][j] != 0) board[i][j] += copy[i][j];
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=R || c>=C) return false;
		return true;
	}
}
