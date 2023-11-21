package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_13460_구슬_탈출_2 {
	
	static int N,M,result;
	static int tx,ty; // 구멍 위치
	static int rx,ry,bx,by; // 빨간 구슬 위치, 파란 구슬 위치
	static char[][] board;
	// 				   up,down,left,right
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new char[N][M];
		
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j);
				if(board[i][j] == 'O') {
					tx = i;
					ty = j;
				}
				if(board[i][j] == 'R') {
					rx = i;
					ry = j;
					board[i][j] = '.';
				}
				if(board[i][j] == 'B') {
					bx = i;
					by = j;
					board[i][j] = '.';
				}
			}
		} // input end
		
		// 보드의 상태가 주어졌을 때, 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.
		solve();

		System.out.println(result);
	}

	private static void solve() {
		// 만약, 10번 이하로 움직여서 빨간 구슬을 구멍을 통해 빼낼 수 없으면 -1을 출력한다.
		
		result = Integer.MAX_VALUE; // 최소 시간을 구해야 함.

		// 게임의 목표는 빨간 구슬을 구멍을 통해서 빼내는 것이다. 이때, 파란 구슬이 구멍에 들어가면 안 된다.
		bfs();
		
		if(result == Integer.MAX_VALUE)
			result = -1;
		
		// 해당 조건 추가하니 64퍼에서 맞았습니다! 로 해결
		if(result > 10) result = -1;
		
	}
	
	private static void bfs() {
		Queue<int[]> q = new LinkedList<>();
		boolean[][][][] visited = new boolean[N][M][N][M];
		visited[rx][ry][bx][by] = true;
		q.add(new int[] {rx,ry,bx,by,0});
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int red_x = temp[0];
			int red_y = temp[1];
			int blue_x = temp[2];
			int blue_y = temp[3];
			int cnt = temp[4];
			
			if(cnt > 10) {
				result = -1;
				return;
			}
			
			if(red_x == tx && red_y == ty && blue_x != tx && blue_y != ty) {
				result = Math.min(result, cnt);
				continue;
			}
			
			// 빨간 구슬을 기준으로 상하좌우 중 움직일 수 있는 방향으로 기울이기
			for(int d=0;d<4;d++) {
				int nx = red_x;
				int ny = red_y;
				int nx2 = blue_x;
				int ny2 = blue_y;
				
				boolean red_flag = false;
				boolean blue_flag = false;

				// 빨간색 구슬 이동
				while(is_valid(nx+dx[d], ny+dy[d]) && board[nx+dx[d]][ny+dy[d]] != '#') {
					nx += dx[d];
					ny += dy[d];
					if(board[nx][ny] == 'O') {
						red_flag = true;
						break;
					}
				}
				
				// 파란색 구슬 이동
				while(is_valid(nx2+dx[d], ny2+dy[d]) && board[nx2+dx[d]][ny2+dy[d]] != '#') {
					nx2 += dx[d];
					ny2 += dy[d];
					if(board[nx2][ny2] == 'O') {
						blue_flag = true;
						break;
					}
				}
				
				// 파란 구슬이 구멍에 들어간 경우
				if(blue_flag) continue;
				
				// 빨간 구슬이 구멍에 들어간 경우
				if(red_flag) {
					result = Math.min(result, cnt+1);
//					continue; // 이 부분을 return으로 바꿔야
					return;
				}
				
				// 빨간 구슬과 파란 구슬이 같은 위치에 있으면 위치 조정 필요
				if(nx == nx2 && ny == ny2) {
					int red = Math.abs(red_x - nx) + Math.abs(red_y - ny);
					int blue = Math.abs(blue_x - nx2) + Math.abs(blue_y - ny2);
					
					if(red > blue) {
						// 파란 구슬이 먼저 해당 위치에 도달
						nx -= dx[d];
						ny -= dy[d];
					}
					else {
						nx2 -= dx[d];
						ny2 -= dy[d];
					}
				}
				
				if(!visited[nx][ny][nx2][ny2]) {
					visited[nx][ny][nx2][ny2] = true;
					q.add(new int[] {nx,ny,nx2,ny2,cnt+1});
				}
			}
		}
			
	}

	private static boolean is_valid(int r, int c) {
		// 범위 밖을 벗어나거나
		if(r<0 || c<0 || r>=N || c>=M) return false;
		// 장애물에 마주치거나
//		if(board[r][c] == '#') return false;
		return true;
	}

}