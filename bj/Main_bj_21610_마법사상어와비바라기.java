package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_21610_마법사상어와비바라기 {
	
	static int N,M,result;
	static int[][] board;
	static ArrayList<int[]> cmd;
	static ArrayList<Point> rainCloud;
	static int[] dx = {0,-1,-1,-1,0,1,1,1};
	static int[] dy = {-1,-1,0,1,1,1,0,-1};
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cmd = new ArrayList<int[]>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			
			cmd.add(new int[] {d,s});
		} // input end
		
		rainCloud = new ArrayList<>();
		// 비바라기 마법은 시전해 (N-1,0), (N-1,1), (N-2,0), (N-2,1)에 비구름 생긴다.
		rainCloud.add(new Point(N-1,0));
		rainCloud.add(new Point(N-1,1));
		rainCloud.add(new Point(N-2,0));
		rainCloud.add(new Point(N-2,1));
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int[] temp : cmd) {
			int dir = temp[0];
			int s = temp[1];
			
			// 1. 모든 구름 d방향으로 s칸만큼 이동
			move_cloud(dir, s);
			
			// 2. 각 구름에 비가 내려 구름이 있는 칸의 물의 양 +1 증가
			for(Point p : rainCloud) {
				board[p.x][p.y] += 1;
			}
			
			// 3. 구름이 사라진다. -> 맨 마지막에 rainCloud 비우기
			
			
			// 4. 2에서 물이 증가한 칸에 물복사버그 마법 시전
			waterCopyBugMagic();
			
			// 5. 바구니의 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양 -2
			// 이때, 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
			make_cloud();
		}
		
		// 모든 명령 수행 후, 남아있는 물의 양 총합 구하기
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] > 0) result += board[i][j];
			}
		}
		
	}

	private static void make_cloud() {
		boolean[][] check = new boolean[N][N];
		for(Point p : rainCloud) {
			check[p.x][p.y] = true;
		}
		
		rainCloud.clear();
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(check[i][j]) continue;
				
				if(board[i][j] >= 2) {
					rainCloud.add(new Point(i, j));
					board[i][j] -= 2;
				}
			}
		}
		
	}

	private static void waterCopyBugMagic() {
		
		// 물복사버그 마법을 사용하면, 대각선 방향으로 거리가 1인 칸에
		// 물이 있는 바구니의 수만큼 (r, c)에 있는 바구니의 물이 양이 증가
		for(Point p : rainCloud) {
			int x = p.x;
			int y = p.y;
			int cnt = 0;
			
			for(int i=1;i<8;i+=2) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				// 범위를 벗어나면 pass
				if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
				
				if(board[nx][ny] > 0) cnt++;
			}
			
			board[x][y] += cnt;
		}
		
	}

	private static void move_cloud(int dir, int s) {
		
		for(Point p : rainCloud) {
			int x = p.x;
			int y = p.y;
			
			// 구름 d 방향으로 s칸만큼 이동
			int nx = (N + x + dx[dir] * (s % N)) % N;
			int ny = (N + y + dy[dir] * (s % N)) % N;
			
//			if(nx < 0) {
////				nx = N + nx;
//				
//				if(Math.abs(nx) >= N) {
//					nx = Math.abs(nx) % N + 1;
//				}
//				else {
//					nx = N % Math.abs(nx) + 1;
//				}
//			}
//			
//			else if(nx >= N) {
////				nx = nx - N;
//				nx = nx % N;
//			}
//			
//			if(ny < 0) {
////				ny = N + ny;
//				
//				if(Math.abs(ny) >= N) {
//					ny = Math.abs(ny) % N + 1;
//				}
//				else {
//					ny = N % Math.abs(ny) + 1;
//				}
//			}
//			
//			else if(ny >= N) {
////				ny = ny - N;
//				ny = ny % N;
//			}
			
			p.x = nx;
			p.y = ny;
		}
		
	}

}
