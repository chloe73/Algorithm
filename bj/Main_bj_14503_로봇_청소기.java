package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_14503_로봇_청소기 {
	
	static int N,M,result;
	static int[][] board;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static Robot robot;
	static class Robot {
		int x,y,d;
		public Robot(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		robot = new Robot(x, y, d);
		
		board = new int[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		outer:while(check_board_wall(robot.x, robot.y)) {
			
			// 1. 현재 칸이 아직 청소되지 않은 경우, 현재 칸을 청소한다.
			if(board[robot.x][robot.y] == 0) {
				result++;
				board[robot.x][robot.y] = 2; // 청소한 칸 표시
			}
			
			// 2. 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 없는 경우,
			if(!check_4_dir(robot.x, robot.y)) {
				int backMoveDir = get_opposite_dir(robot.d);
				int nx = robot.x + dx[backMoveDir];
				int ny = robot.y + dy[backMoveDir];
				
				// 2-1. 바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 한 칸 후진하고 1번으로 돌아간다.
				if(board[nx][ny] != 1) {
					robot.x = nx;
					robot.y = ny;
					continue;
				}
				// 2-2. 바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
				else break outer;
			}
			// 3. 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있는 경우,
			else {
				// 3-1. 반시계 방향으로 90도 회전한다.
				robot.d = change_dir(robot.d);
				int nx = robot.x + dx[robot.d];
				int ny = robot.y + dy[robot.d];
				// 3-2. 바라보는 방향을 기준으로 앞쪽 칸이 청소되지 않은 빈 칸인 경우 한 칸 전진한다.
				if(board[nx][ny] == 0) {
					robot.x = nx;
					robot.y = ny;
					// 3-3. 1번으로 돌아간다.
					continue;
				}
				
			}
		}
	}
	
	private static int change_dir(int d) {
		if(d == 0) return 3;
		return d-1;
	}
	
	// 현재 로봇청소기가 있는 칸을 기준으로 동,서,남,북 중에서 청소해야 할 칸이 있는지 확인하는 함수
	private static boolean check_4_dir(int r, int c) {
		for(int i=0;i<4;i++) {
			int nx = r + dx[i];
			int ny = c + dy[i];
			
			if(!is_valid(nx,ny)) continue;
			
			if(board[nx][ny] == 0) return true; // 청소할 칸이 있는 경우 true
		}
		return false; // 청소할 칸이 없으면 false
	}
	
	// 후진할 방향 return
	private static int get_opposite_dir(int d) {
		if(d == 0) return 2;
		else if(d == 1) return 3;
		else if(d == 2) return 0;
		else return 1;
	}
	
	// 후진해서 이동한 칸이 벽이라면 로봇청소기 작동 멈춘다
	private static boolean check_board_wall(int r, int c) {
		if(board[r][c] == 1) return false;
		return true;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
