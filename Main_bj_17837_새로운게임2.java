package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_bj_17837_새로운게임2 {
	
	static int N,K,result;
	//                  우 좌 상 하
	static int[] dx = {0,0,0,-1,1};
	static int[] dy = {0,1,-1,0,0};
	static int[][] board;
	static Queue<Integer>[][] q;
	static ArrayList<Horse> hList = new ArrayList<>();
	static class Horse {
		int x,y,d;
		public Horse(int x, int y, int d) {
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
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		q = new LinkedList[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				q[i][j] = new LinkedList<Integer>();
			}
		}
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0;i<K;i++) { // k개의 말 정보 입력
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken());
			hList.add(new Horse(r, c, d));
			q[r][c].add(i);
		} // input end
		
		result = solve();
		
		System.out.println(result);
	}

	private static int solve() {
		int turn = 1;
		while(turn <= 1000) {
			
			// 1. 1번 말부터 k번 말까지 순서대로 이동
			for(int i=0;i<K;i++) {
				// 현재 탐색하는 말의 번호와 상태
				Horse now = hList.get(i);
				
				int nx = now.x + dx[now.d];
				int ny = now.y + dy[now.d];
				
				if(isValid(nx, ny)) {
					moveHorse(nx,ny,i);
				} else { // 이동하려는 칸이 범위를 벗어난 경우 = 파란색의 경우
					now.d = changeDir(now.d);
					nx = now.x + dx[now.d];
					ny = now.y + dy[now.d];
					
				}
				
				// 2. 4개 이상 쌓이는 순간 게임 종료
				if(q[now.x][now.y].size() >= 4) return turn;
			}
			
			
			turn++;
		}
		
		return -1;
	}
	
	private static void moveHorse(int nx, int ny, int i) {
		
		int x = hList.get(i).x;
		int y = hList.get(i).y;
		
		if(board[nx][ny] == 0) { // 흰색인 경우
			
			while(!q[x][y].isEmpty()) {
				int temp = q[x][y].poll();
				q[nx][ny].add(temp);
				hList.get(temp).x = nx;
				hList.get(temp).y = ny;
			}

		} else if(board[nx][ny] == 1) { // 빨간색인 경우
			Stack<Integer> stack = new Stack<Integer>();
			int size = q[x][y].size();
			for(int k=0;k<size;k++) {
				stack.add(q[x][y].poll());
			}
			while(!stack.isEmpty()) {
				int temp = stack.pop();
				q[nx][ny].add(temp);
				hList.get(temp).x = nx;
				hList.get(temp).y = ny;
			}
			
		} else if(board[nx][ny] == 2) { // 파란색인 경우
			hList.get(i).d = changeDir(hList.get(i).d);
			nx = x + dx[hList.get(i).d];
			ny = y + dy[hList.get(i).d];
			if(isValid(nx, ny)&& board[nx][ny] != 2) {
				moveHorse(nx, ny, i);
			}
		}
		
	}

	private static int changeDir(int dir) {
		if(dir == 1) return 2;
		else if(dir == 2) return 1;
		else if(dir == 3) return 4;
		else return 3;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
