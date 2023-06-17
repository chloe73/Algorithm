package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_17135_캐슬디펜스 {
	
	static int N,M,D,result;
	static int[][] board, copy_board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[] isChecked;
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		board = new int[N+1][M];
		isChecked = new boolean[M];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		result = 0;
		
		comb(0,0);
		
		System.out.println(result);
	}

	private static void comb(int start, int idx) {
		
		if(idx == 3) { // 궁수 3명 자리 다 배치했으면 시뮬레이션 시작
			ArrayList<Point> castle = new ArrayList<>();
			
			for(int i=0;i<M;i++) {
				if(isChecked[i]) {
					board[N][i] = 2;
					castle.add(new Point(N, i));
				}
				else board[N][i] = 0;
			}
			
			attack(castle);
		}
		
		for(int i=start;i<M;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				comb(i+1, idx+1);
				isChecked[i] = false;				
			}
		}
		
	}

	private static void attack(ArrayList<Point> castle) {
		
		int cnt = 0;
		copy_board = copy();
		
		while(!check_status(copy_board)) {
			// 거리 D 이하 적 중에서 가장 가깝고 가장 왼쪽에 있는 적 제거
			cnt += remove_enemy(castle);
			
			// 적 한 행 아래로 이동
			move_enemy();
		}
		
		result = Math.max(result, cnt);
	}
	
	private static void move_enemy() {
		
		for(int i=N-1;i>=0;i--) {
			for(int j=0;j<M;j++) {
				if(i == N-1) copy_board[i][j] = 0;
				else {
					copy_board[i+1][j] = copy_board[i][j];
					copy_board[i][j] = 0;
				}
			}
		}
		
	}

	private static int remove_enemy(ArrayList<Point> castle) {
		// 각 궁수마다 가장 가깝고 가장 왼쪽에 있는 적 제거. 단, 거리 D 이하 안에서
		
		ArrayList<Point> removeList = new ArrayList<>(); // 제거할 적 리스트
		int removeNum = 0;
		
		for(Point p : castle) {
			// 각 궁수마다 적 선택 -> bfs
			
			boolean[][] visited = new boolean[N+1][M];
			Queue<int[]> q = new LinkedList<>();
			q.add(new int[] {p.x,p.y,0});
			visited[p.x][p.y] = true;
			
			int targetX = -1;
			int targetY = -1; // 가장 왼쪽에 있는 적
			int dist = Integer.MAX_VALUE; // 최소 거리
			
			while(!q.isEmpty()) {
				int[] temp = q.poll();
				int x = temp[0];
				int y = temp[1];
				int d = temp[2];
				
				if(d > D) break;
				
				if(copy_board[x][y] == 1) {
					
					if(dist > d) {
						targetX = x;
						targetY = y;
						dist = d;
					}
					else if(dist == d) { // 가까운게 여러 개라면
						if(targetX != -1 && targetY != -1) {
							if(targetY > y) {
								targetX = x;
								targetY = y;
							}
						}
					}
				}
				
				
				for(int dir = 0;dir<4;dir++) {
					int nx = x + dx[dir];
					int ny = y + dy[dir];
					
					if(nx<0|| ny<0 || nx>N ||ny>=M || visited[nx][ny]) continue;
					
					if(d+1 <= D && copy_board[nx][ny] == 1) {
						visited[nx][ny] = true;
						q.add(new int[] {nx,ny,d+1});
						continue;
					}
					
					if(d+1 <= D && copy_board[nx][ny] == 0) {
						visited[nx][ny] = true;
						q.add(new int[] {nx,ny,d+1});
					}
				}
			}
			
			if(targetX != -1 && targetY != -1) removeList.add(new Point(targetX, targetY));
		}
		
		for(Point p : removeList) {
			if(copy_board[p.x][p.y] == 1) {
				removeNum++;
				copy_board[p.x][p.y] = 0;
			}
		}
		
		return removeNum;
	}

	private static boolean check_status(int[][] arr) {
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(arr[i][j] == 1) return false;
			}
		}
		
		return true;
	}

	private static int[][] copy() {
		int[][] arr = new int[N+1][M];
		
		for(int i=0;i<=N;i++) {
			for(int j=0;j<M;j++) {
				arr[i][j] = board[i][j];
			}
		}
		
		return arr;
	}
}
