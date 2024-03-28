package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_포탑_부수기 {
	
	static int N,M,K,result;
	static int[][] board;
	static int[] dx = {0,0,1,0,-1};
	static int[] dy = {0,1,0,-1,0};
	static PriorityQueue<Top> pq; // 포탑을 관리하는 자료구조
	static Top attacker, strongestTop; // 공격자, 가장 강한 포탑
	static class Top implements Comparable<Top>{
		int x,y,power,sum,recent;
		
		public Top(int x, int y, int power, int sum, int recent) {
			this.x = x;
			this.y = y;
			this.power = power;
			this.sum = sum;
			this.recent = recent;
		}

		@Override
		public int compareTo(Top o) {
			if(this.power == o.power) {
				if(this.recent == o.recent) {
					if(this.sum == o.sum) {
						return o.y - this.y; // 열 값이 가장 큰 포탑
					}
					return o.sum - this.sum; // 행과 열의 합이 가장 큰 포탑
				}
				return this.recent - o.recent; // 가장 최근에 공격한 포탑
			}
			return this.power - o.power; // 공격력이 가장 낮은 포탑
		}
	}
	static class Point {
		int x,y,dist;
		String num;
		public Point(int x, int y, int dist, String num) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.num = num;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		pq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] > 0) pq.add(new Top(i, j, board[i][j], i+j, 0));
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		int turn = 0;
		while(turn++ < K) {
			System.out.println(turn);
			if(pq.size() == 1) break;

			// 1. 공격자 선정
			select_attacker();
			
			// 2. 공격자 공격
			
			// 가장 강한 포탑 선정
			select_strongestTop();
			
			// 공격을 할 때에는 레이저 공격을 먼저 시도하고
			boolean flag = isPossible_lazer_attack();
			
			// 만약 그게 안 된다면 포탄 공격을 합니다.
			if(!flag) bomb_attack();

			// 3. 포탑 부서짐 & 4. 포탑 정비
			setting_top();
			
			print();
			System.out.println(" =================== ");
		}
		
		// 전체 과정이 종료된 후 남아있는 포탑 중 가장 강한 포탑의 공격력을 출력
		while(!pq.isEmpty()) {
			Top t = pq.poll();
			result = t.power;
		}
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	private static void setting_top() {
		// 공격자가 가장 강한 포탑 공격한 뒤, board와 pq 데이터 다시 세팅
		Queue<Top> q = new LinkedList<>();
		
		while(!pq.isEmpty()) {
			Top t = pq.poll();
			// 공격이 끝났으면, 부서지지 않은 포탑 중 공격과 무관했던 포탑은 공격력이 1씩 올라갑니다. 
			// 공격과 무관하다는 뜻은 공격자도 아니고, 공격에 피해를 입은 포탑도 아니라는 뜻입니다.
			if(t.power > 0 && board[t.x][t.y] == t.power) {
				t.power++;
				q.add(t);
				continue;
			}
			
			if(board[t.x][t.y] == 0) continue;
			
			if(board[t.x][t.y] > 0 && board[t.x][t.y] != t.power) {
				t.power = board[t.x][t.y];
				q.add(t);
				continue;
			}
		}
		
		while(!q.isEmpty()) {
			Top t = q.poll();
			t.recent++; // 최근에 공격하지 않았으므로 1 증가
			board[t.x][t.y] = t.power;
			pq.add(t);
		}
		
		attacker.recent = 0;
		pq.add(attacker);
		strongestTop.recent++;
		if(strongestTop.power > 0) pq.add(strongestTop);
	}

	private static void bomb_attack() {
		// 포탄 공격
		int[] ddx = {-1,-1,0,1,1,1,0,-1};
		int[] ddy = {0,1,1,1,0,-1,-1,-1};
		
		// 공격 대상에 포탄을 던집니다. 공격 대상은 공격자 공격력 만큼의 피해를 받습니다.
		board[strongestTop.x][strongestTop.y] -= attacker.power;
		
		// 공격 당한 후, 가장 강한 포탑의 공격력이 음수가 된다면,
		if(board[strongestTop.x][strongestTop.y] < 0) {
			board[strongestTop.x][strongestTop.y] = 0;
			strongestTop.power = 0;
		}
		else strongestTop.power -= attacker.power;
		
		// 추가적으로 주위 8개의 방향에 있는 포탑도 피해를 입는데, 공격자 공격력의 절반 만큼의 피해를 받습니다.
		int num = attacker.power / 2;
		for(int d=0;d<8;d++) {
			int nx = strongestTop.x + ddx[d];
			int ny = strongestTop.y + ddy[d];
			
			// 만약 가장자리에 포탄이 떨어졌다면, 위에서의 레이저 이동처럼 포탄의 추가 피해가 반대편 격자에 미치게 됩니다.
			if(is_out_of_range(nx, ny)) {
				if(nx == -1) nx = N-1;
				if(nx == N) nx = 0;
				if(ny == -1) ny = M-1;
				if(ny == M) ny = 0;
			}
			
			// 공격자는 해당 공격에 영향을 받지 않습니다.
			if(nx == attacker.x && ny == attacker.y) continue;
			
			board[nx][ny] -= num;
			if(board[nx][ny] < 0) board[nx][ny] = 0;
		}
	}

	private static boolean isPossible_lazer_attack() {
		// 레이저 공격
		// 상하좌우의 4개의 방향으로 움직일 수 있습니다.
		// 부서진 포탑이 있는 위치는 지날 수 없습니다.
		// 가장자리에서 막힌 방향으로 진행하고자 한다면, 반대편으로 나옵니다.
		// 레이저 공격은 공격자의 위치에서 공격 대상 포탑까지의 최단 경로로 공격
		
		boolean[][] visited = new boolean[N][M];
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(attacker.x, attacker.y, 0, ""));
		visited[attacker.x][attacker.y] = true;
		
		int distance = Integer.MAX_VALUE; // 이동거리 최소를 구해야 함.
		ArrayList<Integer> finalRoute = new ArrayList<>(); // 최종 이동경로
		
		while(!q.isEmpty()) {
			Point temp = q.poll();
			int x = temp.x;
			int y = temp.y;
			int dist = temp.dist;
			String route = temp.num;
			
			if(x == strongestTop.x && y == strongestTop.y) {
				// 경로의 길이가 똑같은 최단 경로가 2개 이상이라면, 우/하/좌/상의 우선순위대로 먼저 움직인 경로가 선택됩니다.
//				if(distance == dist) finalRoute = Math.min(finalRoute, route);
				if(distance > dist) {
					distance = dist;
					for(int i=0;i<route.length();i++)
						finalRoute.add(route.charAt(i)-'0');
				}
				break;
			}
			
			for(int d=1;d<=4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(is_out_of_range(nx,ny)) {
					if(nx == -1) nx = N-1;
					if(nx == N) nx = 0;
					if(ny == -1) ny = M-1;
					if(ny == M) ny = 0;
				}
				
				if(board[nx][ny] == 0) continue;
				
				if(visited[nx][ny]) continue;
				
				visited[nx][ny] = true;
				String s = ""+route+""+d;

				q.add(new Point(nx,ny,dist+1,s));
			}
		}
		
		// 만약 그러한 경로가 존재하지 않는다면 (2) 포탄 공격을 진행합니다.
		if(distance == Integer.MAX_VALUE) return false;
		
		int tx = attacker.x;
		int ty = attacker.y;
		
		// 최단 경로가 정해졌으면, 
		for(int i=0;i<finalRoute.size();i++) {
			int temp = finalRoute.get(i);
			int nx = tx + dx[temp];
			int ny = ty + dy[temp];
			
			if(is_out_of_range(nx,ny)) {
				if(nx == -1) nx = N-1;
				if(nx == N) nx = 0;
				if(ny == -1) ny = M-1;
				if(ny == M) ny = 0;
			}
			
			// 공격 대상에는 공격자의 공격력 만큼의 피해를 입히며, 피해를 입은 포탑은 해당 수치만큼 공격력이 줄어듭니다.
			if(nx == strongestTop.x && ny == strongestTop.y) {
				board[nx][ny] -= attacker.power;
				
				// 공격 당한 후, 공격력이 음수가 되면 0으로 세팅하기
				if(board[nx][ny]< 0) {
					board[nx][ny] = 0;
					strongestTop.power = 0;
				}
				else strongestTop.power = board[nx][ny];
				
			}
			
			// 공격 대상을 제외한 레이저 경로에 있는 포탑도 공격을 받게 되는데, 이 포탑은 공격자 공격력의 절반 만큼의 공격을 받습니다.
			else {
				int num = attacker.power/2;
				board[nx][ny] -= num;
				
				// 공격 당한 후, 공격력이 음수가 되면 0으로 세팅하기
				if(board[nx][ny] < 0) board[nx][ny] = 0;
			}
			tx = nx;
			ty = ny;
		}
		
		return true;
	}

	private static boolean is_out_of_range(int r, int c) {
		if(r == -1 || r == N || c == -1 || c == M) return true;
		return false;
	}

	private static void select_strongestTop() {
		// 가장 강한 포탑 선정
		Queue<Top> temp = new LinkedList<>();
		
		while(true) {
			Top t = pq.poll();
			if(pq.size() > 0) temp.add(t);
			else {
				strongestTop = new Top(t.x, t.y, t.power, t.sum, t.recent);
				break;
			}
		}
		
		while(!temp.isEmpty()) {
			Top t  = temp.poll();
			pq.add(t);
		}
		
	}

	private static void select_attacker() {
		
		Top t = pq.poll(); // pq에서 공격자 선정
		// N+M만큼의 공격력이 증가
		attacker = new Top(t.x, t.y, t.power+N+M, t.sum, t.recent);
		board[t.x][t.y] += N+M;
		
	}

}
