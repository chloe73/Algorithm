package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_메이즈_러너 {
	
	static int N,M,K,totalDistance;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static Point exit, square;
	static ArrayList<Point> participantList;
	static class Point {
		int x,y,d;
		public Point(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
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
			participantList.add(new Point(x,y,0));
		}
		
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken())-1;
		int y = Integer.parseInt(st.nextToken())-1;
		
		exit = new Point(x, y, 0);
		square = new Point(0,0,0); // 가장 작은 정사각형 좌상단 좌표
		
		solve();
		exit.x++; exit.y++;
		
		// 게임이 끝났을 때, 모든 참가자들의 이동 거리 합과 출구 좌표를 출력
		System.out.println(totalDistance);
		System.out.println(exit.x+" "+exit.y);
	}

	private static void solve() {
		
		// K초 동안 위의 과정을 계속 반복됩니다.
		while(K-- > 0) {
			
			// 만약 K초 전에 모든 참가자가 탈출에 성공한다면, 게임이 끝납니다.
			if(participantList.size() == 0) break;
			
			// 참가자 이동
			move_participants();
			
			// 미로 회전
			rotate_maze();
		}
	}

	private static void rotate_maze() {
		// 한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형을 잡습니다.
		find_min_square();
		// 선택된 정사각형은 시계방향으로 90도 회전하며, 회전된 벽은 내구도가 1씩 깎입니다.
		rotate_square();
		// 선택된 정사각형 안에 포함된 참가자와 출구를 시계방향으로 90도 회전
		rotate_participant_and_exit();
	}

	private static void rotate_participant_and_exit() {
		
		// 참가자 체크
		for(int i=0;i<participantList.size();i++) {
			Point p = participantList.get(i);
			
			// 해당 참가자가 선택된 정사각형 안에 포함될 때만 회전시킨다.
			if(square.x <= p.x && p.x<square.x+square.d && square.y <= p.y && p.y<square.y+square.d) {
				int ox = p.x-square.x; int oy = p.y-square.y;
				
				int rx = oy; int ry = square.d-ox-1;
				
				p.x = rx+square.x;
				p.y = ry+square.y;
			}
		}
		
		// exit 체크
		if(square.x <= exit.x && exit.x<square.x+square.d && square.y <= exit.y && exit.y<square.y+square.d) {
			int ox = exit.x-square.x; int oy = exit.y-square.y;
			
			int rx = oy; int ry = square.d-ox-1;
			
			exit.x = rx+square.x;
			exit.y = ry+square.y;
		}
	}

	private static void rotate_square() {
		// 내구도 1씩 감소
		for(int x=square.x;x<square.x+square.d;x++) {
			for(int y=square.y;y<square.y+square.d;y++) {
				if(board[x][y] > 0) board[x][y]--;
			}
		}
		
		int[][] nextBoard = new int[N][N];
		// 정사각형 시계방향 90도 회전
		for(int x=square.x;x<square.x+square.d;x++) {
			for(int y=square.y;y<square.y+square.d;y++) {
				// 시작점을 (0,0)으로 변환해줌. 기준점을 (0,0)으로 바꾸는 것
				int ox = x-square.x; int oy = y-square.y;
				// 변환된 상태에서 회전 (x,y) -> (y, square.d-x-1)
				int rx = oy; int ry = square.d-ox-1;
				// 다시 square.x, square.y를 더해준다.
				nextBoard[rx+square.x][ry+square.y] = board[x][y];
			}
		}
		
		// board에 nextBoard값으로 갱신해준다.
		for(int x=square.x;x<square.x+square.d;x++) {
			for(int y=square.y;y<square.y+square.d;y++) {
				board[x][y] = nextBoard[x][y];
			}
		}
	}

	private static void find_min_square() {
		// 가장 작은 크기를 갖는 정사각형이 2개 이상이라면, 좌상단 r 좌표가 작은 것이 우선되고, 그래도 같으면 c 좌표가 작은 것이 우선됩니다.
		// 가장 작은 정사각형 크기부터 시작
		for(int sz=2;sz<=N;sz++) {
			// 가장 좌상단 r좌표가 작은 것부터 확인
			for(int x1=0;x1<=N-sz-1;x1++) {
				// 가장 좌상단 c좌표가 작은 것부터 확인
				for(int y1=0;y1<=N-sz-1;y1++) {
					int x2 = x1+sz-1;
					int y2 = y1+sz-1;
					
					// 출구가 포함되지 않으면 pass
					if(!(x1<=exit.x && exit.x <= x2 && y1<=exit.y && exit.y <= y2)) 
						continue;
					
					// 한 명 이상의 참가자가 정사각형 안에 포함되는지 확인
					boolean flag = false;
					for(int k=0;k<participantList.size();k++) {
						Point p = participantList.get(k);
						if(x1 <= p.x && p.x <= x2 && y1 <= p.y && p.y <= y2) {
							flag = true;
							break;
						}
					}
					
					if(flag) {
						square.x = x1;
						square.y = y1;
						square.d = sz;
						
						return;
					}
				}
			}
		}
		
	}

	private static void move_participants() {
		// 1초마다 모든 참가자는 한 칸씩 움직입니다. 움직이는 조건은 다음과 같습니다.
		// 두 위치 (x1,y1), (x2,y2)의 최단거리는 ∣x1−x2∣+∣y1−y2∣로 정의됩니다.
		// 모든 참가자는 동시에 움직입니다.
		// 상하좌우로 움직일 수 있으며, 벽이 없는 곳으로 이동할 수 있습니다.
		// 움직인 칸은 현재 머물러 있던 칸보다 출구까지의 최단 거리가 가까워야 합니다.
		// 움직일 수 있는 칸이 2개 이상이라면, 상하로 움직이는 것을 우선시합니다.
		// 참가가가 움직일 수 없는 상황이라면, 움직이지 않습니다.
		// 한 칸에 2명 이상의 모험가가 있을 수 있습니다.
		
		ArrayList<Point> newParticipantList = new ArrayList<>();
		
		for(int i=0;i<participantList.size();i++) {
			Point temp = participantList.get(i);
			
//			if(temp.x == exit.x && temp.y == exit.y) continue;
			
			// 출구의 행과 현재 행이 다른 경우 행을 이동시킨다.
			if(temp.x != exit.x) {
				int nx = temp.x;
				int ny = temp.y;
				
				if(exit.x > nx) nx++;
				else nx--;
				
				// 빈칸이라면 이동시킬 수 있다.
				if(board[nx][ny] == 0) {
					temp.x = nx;
					temp.y = ny;
					totalDistance++;
					continue;
				}

			}
			
			// 열이 다른 경우, 열을 이동시킨다.
			if(temp.y != exit.y) {
				int nx = temp.x;
				int ny = temp.y;
				
				if(exit.y > ny) ny++;
				else ny--;
				
				// 빈칸이라면 이동시킬 수 있다.
				if(board[nx][ny] == 0) {
					temp.x = nx;
					temp.y = ny;
					totalDistance++;
					continue;
				}

			}
		}
		
		for(int i=0;i<participantList.size();i++) {
			Point p = participantList.get(i);
			if(p.x == exit.x && p.y == exit.y) continue;
			newParticipantList.add(p);
		}
		
		participantList.clear();
		participantList = newParticipantList;
	}

}
