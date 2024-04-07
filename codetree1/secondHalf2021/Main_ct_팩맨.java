package algo.codetree1.secondHalf2021;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_팩맨 {

	static int M,T,result;
	static int px,py;
	static Queue<Monster>[][] board,copy;
	static ArrayList<Monster> mList;
	static int[][] dead;
	// 방향 d는 1부터 순서대로 ↑, ↖, ←, ↙, ↓, ↘, →, ↗ 
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,-1,-1,-1,0,1,1,1};
	static class Monster {
		int x,y,d;
		public Monster(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		board = new LinkedList[4][4];
		copy = new LinkedList[4][4];
		dead = new int[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				board[i][j] = new LinkedList<>();
				copy[i][j] = new LinkedList<>();
			}
		}
		
		st = new StringTokenizer(br.readLine());
		px = Integer.parseInt(st.nextToken())-1;
		py = Integer.parseInt(st.nextToken())-1;
		
		mList = new ArrayList<>();
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			
			board[x][y].add(new Monster(x, y, d));
			mList.add(new Monster(x, y, d));
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(T-- > 0) {
			// 1. 몬스터 복제 시도
			copyMonster();
			
			// 2. 몬스터 이동
			moveMonster();

			// 3. 팩맨 이동
			movePackMan();
			
			// 4. 몬스터 시체 소멸			
			// 5. 몬스터 복제 완성
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					if(dead[i][j]>0) dead[i][j]--;
					if(copy[i][j].size() > 0) {
						while(!copy[i][j].isEmpty()) {
							Monster temp = copy[i][j].poll();
							board[i][j].add(temp);
							mList.add(temp);
						}
					}
				}
			}
		}
		
        result = mList.size();
	}
	
	static class Point {
		int x,y,dist,cnt;
		String dir;
		public Point(int x, int y, int dist, int cnt, String dir) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.cnt = cnt;
			this.dir = dir;
		}
	}
	static int count;
	static String direction;
	static int[] ddx = {0,-1,0,1,0};
	static int[] ddy = {0,0,-1,0,1};

	private static void movePackMan() {
		// 팩맨의 이동은 총 3칸을 이동하게 되는데, 각 이동마다 상하좌우의 선택지를 가지게 됩니다.
		// 총 4가지의 방향을 3칸 이동하기 때문에 총 64개의 이동 방법이 존재하는데, 
		// 이 중 몬스터를 가장 많이 먹을 수 있는 방향으로 움직이게 됩니다.
		
		// 만약 가장 많이 먹을 수 있는 방향이 여러개라면 상-좌-하-우의 우선순위를 가지며
		// 우선순위가 높은 순서대로 배열하면 "상상상 - 상상좌 - 상상하 - 상상우 - 상좌상 - 상좌좌 - 상좌하 - ..."과 같이 나타낼 수 있습니다. 
		// 이동하는 과정에 격자 바깥을 나가는 경우는 고려하지 않습니다.
		
		count = -1;
		direction = "";
		isChecked = new boolean[5];
		perm(0,px,py, "");
		
		// 이때 이동할 때 이동하는 칸에 있는 몬스터는 모두 먹어치운 뒤, 그 자리에 몬스터의 시체를 남깁니다.
		// 이때 팩맨은 알은 먹지 않으며, 움직이기 전에 함께 있었던 몬스터도 먹지 않습니다. 즉, 이동하는 과정에 있는 몬스터만 먹습니다.
		
		for(int i=0;i<direction.length();i++) {
			int d = direction.charAt(i)-'0';
			px += ddx[d];
			py += ddy[d];
			if(board[px][py].size() > 0) {
				dead[px][py] = 3;
				board[px][py].clear();
			}
		}
		
		mList = new ArrayList<>();
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(board[i][j].size() > 0) {
					for(int k=0;k<board[i][j].size();k++) {
						Monster temp = board[i][j].poll();
						mList.add(new Monster(temp.x, temp.y, temp.d));
						board[i][j].add(temp);
					}
				}
			}
		}
	}
	
	static boolean[] isChecked;
	
	private static void perm(int idx, int x, int y, String dir) {
		
		if(idx == 3) {
			int cnt = 0;
			boolean[][] visited = new boolean[4][4];
			int nx = px;
			int ny = py;
			
			for(int i=0;i<dir.length();i++) {
				int d = dir.charAt(i)-'0';
				nx += ddx[d];
				ny += ddy[d];
				
				if(!isValid(nx, ny)) return;
				
				if(board[nx][ny].size() > 0 && !visited[nx][ny]) {
					visited[nx][ny] = true;
					cnt += board[nx][ny].size();
				}
			}
			
			if(count < cnt) {
				count = cnt;
				direction = dir;
			}
			return;
		}
		
		for(int i=1;i<=4;i++) {
			int nx = x + ddx[i];
			int ny = y + ddy[i];
			if(isValid(nx, ny))
				perm(idx+1,nx,ny, dir+""+i);
		}
	}

	private static void moveMonster() {
		// 몬스터는 현재 자신이 가진 방향대로 한 칸 이동합니다. 
		ArrayList<Monster> renewal = new ArrayList<>();
		
		for(Monster temp : mList) {
			board[temp.x][temp.y].clear();
			int nx = temp.x + dx[temp.d];
			int ny = temp.y + dy[temp.d];
			int d = temp.d;
			
			// 이때 움직이려는 칸에 몬스터 시체가 있거나, 팩맨이 있는 경우거나 격자를 벗어나는 방향일 경우에는 
			// 반시계 방향으로 45도를 회전한 뒤 해당 방향으로 갈 수 있는지 판단합니다. 
			// 만약 갈 수 없다면, 가능할 때까지 반시계 방향으로 45도씩 회전하며 해당 방향으로 갈 수 있는지를 확인합니다. 
			// 만약 8 방향을 다 돌았는데도 불구하고, 모두 움직일 수 없었다면 해당 몬스터는 움직이지 않습니다.
			int cnt = 1;
			while(!isValid(nx, ny) || dead[nx][ny] > 0 || (nx == px && ny == py)) {
				cnt++;
				if(d == 7) d = 0;
				else d++;
				
				nx = temp.x + dx[d];
				ny = temp.y + dy[d];
				
				if(cnt > 8) break;
			}
			
			if(isValid(nx, ny) && dead[nx][ny] == 0 && !(nx == px && ny == py))
				renewal.add(new Monster(nx, ny, d));
			else
				renewal.add(new Monster(temp.x,temp.y,temp.d));
		}
		
		for(Monster m : renewal) {
			board[m.x][m.y].add(new Monster(m.x, m.y, m.d));
		}
		
		mList = renewal;
	}

	private static void copyMonster() {
		
		copy = new LinkedList[4][4];
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				copy[i][j] = new LinkedList<>();
			}
		}
		
		for(Monster m : mList) {
			copy[m.x][m.y].add(new Monster(m.x, m.y, m.d));
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=4 || c>=4) return false;
		return true;
	}
}
