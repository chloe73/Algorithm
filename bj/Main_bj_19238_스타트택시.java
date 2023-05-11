package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_19238_스타트택시 {
	
	static int N,M,fuel,tx,ty;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static HashMap<Integer, Customer> customerList;
	static PriorityQueue<Node> pq;
	static class Customer {
		int sx,sy,ex,ey;
		public Customer(int sx, int sy, int ex, int ey) {
			this.sx = sx;
			this.sy = sy;
			this.ex = ex;
			this.ey = ey;
		}
	}
	static class Node implements Comparable<Node> {
		int r,c,dist,idx;
		public Node(int r, int c, int dist, int idx) {
			this.r = r;
			this.c = c;
			this.dist = dist;
			this.idx = idx;
		}
		public int compareTo(Node o) {
			if(this.dist == o.dist) {
				if(this.r == o.r) {
					return this.c - o.c;
				}
				return this.r - o.r;
			}
			return this.dist - o.dist;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());
		board = new int[N][N];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 택시의 시작 위치 행과 열 번호
		st = new StringTokenizer(br.readLine());
		tx = Integer.parseInt(st.nextToken())-1;
		ty = Integer.parseInt(st.nextToken())-1;
		
		customerList = new HashMap<>();
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken())-1;
			int sy = Integer.parseInt(st.nextToken())-1;
			int ex = Integer.parseInt(st.nextToken())-1;
			int ey = Integer.parseInt(st.nextToken())-1;
			customerList.put(i, new Customer(sx, sy, ex, ey));
		} // input end
		
		solve();

		System.out.println(fuel);
	}

	private static void solve() {

		for(int cnt=0;cnt<M;cnt++) {
			
			// 현재 택시 위치에서 각 손님까지의 최단거리 구하기
			int customerIdx = find_customer();
			
			if(fuel == -1) break;
			
			// 승객의 목적지까지 이동시키기
			move_destination(customerIdx);
			if(fuel == -1) break;
		}
		
	}

	private static void move_destination(int customerIdx) {
		
		// 가야 할 도착지
		int ex = customerList.get(customerIdx).ex;
		int ey = customerList.get(customerIdx).ey;
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {tx,ty,0});
		boolean[][] visited = new boolean[N][N];
		visited[tx][ty] = true;
		boolean flag = false; // 목적지까지 갈 수 있는지 체크하는 변수
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int dist = temp[2];
			
			if(x == ex && y == ey) {
				if(fuel < dist) {
					fuel = -1;
					return;
				} else {
					flag = true;
					fuel = fuel - dist + (dist * 2);
					tx = ex;
					ty = ey;
					customerList.remove(customerIdx);
				}
				break;
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx<0 || ny<0 || nx>=N || ny>= N) continue;
				
				if(visited[nx][ny] || board[nx][ny] == 1) continue;
				
				visited[nx][ny] = true;
				q.add(new int[] {nx,ny,dist+1});
			}
		}
		
		if(!flag) {
			fuel = -1;
			return;
		}
	}

	// 모든 승객에 대해 각각 bfs를 돌리면 안됨 !!!!! -> 
	private static int find_customer() {
		
		int index = -1;
		pq = new PriorityQueue<>();
		int[][] cus_board = new int[N][N];
		
		for(int i : customerList.keySet()) { // 각 승객의 위치 표시하기
			Customer customer = customerList.get(i);
			int cx = customer.sx;
			int cy = customer.sy;
			
			cus_board[cx][cy] = i;
			
		}
			
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {tx,ty,0});
		boolean[][] visited = new boolean[N][N];
		visited[tx][ty] = true;
		
		while(!q.isEmpty()) {
			int size = q.size();
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int dist = temp[2];
			
			for(int s=0;s<size;s++) {
				
				if(cus_board[x][y] > 0) {
					pq.add(new Node(x, y, dist, cus_board[x][y]));
					continue;
				}
				
				for(int d=0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(nx<0 || ny<0 || nx>=N || ny>= N) continue;
					
					if(visited[nx][ny] || board[nx][ny] == 1) continue;
					
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny,dist+1});
				}
			}
			
			
		}
		
		
		if(pq.size() > 0) {
			Node n = pq.poll();			
			// 선택된 승객의 번호와 거리
			int idx = n.idx;
			int dist = n.dist;
			if(fuel < dist) {
				fuel = -1;
				return index;
			} else {
				// 택시 이동 후 연료 그 거리만큼 줄어든다.
				fuel -= dist;
				tx = customerList.get(idx).sx;
				ty = customerList.get(idx).sy;
				index = idx;
			}
		} else {
			fuel = -1;
			return index;
		}
		
		return index;
	}

}
