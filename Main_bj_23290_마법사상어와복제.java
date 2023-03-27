package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_23290_마법사상어와복제 {
	
	static int M,S,sx,sy,result;
	static ArrayList<Integer>[][] board;
	static int[][] smell;
	static int[] fx = {0,-1,-1,-1,0,1,1,1};
	static int[] fy = {-1,-1,0,1,1,1,0,-1};
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
			board[x][y].add(i);
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
			board[f.x][f.y].add(size++);
		}
		
	}

	private static void remove_smell() {
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(smell[i][j] > 0) smell[i][j]--;
			}
		}
		
	}

	private static void move_shark() {
		// 상어 연속 3칸 이동 : 가장 물고기를 많이 먹는 경로 그 중 사전 순 앞서는 방법으로 이동
		
		Queue<int[]> q = new LinkedList<>();
		//            상 좌 하 우
		int[] dx = {0,-1,0,1,0};
		int[] dy = {0,0,-1,0,1};
		boolean[][] visited = new boolean[4][4];
		q.add(new int[] {sx,sy,0,0,0});
		visited[sx][sy] = true;
		int fishNum = 0;
		int direction = 0;
		int[] move_dir = new int[4];
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			int dir = temp[3]; // 이동 방향
			int fNum = temp[4];
			
			if(cnt == 3) {
				System.out.println(dir+", "+fNum);
				if(fNum > fishNum) { // 먹는 물고기 수가 더 많은 방법이라면
					direction = dir;
				}
				else if(fNum == fishNum) { // 먹는 물고기 수가 같다면
//					System.out.println(dir);
					if(dir < direction) direction = dir;						
				}
				continue;
			}
			
			for(int i=1;i<=4;i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(!isValid(nx,ny)) continue;
				
				if(visited[nx][ny]) continue;
				
				// 이동방향 추가
				String s = dir+""+i;
				int newDir = Integer.parseInt(s);
				
				visited[nx][ny] = true;
				if(board[nx][ny].size() == 0) {
					q.add(new int[] {nx,ny,cnt+1,newDir,fNum});
					continue;
				}
				else if(board[nx][ny].size() > 0) {
					int size = board[nx][ny].size();
					q.add(new int[] {nx,ny,cnt+1,newDir,fNum+size});
					continue;
				}
			}
		}
		
		// 선택된 이동 방법으로 상어 이동 + 물고기 먹고 + 냄새 남김
		System.out.println("==== last direction ====");
		System.out.println(direction);
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
			board[temp.x][temp.y].remove(Integer.valueOf(i));
			
			while (cnt++ < 8) {
				nx = temp.x + fx[d];
				ny = temp.y + fy[d];
				
				if(isValid(nx,ny) && (nx != sx || ny != sy) && smell[nx][ny] == 0) break;
				
				d = change_dir(d);
			}
			
			fishList.get(i).x = nx;
			fishList.get(i).y = ny;
			fishList.get(i).d = d;
			// 물고기 이동 후 board에 위치 추가
			board[nx][ny].add(i);
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
