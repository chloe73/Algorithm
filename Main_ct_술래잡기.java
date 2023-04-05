package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_술래잡기 {
	
	static int N,M,H,K,score;
	static boolean moveFlag = true; // 달팽이 수열 방향
	static int moveIdx; // 술래 현재 위치
	static ArrayList<Integer>[][] board; // 도망자 위치 정보 간략히 표시
	static boolean[][] tree;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static int[] dx2 = {1,0,-1,0};
	static int[] dy2 = {0,1,0,-1};
	static HashMap<Integer, Runner> runnerMap;
	static ArrayList<Point> boardList, backBoardList;
	static class Point {
		int x,y,d;
		public Point(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	static class Runner {
		int x,y,d;
		public Runner(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new ArrayList[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				board[i][j] = new ArrayList<>();
			}
		}
		tree = new boolean[N][N];
		runnerMap = new HashMap<>();
		boardList = new ArrayList<>();
		backBoardList = new ArrayList<>();
		
		for(int i=1;i<=M;i++) { // 도망자 정보 input
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			
			board[x][y].add(i); // 도망자 정보 board에 간략히 표시
			if(d == 1) { // 이동 방법 d가 1인 경우 좌우로 움직임, 항상 오른쪽을 보고 시작
				runnerMap.put(i, new Runner(x, y, 1));
			}
			else { // 2인 경우 상하로만 움직임, 항상 아래쪽을 보고 시작함
				runnerMap.put(i, new Runner(x, y, 2));
			}
		}
		
		for(int i=0;i<H;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			tree[x][y] = true;
		} // input end
		
		solve();
		
		System.out.println(score);
	}

	private static void solve() {
		
		board_init(); // 달팽이 수열 세팅
		
		for(int t=1;t<=K;t++) {
			// 1. 도망자 이동
			move_runner();
			
			// 2. 술래 이동
			int num = catch_runner();
			score += t * num;
		}
		
	}

	private static int catch_runner() {
		
		// 술래 1칸 이동
		if(moveFlag) {
			if(moveIdx+1 == N*N-1) {
				moveFlag = false;
				moveIdx = 0;
			}
			else moveIdx++;
		}
		else {
			if(moveIdx+1 == N*N-1) {
				moveFlag = true;
				moveIdx = 0;
			}
			else moveIdx++;
		}
		
		// 술래의 시야는 현재 바라보고 있는 방향을 기준으로 현재 칸을 포함하여 총 3칸 
		// 격자 크기에 상관없이 술래의 시야는 항상 3칸임에 유의
		// 만약 나무가 놓여 있는 칸이라면, 해당 칸에 있는 도망자는 나무에 가려져 보이지 않게 됨.
		
		// 현재 술래의 위치 좌표
		int sx = -1;
		int sy = -1;
		int sd = -1;
		
		int cnt = 0;
		if(moveFlag) {
			sx = boardList.get(moveIdx).x;
			sy = boardList.get(moveIdx).y;
			sd = boardList.get(moveIdx).d;
			
			if(board[sx][sy].size() > 0 && !tree[sx][sy]) {
				cnt+=board[sx][sy].size();
				for(int num : board[sx][sy]) {
					runnerMap.remove(num);					
				}
				board[sx][sy].clear();
			}
			
			for(int i=0;i<2;i++) {
				sx += dx[sd];
				sy += dy[sd];
				
				if(!isValid(sx,sy)) break;
				
				if(board[sx][sy].size() > 0 && !tree[sx][sy]) {
					cnt+=board[sx][sy].size();
					for(int num : board[sx][sy]) {
						runnerMap.remove(num);					
					}
					board[sx][sy].clear();
				}
			}
			
		}
		else {
			sx = backBoardList.get(moveIdx).x;
			sy = backBoardList.get(moveIdx).y;
			sd = backBoardList.get(moveIdx).d;
			
			if(board[sx][sy].size() > 0 && !tree[sx][sy]) {
				cnt+=board[sx][sy].size();
				for(int num : board[sx][sy]) {
					runnerMap.remove(num);					
				}
				board[sx][sy].clear();
			}
			
			for(int i=0;i<2;i++) {
				sx += dx2[sd];
				sy += dy2[sd];
				
				if(!isValid(sx,sy)) break;
				
				if(board[sx][sy].size() > 0 && !tree[sx][sy]) {
					cnt+=board[sx][sy].size();
					for(int num : board[sx][sy]) {
						runnerMap.remove(num);					
					}
					board[sx][sy].clear();
				}
			}
		}
		return cnt;
	}

	private static void move_runner() {
		
		// 도망자가 움직이는 경우 : 술래와의 거리가 3 이하인 경우
		Queue<Integer> moveList = new LinkedList<>();
		
		// 현재 술래의 위치
		int sx = -1;
		int sy = -1;
		if(moveFlag) {
			sx = boardList.get(moveIdx).x;
			sy = boardList.get(moveIdx).y;
		}
		else {
			sx = backBoardList.get(moveIdx).x;
			sy = backBoardList.get(moveIdx).y;
		}
		
		// 거리가 3 이하인 도망자들 확인
		for(int temp : runnerMap.keySet()) {
			int x = runnerMap.get(temp).x;
			int y = runnerMap.get(temp).y;
			
			int dist = Math.abs(sx-x)+Math.abs(sy-y);
			
			// 거리 3 이하인 도망자들 이동 시작
			if(dist <= 3) {
				int d = runnerMap.get(temp).d;
				
				// 현재 바라보고 있는 방향으로 1칸 움직인다 했을 때 격자를 벗어나지 않는 경우
				if(isValid(x+dx[d],y+dy[d])) {
					// 움직이려는 칸에 술래가 있는 경우라면 움직이지 않습니다.
					if(x+dx[d] == sx && y+dy[d] == sy) continue;
					
					else {
						removeList(temp, x, y);
						x += dx[d];
						y += dy[d];
						board[x][y].add(temp);
						// 움직이려는 칸에 술래가 있지 않다면 해당 칸으로 이동합니다. 해당 칸에 나무가 있어도 괜찮습니다.
						runnerMap.get(temp).x = x;
						runnerMap.get(temp).y = y;
						continue;
					}
				}
				
				// 현재 바라보고 있는 방향으로 1칸 움직인다 했을 때 격자를 벗어나는 경우
				else {
					// 먼저 방향을 반대로 틀어줍니다. 이후 바라보고 있는 방향으로 1칸 움직인다 헀을 때 
					if(d == 0) d = 2;
					else if(d == 1) d = 3;
					else if(d == 2) d = 0;
					else if(d == 3) d = 1;
					runnerMap.get(temp).d = d;
					
					// 해당 위치에 술래가 없다면 1칸 앞으로 이동합니다.
					if(x+dx[d] == sx && y+dy[d] == sy) continue;
					else {
						removeList(temp, x, y);
						x += dx[d];
						y += dy[d];
						board[x][y].add(temp);
						runnerMap.get(temp).x = x;
						runnerMap.get(temp).y = y;
						continue;
					}
				}
			}
			else continue;
		}
		
	}
	
	private static void removeList(int val, int x, int y) {
		int idx = board[x][y].indexOf(val);
		board[x][y].remove(idx);
		return;
	}
	
	private static void board_init() {
		
		int x = N/2;
		int y = N/2;
		int d = 0;
		int cnt = 1;
		boolean flag = false;
		boardList.add(new Point(x, y, d));
		
		outer:while(true) {
			
			if(x == 0 && y == 0) break;
			
			for(int i=0;i<cnt;i++) {
				if(x == 0 && y == 0) break outer;
				x += dx[d];
				y += dy[d];
				if(i == cnt-1) {
					if(d == 3) boardList.add(new Point(x, y, 0));
					else boardList.add(new Point(x, y, d+1));
				}
				else boardList.add(new Point(x, y, d));
			}
			
			if(d==3) d = 0;
			else d++;
			
			if(!flag) {
				flag = true;
				continue;
			}
			else {
				cnt++;
				flag = false;
				continue;
			}
		}
		
		x = 0;
		y = 0;
		d = 0;
		flag = false;
		backBoardList.add(new Point(x, y, d));
		
		outer:while(true) {
			if(x == N/2 && y == N/2) break;
			if(cnt == N) {
				for(int i=0;i<cnt-1;i++) {
					x += dx2[d];
					y += dy2[d];
					if(i == cnt-2) {
						backBoardList.add(new Point(x, y, 1));
						break;
					}
					else backBoardList.add(new Point(x, y, d));
				}
				d++;
				cnt--;
				continue;
			}
			
			else {
				for(int i=0;i<cnt;i++) {
					if(x == N/2 && y == N/2) break outer;
					x += dx2[d];
					y += dy2[d];
					if(i == cnt-1) {
						if(d == 3) backBoardList.add(new Point(x, y, 0));
						else backBoardList.add(new Point(x, y, d+1));
					}
					else backBoardList.add(new Point(x, y, d));
				}
				if(d==3) d = 0;
				else d++;
				
				if(!flag) {
					flag = true;
					continue;
				}
				else {
					cnt--;
					flag = false;
					continue;
				}
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
