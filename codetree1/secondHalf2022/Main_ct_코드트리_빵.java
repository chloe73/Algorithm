package algo.codetree1.secondHalf2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_코드트리_빵 {
	
	static int N,M,time;
	static int[][] board;
	// 방향 우선순위 :    ↑  ←  → ↓
	static int[] dx = {-1,0,0,1};
	static int[] dy = {0,-1,1,0};
	static HashMap<Integer, Person> pMap;
	static class Person {
		int x,y,m,tx,ty;
		boolean isArrived;
		public Person(int x, int y, int m, int tx, int ty) {
			this.x = x;
			this.y = y;
			this.m = m;
			this.tx = tx;
			this.ty = ty;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		pMap = new HashMap<>();
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			
			int tx = Integer.parseInt(st.nextToken())-1;
			int ty = Integer.parseInt(st.nextToken())-1;
			
			pMap.put(i, new Person(-1, -1, i, tx, ty));
		} // input end
		
		time = 0;
		
		solve();
		
		System.out.println(time);
	}

	private static void solve() {
		time = 1;
		
		while(!AllPersonArrived()) {
			// 빵을 구하고자 하는 m명의 사람이 있는데, 1번 사람은 정확히 1분에, 
			// 2번 사람은 정확히 2분에, ..., m번 사람은 정확히 m 분에 각자의 베이스캠프에서 출발하여 편의점으로 이동하기 시작합니다.
			
			// 코드트리 빵을 구하고 싶은 사람들은 다음과 같은 방법으로 움직입니다. 이 3가지 행동은 총 1분 동안 진행되며, 정확히 1, 2, 3 순서로 진행되어야 함에 유의합니다.

			// 1. 격자에 있는 사람들 모두가 본인이 가고 싶은 편의점 방향을 향해서 1 칸 움직입니다. 
			// 최단거리로 움직이며 최단 거리로 움직이는 방법이 여러가지라면 ↑, ←, →, ↓ 의 우선 순위로 움직이게 됩니다. 
			// 여기서 최단거리라 함은 상하좌우 인접한 칸 중 이동가능한 칸으로만 이동하여 도달하기까지 거쳐야 하는 칸의 수가 최소가 되는 거리를 뜻합니다.
			move();
			
			// 2. 만약 편의점에 도착한다면 해당 편의점에서 멈추게 되고, 이때부터 다른 사람들은 해당 편의점이 있는 칸을 지나갈 수 없게 됩니다. 
			// 격자에 있는 사람들이 모두 이동한 뒤에 해당 칸을 지나갈 수 없어짐에 유의합니다.
			check_movable_pos();
			
			// 3. 현재 시간이 t분이고 t ≤ m를 만족한다면, t번 사람은 자신이 가고 싶은 편의점과 가장 가까이 있는 베이스 캠프에 들어갑니다. 
			go_to_baseCamp();
			
			if(AllPersonArrived()) return;
			time++;
		}
	}
	
	private static void check_movable_pos() {
		for(int i : pMap.keySet()) {
			Person temp = pMap.get(i);
			
			if(temp.x == -1 && temp.y == -1) continue;
			
			if(temp.isArrived) continue;
			
			if(temp.x == temp.tx && temp.y == temp.ty && !temp.isArrived) {
				temp.isArrived = true;
				board[temp.x][temp.y] = -1;
			}
		}
	}

	static class Point {
		int x,y,dist;
		String dir;
		public Point(int x, int y, int dist, String dir) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.dir = dir;
		}
	}
	
	private static void move() {
		
		for(int i : pMap.keySet()) {
			Person temp = pMap.get(i);
			
			if(temp.x == -1 && temp.y == -1) continue;
			
			if(temp.isArrived) continue;
			
			// 격자에 있는 사람인 경우에만 한 칸 이동함.
			Queue<Point> q = new LinkedList<>();
			boolean[][] visited = new boolean[N][N];
			q.add(new Point(temp.x,temp.y,0,""));
			visited[temp.x][temp.y] = true;
			int distance = Integer.MAX_VALUE;
			String finalRoute = "";
			
			while(!q.isEmpty()) {
				Point tmp = q.poll();
				int x = tmp.x;
				int y = tmp.y;
				
				if(x == temp.tx && y == temp.ty) {
					if(distance > tmp.dist) {
						distance = tmp.dist;
						finalRoute = tmp.dir;
					}
					continue;
				}
				
				for(int d= 0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(!isValid(nx, ny) || visited[nx][ny] || board[nx][ny] == -1) continue;
					
					visited[nx][ny] = true;
					q.add(new Point(nx,ny,tmp.dist+1,tmp.dir+""+d));
				}
			}
			
			int d = finalRoute.charAt(0)-'0';
			int nx = temp.x + dx[d];
			int ny = temp.y + dy[d];
				
			temp.x = nx;
			temp.y = ny;
		}
		
	}

	private static void go_to_baseCamp() {
		Person target = pMap.get(time);
		
		if(!pMap.containsKey(time)) return;
		
		if(!(target.x == -1 && target.y == -1)) return;
		
		// 현재 시간이 t분이고 t ≤ m를 만족한다면, t번 사람은 자신이 가고 싶은 편의점과 가장 가까이 있는 베이스 캠프에 들어갑니다. 
		if(time <= target.m) {
			// 여기서 가장 가까이에 있다는 뜻 역시 1에서와 같이 최단거리에 해당하는 곳을 의미합니다. 
			// 가장 가까운 베이스캠프가 여러 가지인 경우에는 그 중 행이 작은 베이스캠프, 행이 같다면 열이 작은 베이스 캠프로 들어갑니다. 
			// t번 사람이 베이스 캠프로 이동하는 데에는 시간이 전혀 소요되지 않습니다.
			int[] pos = bfs(target.tx, target.ty);
			int nx = pos[0];
			int ny = pos[1];
			
			// 이때부터 다른 사람들은 해당 베이스 캠프가 있는 칸을 지나갈 수 없게 됩니다. 
			// t번 사람이 편의점을 향해 움직이기 시작했더라도 해당 베이스 캠프는 앞으로 절대 지나갈 수 없음에 유의합니다. 
			// 마찬가지로 격자에 있는 사람들이 모두 이동한 뒤에 해당 칸을 지나갈 수 없어짐에 유의합니다.
			board[nx][ny] = -1;
			
			// 가려는 편의점과 가장 가까운 베이스캠프로 이동
			target.x = nx;
			target.y = ny;
		}
	}
	
	private static int[] bfs(int r, int c) {
		int[] ret = new int[2];
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		q.add(new int[] {r,c,0});
		visited[r][c] = true;
		int distance = Integer.MAX_VALUE;
		int ex = Integer.MAX_VALUE;
		int ey = Integer.MAX_VALUE;
		
		while(!q.isEmpty()) {
			int[] tmp = q.poll();
			int x = tmp[0];
			int y = tmp[1];
			int dist = tmp[2];
			
			if(board[x][y] == 1) {
				if(distance > dist) {
					distance = dist;
					ex = x;
					ey = y;
				}
				else if(distance == dist) {
					// 가장 가까운 베이스캠프가 여러 가지인 경우에는 그 중 행이 작은 베이스캠프, 
					if(ex > x) {
						ex = x;
						ey = y;
						continue;
					}
					// 행이 같다면 열이 작은 베이스 캠프로 들어갑니다. 
					if(ex == x && ey > y) {
						ey = y;
						continue;
					}
				}
				continue;
			}
			
			for(int d= 0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx, ny) || visited[nx][ny] || board[nx][ny] == -1) continue;
				
				visited[nx][ny] = true;
				q.add(new int[] {nx,ny,dist+1});
			}
		}
		
		ret[0] = ex;
		ret[1] = ey;
		
		return ret;
	}

	private static boolean AllPersonArrived() {
		for(int i : pMap.keySet()) {
			if(!pMap.get(i).isArrived) return false;
		}
		return true;
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
