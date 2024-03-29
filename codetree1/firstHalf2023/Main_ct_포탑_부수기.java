package algo.codetree1.firstHalf2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_포탑_부수기 {
	
	static int N,M,K;
	static Top[][] board;
	static boolean[][] visited; // 해당 턴에 공격자 및 공격받은 포탑 체크
	// 우/하/좌/상의 우선순위대로 먼저 움직인 경로가 선택됩니다.
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static PriorityQueue<Top> pq;
	static class Top implements Comparable<Top>{
		int x,y,p,time,xySum;
		public Top(int x, int y, int p) {
			this.x = x;
			this.y = y;
			this.p = p;
			this.time = 0;
			this.xySum = x + y;
		}
		public int compareTo(Top o) {
			if(this.p == o.p) {
				if(this.time == o.time) {
					if(this.xySum == o.xySum) {
						// 만약 그러한 포탑이 2개 이상이라면, 각 포탑 위치의 열 값이 가장 큰 포탑이 가장 약한 포탑입니다.
						return o.y - this.y;
					}
					// 만약 그러한 포탑이 2개 이상이라면, 각 포탑 위치의 행과 열의 합이 가장 큰 포탑이 가장 약한 포탑입니다.
					return o.xySum - this.xySum;
				}
				// 만약 공격력이 가장 낮은 포탑이 2개 이상이라면, 가장 최근에 공격한 포탑이 가장 약한 포탑입니다. (모든 포탑은 시점 0에 모두 공격한 경험이 있다고 가정하겠습니다.)
				return this.time - o.time;
			}
			// 공격력이 가장 낮은 포탑이 가장 약한 포탑입니다.
			return this.p - o.p;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		board = new Top[N][M];
		pq = new PriorityQueue<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				int p = Integer.parseInt(st.nextToken());
				board[i][j] = new Top(i, j, p);
				if(board[i][j].p > 0) {
					pq.add(board[i][j]);
				}
			}
		} // input end
		
		solve();
		
		// 첫 번째 줄에 K번의 턴이 종료된 후 남아있는 포탑 중 가장 강한 포탑의 공격력을 출력합니다.
		int result = 0;
		while(!pq.isEmpty()) {
			result = pq.poll().p;
		}
		System.out.println(result);
	}

	private static void solve() {
		// 하나의 턴은 다음의 4가지 액션을 순서대로 수행하며, 총 K번 반복됩니다.
		while(K-- > 0) {
			// 만약 부서지지 않은 포탑이 1개가 된다면 그 즉시 중지됩니다.
			if(pq.size() == 1) break;
			
			visited = new boolean[N][M];
			// 1. 공격자 선정
			// 부서지지 않은 포탑 중 가장 약한 포탑이 공격자로 선정됩니다. 
			Top attacker = pq.poll();
			visited[attacker.x][attacker.y] = true;
			// 공격자로 선정되면 가장 약한 포탑이므로, 핸디캡이 적용되어 N+M만큼의 공격력이 증가됩니다.
			attacker.p += (N+M);
			
			// 2. 공격 대상 선정
			// 가장 강한 포탑
			Top strongestTop = null;
			while(!pq.isEmpty()) {
				strongestTop = pq.poll();
			}
			visited[strongestTop.x][strongestTop.y] = true;
			
			// 3. 공격자의 공격 시작
			attack(attacker, strongestTop);
			
			pq.clear();
			// 4. 공격력 0이 된 포탑 부서짐 & 포탑 정비(공격과 무관했던 포탑들 공격력 1씩 증가)
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					// 이미 부서진 포탑이나 공격 당한 위치들은 넘어간다.
					if(board[i][j].p == 0) continue;
					// 공격과 무관했던 포탑들 공격력 1 증가
					if(board[i][j].p > 0 && !visited[i][j]) {
						board[i][j].p++;
					}
					if(i == attacker.x && j == attacker.y) {						
						board[attacker.x][attacker.y].time = 0;
						pq.add(board[i][j]);
						continue;
					}
					
					if(board[i][j].p > 0) {
						board[i][j].time++;
						pq.add(board[i][j]);
					}
				}
			}
			
			System.out.println("attacker : "+attacker.x+", "+attacker.y);
			System.out.println("strongest : "+strongestTop.x+", "+strongestTop.y);
			print();
			System.out.println(" ======================== ");
		}
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				System.out.print(board[i][j].p+"\t");
			}
			System.out.println();
		}
	}

	private static void attack(Top attacker, Top strongestTop) {
		if(!lazer_attack(attacker, strongestTop)) {
			bomb_attack(attacker, strongestTop);
		}
	}

	private static void bomb_attack(Top attacker, Top strongestTop) {
		int[] ddx = {-1,1,0,0,-1,-1,1,1};
		int[] ddy = {0,0,-1,1,-1,1,-1,1};
		// 공격 대상에 포탄을 던집니다. 공격 대상은 공격자 공격력 만큼의 피해를 받습니다.
		board[strongestTop.x][strongestTop.y].p -= attacker.p;
		if(board[strongestTop.x][strongestTop.y].p < 0)
			board[strongestTop.x][strongestTop.y].p = 0;
		// (절반이라 함은 공격력을 2로 나눈 몫을 의미합니다.) 
		// 만약 가장자리에 포탄이 떨어졌다면, 위에서의 레이저 이동처럼 포탄의 추가 피해가 반대편 격자에 미치게 됩니다.
		
		// 추가적으로 주위 8개의 방향에 있는 포탑도 피해를 입는데, 공격자 공격력의 절반 만큼의 피해를 받습니다. 
		for(int d=0;d<8;d++) {
			int nx = strongestTop.x + ddx[d];
			int ny = strongestTop.y + ddy[d];
			
			int[] pos = checkRange(nx, ny);
			
			if(pos[0] == -1 && pos[1] == -1) continue;
			
			nx = pos[0];
			ny = pos[1];
			
			if(board[nx][ny].p == 0) continue;
			
			// 공격자는 해당 공격에 영향을 받지 않습니다. 
			if(nx == attacker.x && ny == attacker.y) continue;
			
			visited[nx][ny] = true;
			board[nx][ny].p -= (attacker.p / 2);
			if(board[nx][ny].p < 0)
				board[nx][ny].p = 0;
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
	
	private static boolean lazer_attack(Top attacker, Top strongestTop) {
		// 상하좌우의 4개의 방향으로 움직일 수 있습니다.
		// 부서진 포탑이 있는 위치는 지날 수 없습니다.
		// 가장자리에서 막힌 방향으로 진행하고자 한다면, 반대편으로 나옵니다. 
		// (예를 들어, 위의 예시에서 (2,3)에서 오른쪽으로 두번 이동한다면, (2,3) -> (2,4) -> (2,1) 순으로 이동합니다.)
		// 레이저 공격은 공격자의 위치에서 공격 대상 포탑까지의 최단 경로로 공격합니다. 
		// 만약 그러한 경로가 존재하지 않는다면 (2) 포탄 공격을 진행합니다. 
		// 만약 경로의 길이가 똑같은 최단 경로가 2개 이상이라면, 우/하/좌/상의 우선순위대로 먼저 움직인 경로가 선택됩니다.
		
		Queue<Point> q = new LinkedList<>();
		boolean[][] visit = new boolean[N][M];
		q.add(new Point(attacker.x, attacker.y, 0, ""));
		visit[attacker.x][attacker.y] = true;
		int distance = Integer.MAX_VALUE;
		String finalRoute = "";
		
		while(!q.isEmpty()) {
			Point temp = q.poll();
			
			if(temp.x == strongestTop.x && temp.y == strongestTop.y) {
				if(distance > temp.dist) {
					distance = temp.dist;
					finalRoute = temp.dir;
					// 최단 경로가 정해졌으면, 공격 대상에는 공격자의 공격력 만큼의 피해를 입히며, 피해를 입은 포탑은 해당 수치만큼 공격력이 줄어듭니다. 또한 공격 대상을 제외한 레이저 경로에 있는 포탑도 공격을 받게 되는데, 이 포탑은 공격자 공격력의 절반 만큼의 공격을 받습니다. (절반이라 함은 공격력을 2로 나눈 몫을 의미합니다.)
					int nx = attacker.x;
					int ny = attacker.y;
					
					for(int i=0;i<finalRoute.length();i++) {
						int d = finalRoute.charAt(i)-'0';
						
						nx += dx[d];
						ny += dy[d];
						
						int[] pos = checkRange(nx, ny);
						
						nx = pos[0];
						ny = pos[1];
						
						visited[nx][ny] = true;
						
						if(nx == strongestTop.x && ny == strongestTop.y) {
							board[nx][ny].p -= attacker.p;
						}
						else {
							board[nx][ny].p -= (attacker.p / 2);
						}
						
						if(board[nx][ny].p <  0) {
							board[nx][ny].p = 0;
						}
					}
					return true;
				}
			}
			
			for(int d=0;d<4;d++) {
				int nx = temp.x + dx[d];
				int ny = temp.y + dy[d];
				
				int[] pos = checkRange(nx, ny);
				
				if(pos[0] == -1 && pos[1] == -1) continue;
				
				nx = pos[0];
				ny = pos[1];
				
				if(visit[nx][ny] || board[nx][ny].p == 0) continue;
				
				visit[nx][ny] = true;
				Point next = new Point(nx, ny, temp.dist+1, temp.dir+""+d);
				q.add(next);
			}
		}
		
		if(distance == Integer.MAX_VALUE) return false;
		
//		// 최단 경로가 정해졌으면, 공격 대상에는 공격자의 공격력 만큼의 피해를 입히며, 피해를 입은 포탑은 해당 수치만큼 공격력이 줄어듭니다. 또한 공격 대상을 제외한 레이저 경로에 있는 포탑도 공격을 받게 되는데, 이 포탑은 공격자 공격력의 절반 만큼의 공격을 받습니다. (절반이라 함은 공격력을 2로 나눈 몫을 의미합니다.)
//		int nx = attacker.x;
//		int ny = attacker.y;
//		
//		for(int i=0;i<finalRoute.length();i++) {
//			int d = finalRoute.charAt(i)-'0';
//			
//			nx += dx[d];
//			ny += dy[d];
//			
//			int[] pos = checkRange(nx, ny);
//			
//			nx = pos[0];
//			ny = pos[1];
//			
//			visited[nx][ny] = true;
//			
//			if(nx == strongestTop.x && ny == strongestTop.y) {
//				board[nx][ny].p -= attacker.p;
//			}
//			else {
//				board[nx][ny].p -= (attacker.p / 2);
//			}
//			
//			if(board[nx][ny].p <  0) {
//				board[nx][ny].p = 0;
//			}
//		}
		
		return false;
	}


	private static int[] checkRange(int r, int c) {
		int[] ret = new int[2];
		ret[0] = r;
		ret[1] = c;
		
//		if( (r<0 && c<0) || (r<0 && c>=M) || (r>=N && c<0) || (r>=N && c>=M) ) {
//			ret[0] = -1;
//			ret[1] = -1;
//			return ret;
//		}
		
		if(r == -1) ret[0] = N-1;
		else if(r == N) ret[0] = 0;
		
		if(c == -1) ret[1] = M-1;
		else if(c == M) ret[1] = 0;
		
		return ret;
	}

}
