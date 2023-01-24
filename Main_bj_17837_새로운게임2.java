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
	static ArrayList<Integer>[][] list;
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
		list = new ArrayList[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				list[i][j] = new ArrayList<Integer>();
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
			list[r][c].add(i);
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
				ArrayList<Integer> up_horse = new ArrayList<>();
				int start_idx = 0;
				int x = now.x;
				int y = now.y;
				
				for(int p=0;p<list[x][y].size();p++) {
					if(list[x][y].get(p) == i) {
						start_idx = p;
						break;
					}
				}
				
				for(int p=start_idx;p<list[x][y].size();p++) {
					up_horse.add(list[x][y].get(p));
				}
				
				int nx = now.x + dx[now.d];
				int ny = now.y + dy[now.d];
				
				if(!isValid(nx, ny) || board[nx][ny] == 2) {
					hList.get(i).d = changeDir(hList.get(i).d);
					nx = now.x + dx[hList.get(i).d];
					ny = now.y + dy[hList.get(i).d];
					if(!isValid(nx, ny) || board[nx][ny] == 2) continue;
					else {
						if(board[nx][ny] == 0) {
							for(int h : up_horse) {
								list[nx][ny].add(h);
								hList.get(h).x = nx;
								hList.get(h).y = ny;
							}
						}
						else if(board[nx][ny] == 1) {
							for(int p=up_horse.size()-1;p>=0;p--) {
								list[nx][ny].add(up_horse.get(p));
								hList.get(up_horse.get(p)).x = nx;
								hList.get(up_horse.get(p)).y = ny;
							}
						}
					}
				}
				else if(board[nx][ny] == 0) {
					for(int h : up_horse) {
						list[nx][ny].add(h);
						hList.get(h).x = nx;
						hList.get(h).y = ny;
					}
				}
				else if(board[nx][ny] == 1) {
					for(int p=up_horse.size()-1;p>=0;p--) {
						list[nx][ny].add(up_horse.get(p));
						hList.get(up_horse.get(p)).x = nx;
						hList.get(up_horse.get(p)).y = ny;
					}
				}
				
				// 2. 4개 이상 쌓이는 순간 게임 종료
				if(list[now.x][now.y].size() >= 4) return turn;
				
				for(int p=list[x][y].size()-1;p>=start_idx;p--) {
					list[x][y].remove(p);
				}
			}
			
			
			turn++;
		}
		
		return -1;
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
