package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_3055_탈출 {
	
	static int R,C,result;
	static int tx,ty; // 고슴도치 위치
	static int ex,ey; // 비버의 굴 위치
	static char[][] board;
	static Queue<int[]> wq, hq; // 물 위치 저장
	static boolean[][] wvisited, hvisited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board =new char[R][C];
		wq = new LinkedList<>();
		wvisited = new boolean[R][C];
		hvisited = new boolean[R][C];
		hq = new LinkedList<>();
		for(int i=0;i<R;i++) {
			String s = br.readLine();
			for(int j=0;j<C;j++) {
				board[i][j] = s.charAt(j);
				if(board[i][j] == 'S') {
					tx = i;
					ty = j;
					hq.add(new int[] {i,j,0});
					hvisited[i][j] = true;
					board[i][j] = '.';
				}
				if(board[i][j] == 'D') {
					ex = i;
					ey = j;
					board[i][j] = '.';
				}
				if(board[i][j] == '*') {
					wq.add(new int[] {i,j});
					wvisited[i][j] = true;
				}
			}
		} // input end
		
		solve();
		
		System.out.println(result == -1 ? "KAKTUS" : result);
	}

	private static void solve() {
		
		while(true) {
			
			if(tx == ex && ty == ey) break;
			
			// 물이 먼저 이동
			move_water();
			
			// 고슴도치 이동
			if(!move_h()) {
				result = -1;
				break;
			}
		}
		
	}
	
	private static boolean move_h() {
		
		int size = hq.size();
		boolean flag = false;
		
		for(int i=0;i<size;i++) {
			int[] temp = hq.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			if(x == ex && y == ey) {
				result = cnt;
				tx = ex;
				ty = ey;
				return true;
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || hvisited[nx][ny]) continue;
				
				if(board[nx][ny] == '.' && !wvisited[nx][ny]) {
					hvisited[nx][ny] = true;
					hq.add(new int[] {nx,ny,cnt+1});
					flag = true;
				}
			}
		}
		
		return flag;
	}

	private static void move_water() {
		int size = wq.size();
		
		for(int i=0;i<size;i++) {
			int[] temp = wq.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || wvisited[nx][ny]) continue;
				
				if(nx == ex && ny == ey) continue;
				
				if(board[nx][ny] == '.') {
					wvisited[nx][ny] = true;
					wq.add(new int[] {nx,ny});
					continue;
				}
			}
		}
		
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=R || c>=C) return false;
		return true;
	}

}