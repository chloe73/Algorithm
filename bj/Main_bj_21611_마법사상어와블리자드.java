package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_bj_21611_마법사상어와블리자드 {
	
	static int N,M,num1,num2,num3,sx,sy;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static ArrayList<Point> bizList,destroyList;
	static ArrayList<int[]> cmd, bombList;
	static class Point {
		int x,y,num;
		public Point(int x, int y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		sx = N / 2;
		sy = N / 2;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cmd = new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			
			cmd.add(new int[] {d,s});
		} // input end
		
		// bizList에 상어가 있는 위치부터 차례대로 값 넣기
		bizList = new ArrayList<>();
		bizList_init();
		
		solve();
		
		System.out.println(num1 + num2 * 2 + num3 * 3);
	}

	private static void bizList_init() {
		//        왼, 아래, 오, 위
		int[] dr = {0,1,0,-1};
		int[] dc = {-1,0,1,0};
		
		int nx = sx;
		int ny = sy;
		int d = 0;
		int cnt = 1; // 해당 방향으로 방향을 바꾸지 않고 이동해야 할 칸의 개수
		int idx = 0; // 방향을 바꾸고 있는 횟수 저장해서 홀수인지 짝수인지로 cnt값 1씩 증가시킴
		int index = 0;
		outer:while(true) {
			
			for(int i=0;i<cnt;i++) {
				nx += dr[d];
				ny += dc[d];
				if(board[nx][ny] == 0) bizList.add(new Point(nx, ny, -1));
				else bizList.add(new Point(nx, ny, board[nx][ny]));
				board[nx][ny] = index++;					
				
				if(nx == 0 && ny == 0) break outer;
			}
			idx++;
			if(idx % 2 == 0) cnt++;
			d = change_dir(d);
		}
		
	}

	private static int change_dir(int d) {
		if(d == 0) return 1;
		else if(d == 1) return 2;
		else if(d == 2) return 3;
		else return 0;
	}

	private static void solve() {
		
		for(int[] temp : cmd) {
			int dir = temp[0];
			int s = temp[1];
			
			// 1. 블리자드 마법 시전 : 
			blizard(dir,s);
			
			move();
			
			while(true) {
				if(bomb()) move2();
				
				else break;
			}
			
			change();
		}
		
	}

	private static void change() {
		// TODO Auto-generated method stub
		ArrayList<int[]> changeList = new ArrayList<>();
		
		int cnt = 1;
		int tempNum = bizList.get(0).num;
		bizList.get(0).num = -1;
		
		for(int i=1;i<bizList.size();i++) {
			if(bizList.get(i).num == -1) {
				changeList.add(new int[] {cnt, tempNum});
				break;
			}
			
			if(bizList.get(i).num == tempNum) {
				cnt++;
				bizList.get(i).num = -1;
				continue;
			}
			
			else {
				changeList.add(new int[] {cnt,tempNum});
				
				cnt = 1;
				tempNum = bizList.get(i).num;
				
				bizList.get(i).num = -1;
				continue;
			}
		}
		
		int idx = 0;
		for(int[] temp : changeList) {
			if(idx >= bizList.size()) break;
			bizList.get(idx).num = temp[0];
			bizList.get(idx+1).num = temp[1];
			idx += 2;
		}
	}
	
	private static void move2() {
		// 터진 후 
		int idx=0, cnt=0;
		
		for(int[] temp : bombList) {
			if(idx == 0 && cnt == 0) {
				idx = temp[0];
				cnt = temp[1];
				
				while(true) {
					if(idx+cnt >= bizList.size()) break;
					
					if(bizList.get(idx+cnt).num == 0 || bizList.get(idx+cnt).num == -1) break;
					
					bizList.get(idx).num = bizList.get(idx+cnt).num;
					
					idx++;
				}
				
				continue;
			}
			
			else {
				cnt += temp[1];
				
				while(true) {
					if(idx+cnt >= bizList.size()) break;
					
					if(bizList.get(idx+cnt).num == 0 || bizList.get(idx+cnt).num == -1) break;
					
					bizList.get(idx).num = bizList.get(idx+cnt).num;
					
					idx++;
				}
			}
		}
		
		for(int i=idx;i<bizList.size();i++) {
			if(bizList.get(i).num == -1) break;
			
			bizList.get(i).num = -1;
		}
	}

	private static boolean bomb() {
		// 4개 이상 연속하는 구슬이 있다면 bombList에 추가
		
		int cnt = 1;
		int tempNum = bizList.get(0).num;
		bombList = new ArrayList<>();
		
		for(int i=1;i<bizList.size();i++) {
			if(bizList.get(i).num == -1) break;
			
			if(tempNum == bizList.get(i).num) {
				cnt++;
				continue;
			}
			
			else {
				if(cnt >= 4) {
					// 폭발한 구슬의 개수 갱신
					if(tempNum == 1) num1 += cnt;
					else if(tempNum == 2) num2 += cnt;
					else if(tempNum == 3) num3 += cnt;
					
					bombList.add(new int[] {i-cnt, cnt});

				}
				tempNum = bizList.get(i).num;
				cnt = 1;
			}
			
		}
		
		if(bombList.size() == 0) return false;
		else {
			for(int[] temp : bombList) {
				int nx = temp[0];
				while(nx < temp[0]+temp[1]) {
					bizList.get(nx).num = 0;
					 nx++;
				 }
			}

			return true;
		}
	}

	private static void move() {
		int x = destroyList.get(0).x;
		int y = destroyList.get(0).y;
		int index = board[x][y]; // 처음 파괴된 구슬 위치부터 board에서 인덱스 번호가 바뀜
		
		int destroyCnt = 1;
		for(int i=index;i<bizList.size();i++) {
			if(i+destroyCnt >= bizList.size()) break;
			Point nextBiz = bizList.get(i+destroyCnt);
			if(nextBiz.num > 0) {
				bizList.get(i).num = nextBiz.num;
				continue;
			}
			else if(nextBiz.num == 0) {
				destroyCnt++;
				bizList.get(i).num = bizList.get(i+destroyCnt).num;
				continue;
			}
			 
			// 현재 위치의 구슬 번호가 -1이라면 : 구슬이 아예 없는 자리라면
			if(bizList.get(i).num == -1) {
				int nx = i;
				while(--nx >= i-destroyCnt) {
					bizList.get(nx).num = -1;
				}
				break;
			}
		}
		
	}

	private static void blizard(int dir, int s) {
		// dir방향으로 s칸 구슬 파괴
		int nx = sx;
		int ny = sy;
		
		destroyList = new ArrayList<>();
		
		for(int i=1;i<=s;i++) {
			nx += dx[dir];
			ny += dy[dir];
			
			if(nx < 0 || ny < 0 || nx >= N || ny >= N) break;
			
			int idx = board[nx][ny];
			bizList.get(idx).num = 0;
			destroyList.add(new Point(nx, ny, 0));
		}
	}

}