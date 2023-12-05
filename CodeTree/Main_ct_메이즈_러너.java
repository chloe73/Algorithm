package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_메이즈_러너 {
	
	static int N,M,K;
	static int ex,ey; // 출구 좌표 => 계속해서 바뀌는 값
	static int totalDistance; // 총 이동거리
	static int sx,sy,squareSize; // 찾은 정사각형의 좌표와 크기
	static int[][] board;
	static ArrayList<Point> participantList;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		participantList = new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			participantList.add(new Point(x, y));
		}
		
		st = new StringTokenizer(br.readLine());
		ex = Integer.parseInt(st.nextToken())-1;
		ey = Integer.parseInt(st.nextToken())-1;
		// input end
		
		solve();
		
		System.out.println(totalDistance);
		System.out.println(ex+" "+ey);
	}

	private static void solve() {
		while(K-- > 0) {
			
			// 1초마다 모든 참가자는 한 칸씩 움직입니다. 움직이는 조건은 다음과 같습니다.
			// 두 위치 (x1,y1), (x2,y2)의 최단거리는 ∣x1−x2∣+∣y1−y2∣로 정의됩니다.
			// 상하좌우로 움직일 수 있으며, 벽이 없는 곳으로 이동할 수 있습니다.
			// 움직인 칸은 현재 머물러 있던 칸보다 출구까지의 최단 거리가 가까워야 합니다.
			// 움직일 수 있는 칸이 2개 이상이라면, 상하로 움직이는 것을 우선시합니다.
			// 참가가가 움직일 수 없는 상황이라면, 움직이지 않습니다.
			// 한 칸에 2명 이상의 참가자가 있을 수 있습니다.
			move_participants();
			
			// 모든 참가자가 미로를 탈출했으면 stop
			if(is_escaped()) break;

			// 미로 회전
			// 한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형을 찾는다.
			find_square();
			
			// 찾은 정사각형 회전
			rotate_square();
			
			// 모든 참가자들 회전 및 출구 회전
			rotate_participants_and_exit();
			
			System.out.println("***** : "+ex+", "+ey+", "+totalDistance);
		}
	}
	
	private static void rotate_participants_and_exit() {
		
		for(Point p : participantList) {
			// 해당 참가자가 정사각형 안에 포함되어 있다면
			if(sx <= p.x && p.x < sx+squareSize && sy<= p.y && p.y < sy+squareSize) {
				int ox = p.x-sx;
				int oy = p.y-sy;
				
				int rx = oy;
				int ry = squareSize - ox - 1;
				
				p.x = rx + sx;
				p.y = ry + sy;
			}
		}
		
		// 출구도 회전
		if(sx <= ex && ex < sx+squareSize && sy<= ey && ey < sy+squareSize) {
			int ox = ex-sx;
			int oy = ex-sy;
			
			int rx = oy;
			int ry = squareSize - ox - 1;
			
			ex = rx + sx;
			ey = ry + sy;
		}

	}

	private static void rotate_square() {
		// 선택된 정사각형의 벽은 내구도가 1씩 깎입니다.
		for(int x=sx;x<sx+squareSize;x++) {
			for(int y=sy;y<sy+squareSize;y++) {
				if(board[x][y] > 0) board[x][y]--;
			}
		}
		
		// 정사각형은 시계방향으로 90도 회전
		int[][] nextBoard = new int[N][N];
		for(int x=sx;x<sx+squareSize;x++) {
			for(int y=sy;y<sy+squareSize;y++) {
				// 현재 (x,y)좌표를 (0,0)으로 세팅하고 회전시키기
				int ox = x-sx;
				int oy = y-sy;
				
				int rx = oy;
				int ry = squareSize - ox -1;
				
				nextBoard[rx+sx][ry+sy] = board[x][y];
			}
		}
		
		// nextBoard에 있는 값 기존 board에 복사
		for(int x=sx;x<sx+squareSize;x++) {
			for(int y=sy;y<sy+squareSize;y++) {
				board[x][y] = nextBoard[x][y];
			}
		}
	}

	private static void find_square() {
		// 가장 작은 크기를 갖는 정사각형이 2개 이상이라면, 
		// 좌상단 r 좌표가 작은 것이 우선되고, 그래도 같으면 c 좌표가 작은 것이 우선됩니다.
		for(int size=2;size<=N;size++) {
			for(int x1=0;x1<=N-size;x1++) {
				for(int y1=0;y1<=N-size;y1++) {
					int x2 = x1 + size - 1;
					int y2 = y1 + size - 1;
					
					// 만약 해당 정사각형 안에 출구가 없으면 pass
					if(!(x1<=ex && ex <=x2 && y1<=ey && ey<=y2)) continue;
					
					// 해당 정사각형 안에 참가자가 있는지 확인
					boolean flag = false;
					for(Point p : participantList) {
						if(x1<=p.x && p.x <=x2 && y1<=p.y && p.y<=y2) {
							// 출구에 있는 경우는 pass
//							if(p.x != ex && p.y != ey) {
//								flag = true;
//							}
							if(!(p.x == ex && p.y == ey)) {
								flag = true;
							}
						}
					}
					
					if(flag) {
						sx = x1;
						sy = y1;
						squareSize = size;
						
						return;
					}
				}
			}
		}
	}

	private static void move_participants() {
		// 모든 참가자는 동시에 움직입니다.
		for(Point temp : participantList) {
			// 이미 출구에 있는 경우 패스
			if(temp.x == ex && temp.y == ey) continue;
			
			// 행이 다른 경우 행 이동
			if(temp.x != ex) {
				int nx = temp.x;
				int ny = temp.y;
				
				if(ex > nx) nx++;
				else nx--;
				
				// 벽이 있는지 확인
				if(board[nx][ny] == 0) {
					temp.x = nx;
					temp.y = ny;
					totalDistance++;
					continue;
				}
			}
			
			// 열이 다른 경우 이동
			if(temp.y != ey) {
				int nx = temp.x;
				int ny = temp.y;
				
				if(ey > ny) ny++;
				else ny--;
				
				if(board[nx][ny] == 0) {
					temp.x = nx;
					temp.y = ny;
					totalDistance++;
					continue;
				}
			}
		}
	}

	private static boolean is_escaped() {
		
		for(Point p : participantList) {
			if(p.x == ex && p.y == ey) continue;
			else return false;
		}
		
		return true;
	}
}
