package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_23290_마법사상어와복제 {
	
	static int M,S,sx,sy,result;
	static ArrayList<Fish>[][] board;
	static int[][] smell;
	static int[] fx = {0,-1,-1,-1,0,1,1,1};
	static int[] fy = {-1,-1,0,1,1,1,0,-1};
	//  상 좌 하 우
	static int[] dx = {0,-1,0,1,0};
	static int[] dy = {0,0,-1,0,1};
	static int fishNum;
	static ArrayList<Fish> fishList, copyList;
	static class Fish {
		int x,y,d;
		public Fish(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		board = new ArrayList[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				board[i][j] = new ArrayList<>();
			}
		}
		
		fishList = new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			
			fishList.add(new Fish(x, y, d));
			board[x][y].add(new Fish(x, y, d));
		}
		
		st = new StringTokenizer(br.readLine());
		sx = Integer.parseInt(st.nextToken())-1;
		sy = Integer.parseInt(st.nextToken())-1;
		
		smell = new int[4][4];
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int i=0;i<S;i++) {
			// 1. 물고기 복제
			copy_fish();
			
			// 2. 물고기 이동
			move_fish();
			
			fishNum = Integer.MIN_VALUE;
			// 3. 상어 연속 3칸 이동
			move_shark();
			
			// 4. 2번 전에 생긴 물고기 냄새 사라짐
			remove_smell();
			
			// 5. 물고기 복제 완료 -> 격자에 물고기 추가
			add_fish();
		}
		
		result = fishList.size();
	}

	private static void add_fish() {
		int size = fishList.size();
		for(Fish f : copyList) {
			fishList.add(new Fish(f.x, f.y, f.d));
			board[f.x][f.y].add(new Fish(f.x, f.y, f.d));
		}
	}

	private static void remove_smell() {
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(smell[i][j] > 0) smell[i][j]--;
			}
		}
	}
	
	static int[] temp = new int[3];
	static int[] sharkMove = new int[3];
	
	private static void move_shark_dfs(int idx) {
		// 상어 이동 방향 중복순열로 체크
		if(idx == 3) {
			int fNum = check_fish();
			if(fNum == -1) return;
			if(fNum > fishNum) {
				fishNum = fNum;
				for(int i=0;i<3;i++) {
					sharkMove[i] = temp[i];
				}
			}
			return;
		}
		
		for(int i=1;i<=4;i++) {
			temp[idx] = i;
			move_shark_dfs(idx+1);
		}
	}

	private static int check_fish() {
		int cnt = 0;
		
		boolean[][] visited = new boolean[4][4];
		
		int x = sx;
		int y = sy;
		
		for(int i=0;i<3;i++) {
			x += dx[temp[i]];
			y += dy[temp[i]];
			
			if(!isValid(x,y)) return -1;
			
			if(visited[x][y]) continue;
			
			visited[x][y] = true;
			cnt += board[x][y].size();
		}
		
		return cnt;
	}

	private static void move_shark() {
		// 상어 연속 3칸 이동 : 가장 물고기를 많이 먹는 경로 그 중 사전 순 앞서는 방법으로 이동
		
		move_shark_dfs(0);
	
		// 최종 선택된 이동 방향으로 상어이동 + 물고기 먹음 + 냄새 남김 + board 다시 세팅
		
		for(int i=0;i<3;i++) {
			sx += dx[sharkMove[i]];
			sy += dy[sharkMove[i]];
			
			if(board[sx][sy].size() > 0) {
				smell[sx][sy] = 3;
				board[sx][sy].clear();
			}
		}
		
		fishList.clear();
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(board[i][j].size() > 0) {
					for(Fish temp : board[i][j]) {
						fishList.add(new Fish(temp.x, temp.y, temp.d));
					}
				}
			}
		}

	}

	private static void move_fish() {
		// 이동 불가능한 칸 : 상어가 있는 칸, 물고기 냄새 있는 칸, 범위 밖
		
		for(int i=0;i<fishList.size();i++) {
			Fish temp = fishList.get(i);
			
			int d = temp.d;
			int nx = temp.x;
			int ny = temp.y;
			int cnt = 0;
			// 물고기 이동 전 이전 위치 삭제
			board[temp.x][temp.y].clear();
			boolean flag = false;
			while (cnt++ <= 8) {
				nx = temp.x + fx[d];
				ny = temp.y + fy[d];
				
				if(isValid(nx,ny) && (nx != sx || ny != sy) && smell[nx][ny] == 0) {
					flag = true;
					break;
				}
				
				d = change_dir(d);
			}
			
			if(!flag) {
				d = temp.d;
				nx = temp.x;
				ny = temp.y;
			}
			
			fishList.get(i).x = nx;
			fishList.get(i).y = ny;
			fishList.get(i).d = d;
		}
		
		for(Fish temp : fishList) {
			board[temp.x][temp.y].add(temp);
		}
	}

	private static int change_dir(int d) {
		
		if(d == 0) return 7;
		return d-1;
	}

	private static void copy_fish() {
		copyList = new ArrayList<>();
		
		for(Fish f : fishList) {
			copyList.add(new Fish(f.x, f.y, f.d));
		}
		
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=4 || c>=4) return false;
		return true;
	}
}