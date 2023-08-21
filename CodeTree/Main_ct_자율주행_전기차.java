package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_자율주행_전기차 {
	
	static int N,M,C,result;
	static int CX,CY; // 자율주행 전기차 위치
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static HashMap<Integer, Passenger> passengerMap;
	static class Point implements Comparable<Point> {
		int num;
		int x,y,dist;
		public Point(int num, int x, int y, int dist) {
			this.num = num;
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
		@Override
		public int compareTo(Point o) {
			if(this.dist == o.dist) {
				if(this.x == o.x) return this.y - o.y;
				return this.x - o.x;
			}
			return this.dist - o.dist;
		}
		
	}
	static class Passenger {
		int sx,sy;
		int ex,ey;
		public Passenger(int sx, int sy, int ex, int ey) {
			this.sx = sx;
			this.sy = sy;
			this.ex = ex;
			this.ey = ey;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		passengerMap = new HashMap<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // 도로 정보 input
		
		// 자율주행 전기차 초기 위치 정보
		st = new StringTokenizer(br.readLine());
		CX = Integer.parseInt(st.nextToken())-1;
		CY = Integer.parseInt(st.nextToken())-1;
		
		passengerMap = new HashMap<>();
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken())-1;
			int sy = Integer.parseInt(st.nextToken())-1;
			int ex = Integer.parseInt(st.nextToken())-1;
			int ey = Integer.parseInt(st.nextToken())-1;
			
			passengerMap.put(i, new Passenger(sx, sy, ex, ey));
		} // 각 승객의 출발지 위치 정보, 도착지 위치 정보 input
		
		result = -1;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int k=0;k<M;k++) {
			// 1. 전기차의 현재 위치에서 가장 가까운 승객 찾기
			// 승객이 여러명일 경우 현재 위치에서 최단 거리가 가장 짧은 승객을 먼저 태웁니다. 
			// 만약 그런 승객이 여러 명일 경우에는 가장 위에 있는 승객을, 
			// 그런 승객이 여러 명일 때는 가장 왼쪽에 있는 승객을 고릅니다.
			int idx = find_passenger();
			
			if(idx == -1) {
				result = -1;
				return;
			}
			
			int ex = passengerMap.get(idx).ex;
			int ey = passengerMap.get(idx).ey;
			
			int plusDist = bfs(CX, CY, ex, ey);
			
			if(plusDist == -1) {
				result = -1;
				return;
			}
			
			if(C < plusDist) {
				result = -1;
				return;
			}
			
			// 해당 승객을 도착지까지 문제없이 데려다 줄 수 있는 경우
			if(C >= plusDist) {
				CX = ex;
				CY = ey;
				C -= plusDist;
				C += plusDist*2;
				passengerMap.remove(idx);
			}
			// 불가능한 경우
			else {
				result = -1;
				return;
			}
		}
		
		result = C;
	}
	
	private static int find_passenger() {
		int index = -1;
		
		PriorityQueue<Point> pq = new PriorityQueue<>();
		
		int[][] copyBoard = new int[N][N];
		
		for(int i :passengerMap.keySet()) {
			Passenger p = passengerMap.get(i);
			
			copyBoard[p.sx][p.sy] = i;
		}
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		q.add(new int[] {CX,CY,0});
		visited[CX][CY] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int dist = temp[2];
			
			if(copyBoard[x][y] > 0) pq.add(new Point(copyBoard[x][y], x, y, dist));
			
			for(int i=0;i<4;i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(!is_valid(nx, ny) || visited[nx][ny] || board[nx][ny] == 1) continue;
				
				visited[nx][ny] = true;
				q.add(new int[] {nx,ny,dist+1});
			}
		}
		
		if(pq.size() == 0) return index;
		
		if(pq.size() > 0) {
			Point p = pq.poll();
			
			if(C < p.dist) return index;
			else {
				C -= p.dist;
				CX = p.x;
				CY = p.y;
				index = p.num;
				return index;
			}
		}
		
		return index;
	}

	private static int bfs(int sx, int sy, int ex, int ey) {
		int distance = -1;
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		q.add(new int[] {sx, sy, 0});
		visited[sx][sy] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int dist = temp[2];
			
			if(x == ex && y == ey) {
				return dist;
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == 0) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny,dist+1});
				}
			}
		}
		
		return distance;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 ||c<0 || r>=N ||c>=N) return false;
		return true;
	}

}