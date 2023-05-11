package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_23289_온풍기안녕 {
	
	static int R,C,K,W,result;
	static int[][] board;
	static boolean[][][] wall;
	//                 오, 왼, 위, 아래
	static int[] dx = {0,0,0,-1,1};
	static int[] dy = {0,1,-1,0,0};
	static ArrayList<Heater> heaterList; // 히터 리스트
	static ArrayList<Point> targetList; // 온도를 조사해야 하는 칸의 리스트
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static class Heater {
		int x,y,d;
		public Heater(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[R][C];
		wall = new boolean[R][C][5];
		heaterList = new ArrayList<>();
		targetList = new ArrayList<>();
		
		for(int i=0;i<R;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<C;j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 0) continue;
				else if(num == 5) targetList.add(new Point(i, j));
				else heaterList.add(new Heater(i, j, num));
			}
		}
		
		W = Integer.parseInt(br.readLine());
		for(int i=0;i<W;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int t = Integer.parseInt(st.nextToken());
			
			if(t == 0) { // (x,y) - (x-1,y)
				// 위 아래
				wall[x][y][3] = true;
				wall[x-1][y][4] = true;
			}
			else if(t == 1) {
				// 오른쪽 왼쪽
				wall[x][y][1] = true;
				wall[x][y+1][2] = true;
			}
		} // input end
		
		solve();
		
		System.out.println(result);

	}

	private static void solve() {
		
		while(result <= 100) {
			
			// 1. 온풍기에서 바람 한 번 나옴
			make_wind();
			
			// 2. 온도 조절됨.
			set_temperature();
			
			// 3. 온도가 1 이상인 가장 바깥쪽 칸 온도 1씩 감소
			down_temperature();
			
			// 4. 초콜릿 하나 먹음
			result++;
//			print();
//			System.out.println("===== "+ result + " =====");
			// 5. 조사하는 모든 칸의 온도가 K 이상이면 stop!
			if(check_target()) break;
			else continue;
		}
		
	}
	
	private static void print() {
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	private static boolean check_target() {
		
		boolean flag = true;
		for(Point p : targetList) {
			if(board[p.x][p.y] >= K) continue;
			else {
				flag = false;
				break;
			}
		}
		
		return flag;
	}

	private static void down_temperature() {
		// board의 가장자리 -1씩 감소
		
		for(int j=0;j<C;j++) {
			// 0번째 행 check !
			if(board[0][j] > 0) board[0][j] -= 1;
			
			// R-1행 check !
			if(board[R-1][j] > 0) board[R-1][j] -= 1;
		}
		
		for(int i=1;i<R-1;i++) {
			// 0번째 열 check !
			if(board[i][0] > 0) board[i][0] -= 1;
			
			// C-1열 check !
			if(board[i][C-1] > 0) board[i][C-1] -= 1;
		}
		
		
	}

	private static void set_temperature() {
		int[][] copy = new int[R][C];
		
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(board[i][j] > 0) {
					int temp = board[i][j];
					
					for(int k=1;k<=4;k++) {
						int nx = i + dx[k];
						int ny = j + dy[k];
						
						if(!isValid(nx,ny)) continue;
						
						if(wall[i][j][k]) continue;
						
						if(temp > board[nx][ny]) {
							int dif = temp - board[nx][ny];
							int num = dif / 4;
							copy[nx][ny] += num;
							copy[i][j] -= num;
						}
					}
				}
			}
		}
		
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(copy[i][j] == 0) continue;
				else {
					board[i][j] += copy[i][j];
				}
			}
		}
	}

	private static void make_wind() {
		
		for(Heater h : heaterList) {
			bfs(h.x,h.y,h.d);
		}
	}

	private static void bfs(int r, int c, int d) {
		Queue<int[]> q = new LinkedList<int[]>();
		boolean[][] visited = new boolean[R][C];
		
		int kx = r + dx[d];
		int ky = c + dy[d];
		visited[kx][ky] = true;
		q.add(new int[] {kx,ky});
		
		int[] dirX = new int[3];
		int[] dirY = new int[3];
		
		if(d == 1) { // 오른쪽
			dirX[0] = 0; dirY[0] = 1;
			dirX[1] = -1; dirY[1] = 1;
			dirX[2] = 1; dirY[2] = 1;
		}
		else if(d == 2) { // 왼쪽
			dirX[0] = 0; dirY[0] = -1;
			dirX[1] = -1; dirY[1] = -1;
			dirX[2] = 1; dirY[2] = -1;
		}
		else if(d == 3) { // 위
			dirX[0] = -1; dirY[0] = 0;
			dirX[1] = -1; dirY[1] = -1;
			dirX[2] = -1; dirY[2] = 1;
		}
		else if(d == 4) { // 아래
			dirX[0] = 1; dirY[0] = 0;
			dirX[1] = 1; dirY[1] = -1;
			dirX[2] = 1; dirY[2] = 1;
		}
		board[kx][ky] += 5;
		int num = 5;
		while(!q.isEmpty()) {
			
			if(num == 0) break;
			num--;
			int size = q.size();
			for(int i=0;i<size;i++) {
				int[] temp = q.poll();
				int x = temp[0];
				int y = temp[1];
				
				int nx = x;
				int ny = y;
				for(int a=0;a<3;a++) {
					
					if(a == 0) {
						nx = x + dirX[a];
						ny = y + dirY[a];
						
						if(!isValid(nx,ny)) continue;
						
						if(visited[nx][ny]) continue;
						
						// 벽이 가로막고 있다면 이동 x
						if(wall[x][y][d]) continue;
						
						visited[nx][ny] = true;
						board[nx][ny] += num;
						q.add(new int[] {nx,ny});
						continue;
					}
					
					else if(a == 1) { // 위 먼저 확인
						if(d == 1 || d == 2) {
							// dx 먼저 이동 후 이동 가능한지 확인
							nx = x + dirX[a];
							ny = y;
							
							if(!isValid(nx,ny)) continue;
							
							// 위, 위옆 벽이 있으면 pass
							if(wall[x][y][3] || wall[nx][ny][d]) continue;
							
							ny = y + dirY[a];
							
							if(!isValid(nx,ny)) continue;
							
							if(visited[nx][ny]) continue;
							
							visited[nx][ny] = true;
							board[nx][ny] += num;
							q.add(new int[] {nx,ny});
							continue;
						}
						
						else if(d == 3 || d == 4) {
							// dy 먼저 이동 후 이동 가능한지 확인
							nx = x;
							ny = y +dirY[a];
							
							if(!isValid(nx,ny)) continue;
							
							if(wall[x][y][2] || wall[nx][ny][d]) continue;
							
							nx = x + dirX[a];
							
							if(!isValid(nx,ny)) continue;
							
							if(visited[nx][ny]) continue;
							
							visited[nx][ny] = true;
							board[nx][ny] += num;
							q.add(new int[] {nx,ny});
							continue;
						}
					}
					else { // a == 2인 경우 : 아래 먼저 확인
						if(d == 1 || d == 2) {
							nx = x + dirX[a];
							ny = y;
							
							if(!isValid(nx,ny)) continue;
							
							// 위, 위옆 벽이 있으면 pass
							if(wall[x][y][4] || wall[nx][ny][d]) continue;
							
							ny = y + dirY[a];
							
							if(!isValid(nx,ny)) continue;
							
							if(visited[nx][ny]) continue;
							
							visited[nx][ny] = true;
							board[nx][ny] += num;
							q.add(new int[] {nx,ny});
							continue;
						}
						
						else if(d == 3 || d == 4) {
							nx = x;
							ny = y +dirY[a];
							
							if(!isValid(nx,ny)) continue;
							
							if(wall[x][y][1] || wall[nx][ny][d]) continue;
							
							nx = x + dirX[a];
							
							if(!isValid(nx,ny)) continue;
							
							if(visited[nx][ny]) continue;
							
							visited[nx][ny] = true;
							board[nx][ny] += num;
							q.add(new int[] {nx,ny});
							continue;
						}
					}
					
				}
			}
		}
		
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=R || c>=C) return false;
		return true;
	}

}
